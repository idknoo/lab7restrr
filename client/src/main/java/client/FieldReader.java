package client;

import lib.organization.Address;
import lib.organization.Location;
import lib.organization.OrganizationType;

import java.util.Scanner;

/**
 * Класс с статическмими методами для вывода сообщений и считывания строчных полей класса
 * {@link lib.organization.Organization}
 * В методы встроена проверка после которой они возвращают значения или кидают эксепшн
 */
public class FieldReader {
    /**
     * Метод для чтения поля типа String
     *
     * @param scanner      - сканер
     * @param notification - сообщение
     * @return возвращает поле строчного типа с клавы
     */
    public static String readString(Scanner scanner, String notification) {
        System.out.print(notification);
        String name = "";
        do {
            name = scanner.nextLine().trim();
            if (name.isEmpty() | name.equals("")) {
                System.out.println("Поле не может быть пустым. Пожалуйста повторите ввод");
            } else {
                break;
            }
        }
        while (true);
        return name;
    }

    /**
     * Метод для чтения поля типа String
     *
     * @param scanner      - сканер
     * @param notification - сообщение
     * @return возвращает поле строчного типа с клавы
     */
    public static String readFullName(Scanner scanner, String notification) {
        System.out.print(notification);
        String fullName = "";
        do {
            fullName = scanner.nextLine().trim();
            if (fullName.isEmpty() | fullName.equals("")) {
                System.out.println("Поле не может быть пустым. Пожалуйста повторите ввод");
            }
            if (fullName.length() > 1290) {
                System.out.println("Поле не может быть больше 1290 символов.");
            } else {
                break;
            }
        }
        while (true);
        return fullName;
    }

    /**
     * Ленивый считыватель инт
     *
     * @param scanner - сканр
     * @param msg     - сообщение для вывода при вводе
     * @return - возвращает число большое 0
     */
    public static Integer readInt(Scanner scanner, String msg) {
        System.out.println(msg);
        int number = -1;
        do {
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number < 0) {
                    System.out.println("Число не может быть меньше 0.");
                    System.out.println("Повторите ввод:");
                } else {
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода. Вводите число без пробелов и разделителей.");
                System.out.println("Повторите ввод:");
            }
        } while (number < 0);
        return number;
    }

    /**
     * Метод для считывания координаты X
     *
     * @param scanner - сканнер
     * @return - возвращает координату X
     */
    public static float readX(Scanner scanner) {
        System.out.println("Ввод координат.");
        System.out.println("Введите х:");
        do {
            try {
                float x = Float.parseFloat(scanner.nextLine());
                if (x == 0) {
                    System.out.println("Х не может быть 0");
                    continue;
                }
                return x;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите X без пробелов и разделителей!");
            }
        } while (true);

    }

    /**
     * Метод для чтения поля типа Integer
     *
     * @param scanner      - сканер
     * @param notification - сообщение
     * @return возвращает поле строчного типа с клавы
     */
    public static Integer readID(Scanner scanner, String notification) {
        System.out.println("Ввод ID.");
        System.out.print("> ");
        Integer x;
        do {
            try {
                x = Integer.parseInt(scanner.nextLine());
                if (x <= 0) {
                    System.out.println("Х не может быть меньше или равно нуля");
                    continue;
                }
                return x;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода ID.");
                System.out.println("Введите число без пробелов и разделителей!");
            }
        } while (true);

    }

    public static Integer saveId(Scanner scanner, String notification) {
        System.out.println("Ввод ID.");
        System.out.print("> ");
        Integer x;
        do {
            try {
                x = Integer.parseInt(scanner.nextLine());
                if (x <= 0) {
                    System.out.println("Х не может быть меньше или равно нуля");
                    continue;
                }
                return x;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода ID.");
                System.out.println("Введите число без пробелов и разделителей!");
            }
        } while (true);

    }

    /**
     * Метод считывающий координату Y с клавы
     *
     * @param scanner - сканер
     * @return возвращает Y считанное с клавы
     */
    public static long readY(Scanner scanner) {
        System.out.println("Ввод координат.");
        System.out.println("Введите Y:");
        long y;
        do {
            try {
                y = Long.parseLong(scanner.nextLine());
                if (y > 920) {
                    System.out.println("Y не может быть больше 920");
                    continue;
                }
                return y;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите Y без пробелов и разделителей!");
            }
        } while (true);
    }

    /**
     * Метод считывающий площадь города с клавы
     *
     * @param scanner -сканер
     * @return - возвращает площадь>0
     */
    public static int readArea(Scanner scanner) {
        int area = -1;
        boolean pass = true;
        System.out.println("Ввод площади города. Площадь должна быть больше 0.");
        System.out.println("Введите площадь:");
        do {
            try {
                area = Integer.parseInt(scanner.nextLine());
                if (area <= 0) {
                    System.out.println("Площадь не может быть меньше или равна 0.");
                    System.out.println("Повторите ввод:");
                } else {
                    pass = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите площадь без пробелов и разделителей!");
                pass = true;
            }
        } while (pass);
        return area;

    }

    /**
     * Метод для считывания населения города
     *
     * @param scanner
     * @return - возвращает население города>0
     */
    public static double readAnnualTurnover(Scanner scanner) {
        int AnnualTurnover = -1;
        boolean pass = true;
        System.out.println("Ввод AnnualTurnover. Оно должно быть больше 0.");
        System.out.print("> ");
        do {
            try {
                AnnualTurnover = Integer.parseInt(scanner.nextLine());
                if (AnnualTurnover <= 0) {
                    System.out.println("AnnualTurnover не может быть меньше или равна 0.");
                    System.out.println("Повторите ввод:");
                } else {
                    pass = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите кол-во населения без пробелов и разделителей!");
                pass = true;
            }
        } while (pass);
        return AnnualTurnover;
    }

    /**
     * Метод для считывания высоты над уровнем моря
     *
     * @param scanner - сканер
     * @return возвращает значения поля считанного с клавы
     */
    public static int readMetersAboveSeaLevel(Scanner scanner) {
        int number = 9000;
        boolean pass = true;
        System.out.println("Ввод высоты над уровнем моря.");
        System.out.println("Введите высоту над уровнем моря:");
        do {
            try {
                pass = false;
                number = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка формата ввода.");
                System.out.println("Введите число без пробелов и разделителей.");
                pass = true;
            }
        } while (pass);
        return number;
    }


    public static int readEmployeesCount(Scanner scan) {
        int EmployeesCount;
        System.out.println("Ввод кол-ва работников. Кол-во работников должно быть выше 0.");
        System.out.println("Введите кол-во работников.");
        do {
            try {
                EmployeesCount = Integer.parseInt(scan.nextLine());
                if (EmployeesCount <= 0) {
                    System.out.println("Кол-во работников должно быть выше 0. Повторите ввод");
                } else {
                    return EmployeesCount;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка формата ввода.");
                System.out.println("Введите число без пробелов и разделителей.");
            }

        } while (true);
    }

    public static String readOrganizationType(Scanner scan) {
        System.out.println("Ввод тип организации. Введите строго по примерам ниже. При отсутствии данной информации не вводите ничего.");
        System.out.println("PUBLIC");
        System.out.println("TRUST");
        System.out.println("PRIVATE_LIMITED_COMPANY");
        System.out.println("OPEN_JOINT_STOCK_COMPANY");
        System.out.println("Введите тип организации:");
        String line;
        OrganizationType type;
        do {
            try {
                line = scan.nextLine();
                if (line.isEmpty()) {
                    return (null);
                } else {
                    type = OrganizationType.valueOf(line);
                    return line;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Тип введён в неправильном формате.");
                System.out.println("Вводить название типа организации строго по примерам. Типы организаций:");
                System.out.println("PUBLIC");
                System.out.println("TRUST");
                System.out.println("PRIVATE_LIMITED_COMPANY");
                System.out.println("OPEN_JOINT_STOCK_COMPANY");
                System.out.println("Повторите ввод:");
            }
        } while (true);
    }

    public static long readGovernorAge(Scanner scan) {
        System.out.println("Введите возраст гос. руководителя");
        long age;
        do {
            try {
                age = Long.parseLong(scan.nextLine());
                if (age <= 0) {
                    System.out.println("Возвраст должен быть больше 0");
                    continue;
                }
                return age;
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода координат.");
                System.out.println("Введите возраст без пробелов и разделителей!");
            }
        } while (true);
    }

    public static Address readPostalAddress(Scanner scan) {

//        String street;
//        new Address(street, location);
        System.out.println("Введите название улицы:");
        do {
            String street = scan.nextLine().trim();

            if (street.isEmpty() | street.equals("")) {
                System.out.println("Поле не может быть пустым. Пожалуйста повторите ввод");
                continue;
            }
            return new Address(street, readLocation(scan));
        } while (true);
    }


        private static Location readLocation (Scanner scanner){
            do {
                try {
                    System.out.println("Введите х: ");
                    long x = Long.parseLong(scanner.nextLine());
                    if (x == 0) {
                        System.out.println("х не может равняться 0");
                        continue;
                    }
                    System.out.println("Введите y: ");
                    Float y = Float.parseFloat(scanner.nextLine());
                    System.out.println("Введите z: ");
                    double z = Double.parseDouble(scanner.nextLine());
                    if (z == 0) {
                        System.out.println("z не может равняться 0");
                        continue;
                    }

                    return new Location(x, y, z);
                } catch (NumberFormatException ex) {
                    System.out.println("Ошибка формата ввода.");
                    System.out.println("Введите число без пробелов и разделителей.");
                }
            } while (true);
        }


    }
