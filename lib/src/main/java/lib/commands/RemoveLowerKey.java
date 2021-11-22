package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class RemoveLowerKey implements Command {
    private final Integer id;

    public RemoveLowerKey(Integer id) {
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.removeLowerKey(id);
    }
}
