package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class Exit implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager, User user) {
        return new Message("Оффаем клиент. Тушите свет.");
    }
}
