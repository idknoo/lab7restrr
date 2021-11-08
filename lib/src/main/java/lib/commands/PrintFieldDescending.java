package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class PrintFieldDescending implements Command{
    public PrintFieldDescending() {
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.printFieldDescending();
    }


}
