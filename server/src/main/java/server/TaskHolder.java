package server;

import lib.message.AuthMessage;
import lib.message.AuthResult;
import lib.message.CommandMessage;
import lib.message.Message;

import java.nio.channels.SelectionKey;
import java.util.concurrent.RecursiveAction;

public class TaskHolder extends RecursiveAction {

    /**
     * Пакет
     */
    private Message msg;

    /**
     * Ключ
     */
    private SelectionKey key;

    /**
     * Интерпертатор серевра
     */
    private CollectionWorkerImpl scw;
    private Database db;

    public TaskHolder(Message msg, SelectionKey key, CollectionWorkerImpl scw, Database db) {
        this.msg = msg;
        this.key = key;
        this.scw = scw;
        this.db = db;
    }

    /**
     * Метод для исполнения
     */
    @Override
    protected void compute() {
        try {
            if (msg instanceof AuthMessage) {
                if (((AuthMessage) msg).getBoolean()) {
                    try {
                        db.registerUser(((AuthMessage) msg).getUser());
                        key.attach(new AuthResult("Пользователь зарегестрирован.", true));
                    } catch (UserAlreadyExistsException e) {
                        key.attach(new AuthResult("Пользователь уже существует. Повторите ввод.", false));
                    }

                } else {
                    if (!((AuthMessage) msg).getBoolean()) {
                        try {
                            db.authorizeUser(((AuthMessage) msg).getUser());
                            key.attach(new AuthResult("Вход выполнен", true));
                        } catch (InvalidUsernameOrPasswordException e) {
                            key.attach(new AuthResult("Неверный логин или пароль. Повторите ввод.", false));
                        }

                    }
                }
            } else {
                if (msg instanceof CommandMessage)
                    key.attach(scw.execute(((CommandMessage) msg).getCommand(), ((CommandMessage) msg).getUser()));
            }
            key.interestOps(SelectionKey.OP_WRITE);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}