package lib.exception;

/**
 * класс исключения
 */
public class InvalidValueException extends Exception {

    public InvalidValueException(String message) {
        super(message);
    } //Конструктор, позволяющий задать сообщение для исключения.
}
