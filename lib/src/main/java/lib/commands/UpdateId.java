package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;
import lib.organization.Organization;

public class UpdateId implements Command {
    private final Organization organization;
    private final Integer id;

    public UpdateId(Organization organization, Integer id){
        this.organization = organization;
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.updateId(organization, id, user);
    }
}


