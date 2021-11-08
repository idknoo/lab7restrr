package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class Info implements Command {

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.info();
    }
}

