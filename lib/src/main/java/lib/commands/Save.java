package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class Save implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager) {
        return collectionManager.save();
    }
}
