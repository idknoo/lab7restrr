package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;
import lib.organization.Organization;

public class ReplaceIfLowe implements Command {
    private final Organization organization;

    public ReplaceIfLowe(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        return collectionWorker.replaceIfLowe(organization, user);
    }
}
