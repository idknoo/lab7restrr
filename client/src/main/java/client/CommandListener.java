package client;

import lib.commands.*;
import lib.organization.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandListener {
    private final BufferedReader input;
    private final Boolean isScript;



    public CommandListener(InputStream inputStream, boolean isScript) {
        this.isScript = isScript;
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        this.input = input;
    }

    public Command readCommand() throws IOException {
        String line = null;
        try {
            line = input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (line == null) {
            System.out.println("Ctrl+D: завершение работы");
            System.exit(0);
        }

        String[] cmd = line.split(" ");

        switch (cmd[0]) {
            case "help":
                if (cmd.length == 1) return new Help();
                else {
                    break;
                }
            case "info":
                if (cmd.length == 1) return new Info();
                else {
                    break;
                }
            case "clear":
                if (cmd.length == 1) return new Clear();
                else {
                    break;
                }
            case "show":
                if (cmd.length == 1) return new Show();
                else {
                    break;
                }
            case "insert":
                if (cmd.length == 1) return new Insert(readOrganization());
                else {
                    break;
                }

            case "maxByName":
                if (cmd.length == 1)
                    return new MaxByName();
                else {
                    break;
                }

            case "printFieldDescending":
                if (cmd.length == 1)
                    return new PrintFieldDescending();
                else {
                    break;
                }

            case "replaceIfLowe":
                if (cmd.length == 1)
                    return new ReplaceIfLowe(readOrganization());
                else {
                    break;
                }

            case "removeKey":
                if (cmd.length == 2)
                    return new RemoveKey(Integer.parseInt(cmd[1]));
                else {
                    break;
                }
            case "removeAllByCount":
                if (cmd.length == 2)
                    return new RemoveAllByCount(Integer.parseInt(cmd[1]));
                else {
                    break;
                }

            case "removeGreatKey":
                if (cmd.length == 2)
                    return new RemoveGreatKey(Integer.parseInt(cmd[1]));
                else {
                    break;
                }

            case "removeLowerKey":
                if (cmd.length == 2)
                    return new RemoveLowerKey(Integer.parseInt(cmd[1]));
                else {
                    break;
                }
            case "updateId":
                if (cmd.length == 2)
                    return new UpdateId(() -> readOrganizationId(), Integer.parseInt(cmd[1]));
                else {
                    break;
                }
            case "save":
                if (cmd.length == 1) return new Save();
                else {
                    break;
                }
            case "exit":
                return new Exit();

            case "executeScript":
                try {
                    if (cmd.length == 2) {
                        File file = new File(cmd[1]);
                        if (!file.exists() && file.canRead() && !file.isDirectory() && file.isFile())
                            throw new FileNotFoundException();
                        if (isScript)
                            return new CommandError("Нельзя выполнить один скрипт из другого скрипта");
                        CommandListener commandListener = new CommandListener(new FileInputStream(file), true);
                        return new ExecuteScript(commandListener.readAllCommands());
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Скрипт некорректен");
                }
            default:
                if (isScript)
                    return new CommandError("Вы ввели неверную команду");
                System.out.println("Вы ввели неверную команду. Вызвана команда help с справкой.");
                return new Help();
        }
        if (isScript)
            return new CommandError("Вы ввели неверную команду");
        System.out.println("Вы ввели неверную команду. Вызвана команда help с справкой.");
        return new Help();
    }

    public Command[] readAllCommands() throws IOException {
        ArrayList<Command> commands = new ArrayList<>();
        while (input.ready()) {
            commands.add(readCommand());
        }
        return commands.toArray(new Command[0]);
    }

    private Organization readOrganization() {
        Organization organization = new Organization();
        Scanner scanner = new Scanner(System.in);
        organization.setId(FieldReader.readID(scanner, "Введите ID организации."));
        organization.setName(FieldReader.readString(scanner, "Введите название название организации."));
        float x = FieldReader.readX(scanner);
        long y = FieldReader.readY(scanner);
        organization.setCoordinates(new Coordinates(x, y));
        organization.setAnnualTurnover(FieldReader.readAnnualTurnover(scanner));
        organization.setFullName(FieldReader.readFullName(scanner, "Введите полное название организации."));
        organization.setEmployeesCount(FieldReader.readEmployeesCount(scanner));
        organization.setType(OrganizationType.valueOf(FieldReader.readOrganizationType(scanner)));
        organization.setPostalAddress(FieldReader.readPostalAddress(scanner));
        organization.setCreationDate(ZonedDateTime.now());

        return organization;
    }

    private Organization readOrganizationId() {
        Organization organization = new Organization();
        Scanner scanner = new Scanner(System.in);
//        organization.setId(FieldReader.readID(scanner, "Введите ID организации."));
        organization.setName(FieldReader.readString(scanner, "Введите название название организации."));
        float x = FieldReader.readX(scanner);
        long y = FieldReader.readY(scanner);
        organization.setCoordinates(new Coordinates(x, y));
        organization.setAnnualTurnover(FieldReader.readAnnualTurnover(scanner));
        organization.setFullName(FieldReader.readFullName(scanner, "Введите полное название организации."));
        organization.setEmployeesCount(FieldReader.readEmployeesCount(scanner));
        organization.setType(OrganizationType.valueOf(FieldReader.readOrganizationType(scanner)));
        organization.setPostalAddress(FieldReader.readPostalAddress(scanner));
        organization.setCreationDate(ZonedDateTime.now());

        return organization;
    }

}
