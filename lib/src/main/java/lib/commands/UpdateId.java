package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;

public class UpdateId implements Command {
    private final ReadOrganizationOperation readOrganizationOperation;
    private final Integer id;

    public UpdateId(ReadOrganizationOperation readOrganizationOperation, Integer id){
        this.readOrganizationOperation = readOrganizationOperation;
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.updateId(readOrganizationOperation, id);
    }
}


