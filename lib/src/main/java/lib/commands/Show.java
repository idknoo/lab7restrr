package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class Show implements Command{
    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.show();
    }
}

