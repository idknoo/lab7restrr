package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;
import lib.organization.Organization;

public class Insert implements Command {
    private final Organization organization;

    public Insert(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.insert(organization);
    }
}
