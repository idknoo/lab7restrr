package lib.exception;

/**
 * класс исключения
 */
public class NullValueException extends Exception {

    public NullValueException(String message) {
        super(message);
    } //Конструктор, позволяющий задать сообщение для исключения.
}
