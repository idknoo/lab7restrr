package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class CommandError implements Command {

    private String message;

    public CommandError(String message){
        this.message = message;
    }

    @Override
    public Message execute(CollectionWorker collectionManager, User user) {
        return new Message(message);
    }
}
