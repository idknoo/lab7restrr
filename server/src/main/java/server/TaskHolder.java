package server;

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


    public TaskHolder(Message msg, SelectionKey key, CollectionWorkerImpl scw) {
        this.msg = msg;
        this.key = key;
        this.scw = scw;
    }

    /**
     * Метод для исполнения
     */
    @Override
    protected void compute() {
        try {
                if (msg instanceof CommandMessage)
                    key.attach(scw.execute(((CommandMessage) msg).getCommand()));
            key.interestOps(SelectionKey.OP_WRITE);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}