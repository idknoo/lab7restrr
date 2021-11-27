package server;

import lib.collection.CollectionWorker;
import lib.commands.Command;
import lib.commands.ReadOrganizationOperation;
import lib.commands.User;
import lib.message.CollectionMessage;
import lib.message.Message;
import lib.organization.Organization;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionWorkerImpl implements CollectionWorker {

    private final LocalDateTime time;
    private final LinkedHashMap<Integer, Organization> organizationLinkedHashMap = new LinkedHashMap<Integer, Organization>();
    private final Database database;

    public CollectionWorkerImpl(Database database) {
        database.readAllOrganizations().forEach(organization -> {
            organizationLinkedHashMap.put(organization.getId(), organization);
        });
        this.time = LocalDateTime.now();
        this.database = database;
    }

    @Override
    public Message help() {
        return new Message("Вызвана команда help, идёт вывод доступных команд.\n" +
                "help : вывести справку по доступным командам.\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении.\n" +
                "insert : добавить новый элемент с заданным ключом.\n" +
                "updateId + {element} : обновить значение элемента коллекции, id которого равен заданному.\n" +
                "removeKey + {element}: удалить элемент из коллекции по его ключу.\n" +
                "clear : очистить коллекцию.\n" +
                "save : сохранить коллекцию в файл.\n" +
                "exit : завершить программу (без сохранения в файл).\n" +
                "executeScript + {element}: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "replaceIfLowe : заменить значение по ключу, если новое значение меньше старого.\n" +
                "removeGreatKey + {element} : удалить из коллекции все элементы, ключ которых превышает заданный.\n" +
                "removeLowerKey + {element} : удалить из коллекции все элементы, ключ которых меньше, чем заданный.\n" +
                "removeAllByCount + {element} : удалить из коллекции все элементы, значение поля employeesCount которого эквивалентно заданному.\n" +
                "maxByName : вывести любой объект из коллекции, значение поля name которого является максимальным.\n" +
                "printFieldDescending : вывести значения поля annualTurnover всех элементов в порядке убывания.");
    }

    @Override
    public Message clear(User user) {
        String username = user.getName();
        List<Organization> toRemove = organizationLinkedHashMap.values().stream()
                .filter(p -> p.getOwnerName().equals(username))
                .collect(Collectors.toList());

        if (toRemove.size() > 0) {
            boolean[] success = new boolean[]{true};

            toRemove.forEach(p -> {
                if (database.removeOrganization(p))
                    organizationLinkedHashMap.remove(p);
                else
                    success[0] = false;
            });

            return success[0]
                    ? new Message("Все элементы были удалены")
                    : new Message("Не все элементы были удалены из-за ошибки в БД");
        }

        return new Message("У вас нет элементов для удаления");
    }

    @Override
    public Message info() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Вызвана команда info. Информация о коллекции:");
        strBuilder.append("\nТип коллекции: " + organizationLinkedHashMap.getClass().getName());
        strBuilder.append("\nКоллекция содержит элементы класса: Organization");
        strBuilder.append(String.format("\nКоличество элементов коллекции: %d\n", this.organizationLinkedHashMap.size()));
        if (this.organizationLinkedHashMap.size() > 0) {
            strBuilder.append(String.format("\nДата инициализации коллекции: %s\n", this.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            strBuilder.append(String.format("\nМаксимальный элемент коллекции: \n%s\n", this.getMaxOrganization().toString()));
        }
        return new Message(strBuilder.toString());
    }

    private Organization getMaxOrganization() {
        return organizationLinkedHashMap.values().stream().max(Organization::compareTo).orElse(null);
    }

    @Override
    public Message show() {
        String text = organizationLinkedHashMap
                .values()
                .stream()
                .map(Organization::toString)
                .collect(Collectors.joining("\n"));
        return new CollectionMessage(text,
                organizationLinkedHashMap.values().toArray(new Organization[0]));
    }

    @Override
    public Message updateId(Organization organization, Integer id, User user) { // проверить
        Organization toRemove = organizationLinkedHashMap.values().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (toRemove == null)
            return new Message("Организации с id " + id + " не существует.");

        if (!toRemove.getOwnerName().equals(user.getName()))
            return new Message("Организация с id = " + id + " не твоя. Не трогай чужое!");

        organization.setOwnerName(user.getName());
        organization.setId(id);

        if (database.removeOrganization(id) && database.insertOrganizationWithoutInitialization(organization)) {
            organizationLinkedHashMap.remove(id);
            organizationLinkedHashMap.put(id, organization);
            return new Message("Организация с id = " + id + " обновлена.");
        } else {
            return new Message("Ошибка БД.");
        }
    }

    @Override
    public Message info(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Вызвана команда info. Информация о коллекции:");
        stringBuilder.append("\nТип коллекции: ").append(organizationLinkedHashMap.getClass().getName());
        stringBuilder.append("\nКоллекция содержит элементы класса: Organization");
        stringBuilder.append(String.format("\nКоличество элементов коллекции: %d\n", this.organizationLinkedHashMap.size()));
        if (this.organizationLinkedHashMap.size() > 0) {
            stringBuilder.append(String.format("\nДата инициализации коллекции: %s\n", this.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            stringBuilder.append(String.format("\nМаксимальный элемент коллекции: \n%s\n", this.getMaxOrganization().toString()));
        }
        return new Message(stringBuilder.toString());
    }


//    @Override
//    public Message save() {
//        try {
//            if (organizationLinkedHashMap.size() == 0) {
//                fileWorker.clear();
//                return new Message("коллекция пуста. файл удален");
//            }
//            fileWorker.serialize(new ArrayList<Organization>(organizationLinkedHashMap.values()));
//            return new Message("коллекция сохранена");
//        } catch (IOException e) {
//            e.printStackTrace();
//        return new Message("не удалось сохранить коллекцию");
//        }
//    }

    public LinkedHashMap<Integer, Organization> getCollection() {
        return organizationLinkedHashMap;
    }

    @Override
    public Message insert(Organization organization, User user) {
        if (database.initializeAndInsertOrganization(organization, user)) {
            organizationLinkedHashMap.put(organization.getId(), organization); //проверить это
            return new Message("Организация добавлена.");
        } else {
            return new Message("Организация не добавлена из-за ошибки в Базе данных.");
        }
    }

//    private void readOrganizations() {
//        ArrayList<Organization> organizations = fileWorker.parse();
//        for (Organization organization : organizations) {
//            insert(organization);
//        }
//    }

    @Override
    public Message replaceIfLowe(Organization organization, User user) {
        Organization toReplace = organizationLinkedHashMap
                .values()
                .stream()
                .filter(p -> p.getOwnerName().equals(organization.getOwnerName())
                        && organization.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (organization.compareTo(toReplace) > 0) { // or < 0
            organizationLinkedHashMap.put(organization.getId(), organization);
            return new Message("Организация с заменой ID прошла успешно");
        } else {
            return new Message("Организация с заменой не произошла");
        }
    }

//    @Override
//    public Message save() {
//        return null;
//    }

    @Override
    public Message removeLowerKey(Organization organization, User user) {
        List<Organization> toRemove = organizationLinkedHashMap.values().stream()
                .filter(p -> p.compareTo(organization) < 0)
                .filter(p -> p.getOwnerName().equals(organization.getOwnerName()))
                .collect(Collectors.toList());

        int[] cnt = new int[]{0};
        toRemove.forEach(p -> {
            if (database.removeOrganization(p)) {
                organizationLinkedHashMap.remove(p);
                ++cnt[0];
            }
        });

        String answer = cnt[0] + " организаций уничтожена.";
        if (toRemove.size() != cnt[0])
            answer += "\n Ошибка БД.";

        return new Message(answer);
    }

    @Override
    public Message removeKey(Integer id, User user) {
        // cities = cities.stream().filter(h -> !h.getId().equals(id)).collect(Collectors.toCollection(() -> new PriorityQueue<>()));
        Organization organization = organizationLinkedHashMap.values()
                .stream()
                .filter(h -> h.getId().equals(id))
                .findAny()
                .orElse(null);
        if (organization == null) {
            return new Message("Элемент не найден");
        }
        if (organization.getOwnerName().equals(user.getName())) {
            if (database.removeOrganization(id)) {
                return new Message("Элемент удалён");
            } else {
                return new Message("В базе данных ошибка.");
            }
        } else {
            return new Message("Вы кто такой, я вас не звал, это не ваш объект. ");
        }
    }


    @Override
    public Message removeGreatKey(Organization organization, User user) {
        List<Organization> toRemove = organizationLinkedHashMap.values().stream()
                .filter(p -> p.compareTo(organization) > 0)
                .filter(p -> p.getOwnerName().equals(organization.getOwnerName()))
                .collect(Collectors.toList());

        int[] cnt = new int[]{0};
        toRemove.forEach(p -> {
            if (database.removeOrganization(p)) {
                organizationLinkedHashMap.remove(p);
                ++cnt[0];
            }
        });

        String answer = cnt[0] + " организаций уничтожена.";
        if (toRemove.size() != cnt[0])
            answer += "\n Ошибка БД.";

        return new Message(answer);
    }

    @Override
    public Message removeAllByCount(int employeesCount, User user) {
        Organization organization = organizationLinkedHashMap.values()
                .stream()
                .filter(p -> p.getOwnerName().equals(user.getName()))
                .filter(h -> h.getEmployeesCount() == employeesCount).findAny()
                .orElse(null);
        if (organization == null) {
            return new Message("Элемент не найден");
        }
        organizationLinkedHashMap.remove(organization.getId());
        return new Message("Элемент удален");
    }

    @Override
    public Message printFieldDescending(User user) {
        List<String> list = organizationLinkedHashMap
                .values()
                .stream()
                .filter(p -> p.getOwnerName().equals(user.getName()))
                .map(Organization::getAnnualTurnover)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.toList());
        return new Message(String.join(", ", list));
    }

    @Override

    public Message maxByName(User user) {
        String max = organizationLinkedHashMap
                .values()
                .stream()
                .filter(p -> p.getOwnerName().equals(user.getName()))
                .map(Organization::getName)
                .max(String::compareTo)
                .orElse(null);
        return new Message(String.join(", ", max));

    }


    public Message execute(Command command, User user) {
        return command.execute(this, user);
    }
}
