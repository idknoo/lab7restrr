package lib.organization;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.PriorityQueue;

/**
 * Класс организации для описания хранящихся в коллекции данных
 */
public class Organization implements Comparable<Organization>, Serializable {

    private String ownerName;
    /**
     * ID организации Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Название организации. Поле не может быть null, Строка не может быть пустой
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Координаты города. Не может быть null.
     */
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double annualTurnover; //Значение поля должно быть больше 0
    private String fullName; //Длина строки не должна быть больше 1230, Поле может быть null
    private int employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null

    public Organization(String name, Coordinates coordinates, Integer id, ZonedDateTime creationDate, float annualTurnover, String fullName, int employeesCount, OrganizationType type, Address address) {

        this.name = name;
        this.coordinates = coordinates;
        this.id = id;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = address;
//        this.area = area;
//        this.population = population;
//        this.metersAboveSeaLevel = metersAboveSeaLevel;
//        this.agglomeration = agglomeration;
//        this.government = government;
//        this.governor = governor;
//        this.climate = climate;
    }

    public Organization() { //у нас нет
    } //конструктор для использования в команде Add

    //Далее идут геттеры и сеттеры

    /**
     * Метод для установки названия организации
     *
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setAnnualTurnover(double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    // геттеры
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }


    public int getEmployeesCount() {
        return employeesCount;
    }


    public OrganizationType getType() {
        return type;
    }


    public Address getPostalAddress() {
        return postalAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public double getAnnualTurnover() {
        return annualTurnover;
    }


    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    @Override
    public String toString() {
        return "Organization:\n" +
                "ownerName" + ownerName + "\n" +
                "id=" + id + "\n" +
                "name='" + name + '\'' +'\n' +
                "coordinates=" + coordinates + "\n" +
                "creationDate=" + creationDate + "\n" +
                "annualTurnover=" + annualTurnover + "\n" +
                "fullName='" + fullName +  '\'' + "\n" +
                "employeesCount=" + employeesCount + "\n" +
                "type=" + type + "\n" +
                "address=" + postalAddress + "\n";

    }

    public static boolean idIsUnique(Integer id, PriorityQueue<Organization> queue) {
        for (Organization organization : queue) {
            if (organization.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    // Сравнение объектов из коллекции по id
    @Override
    public int compareTo(Organization o) {
        return (int) (this.employeesCount - ((Organization) o).employeesCount);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    //Результат положителен если объект больше сравнимаего, отрицателен, если меньше и равен 0, если объекты равны.

}
