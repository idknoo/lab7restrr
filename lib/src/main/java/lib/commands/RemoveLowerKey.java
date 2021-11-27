package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;
import lib.organization.Organization;

public class RemoveLowerKey implements Command {
    private final Organization organization;

    public RemoveLowerKey(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        organization.setOwnerName(user.getName());
        return collectionWorker.removeLowerKey(organization, user);
    }
}
