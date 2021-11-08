package lib.organization;

import java.io.Serializable;

/**
 * класс координат
 */
public class Coordinates implements Serializable {

    /**
     * Поле координаты x,
     */
    private double x;

    /**
     * Поле координаты y
     */
    private float y;

    public Coordinates() {}

    /**
     * Конструктор для задания координат x и y
     *
     * @param x - координата x
     * @param y - координата y
     */
    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для получения координаты y
     *
     * @return возвращает значение координаты y
     */
    public float getY() {
        return y;
    }

    /**
     * Метод для получения координаты x
     *
     * @return возвращает значение координаты x
     */
    public double getX() {
        return x;
    }

    /**
     * Метод для получения координат организации в строчной форме в формате "X = A Y = B"
     *
     * @return возвращает строку координат в формате "A B"
     */
    @Override
    public String toString() {
        return x +" "+ y;
    }

}
