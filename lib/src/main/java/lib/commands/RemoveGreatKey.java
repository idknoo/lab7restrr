package lib.commands;

import lib.collection.CollectionWorker;
import lib.message.Message;
import lib.organization.Organization;

public class RemoveGreatKey implements Command {
    private final Organization organization;

    public RemoveGreatKey(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker, User user) {
        organization.setOwnerName(user.getName());
        return collectionWorker.removeGreatKey(organization, user);
    }
}
