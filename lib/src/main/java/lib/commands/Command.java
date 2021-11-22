package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

import java.io.Serializable;

public interface Command extends Serializable {
    Message execute(CollectionWorker collectionManager, User user);
}
