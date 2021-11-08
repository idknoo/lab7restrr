package lib.organization;

import java.io.Serializable;

/**
 * класс координат
 */
public class Address implements Serializable {

    /**
     * Поле координаты x,
     */
    private String street;

    /**
     * Поле координаты y
     */
    private Location town;

    public Address() {}


    public Address(String street, Location town) {
        this.street = street;
        this.town = town;
    }

    /**
     * Метод для получения координаты y
     *
     * @return возвращает значение координаты y
     */
    public String getStreet() {
        return street;
    }

    /**
     * Метод для получения координаты x
     *
     * @return возвращает значение координаты x
     */
    public Location getTown() {
        return town;
    }

    /**
     * Метод для получения координат организации в строчной форме в формате "X = A Y = B"
     *
     * @return возвращает строку координат в формате "A B"
     */
    @Override
    public String toString() {
        return "Address:" +
                "street='" + street + '\'' + "\n" +
                "town=" + town;
    }
}
