package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class RemoveGreatKey implements Command {
    private final Integer id;

    public RemoveGreatKey(Integer id) {
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.removeGreatKey(id);
    }
}
