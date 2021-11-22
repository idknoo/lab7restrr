package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class RemoveKey implements Command {
    private final Integer id;

    public RemoveKey(Integer id) {
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.removeKey(id);
    }
}
