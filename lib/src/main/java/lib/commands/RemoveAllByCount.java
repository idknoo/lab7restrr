package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class RemoveAllByCount implements Command {
    private final int employeesCount;

    public RemoveAllByCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.removeAllByCount(employeesCount, user);
    }
}

