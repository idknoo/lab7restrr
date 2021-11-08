package lib.message;

import lib.organization.Organization;

public class CollectionMessage extends Message{
    private final Organization[] organizations;

    public Organization[] getOrganizations() {
        return organizations;
    }
    public CollectionMessage(String toString, Organization[] toArray) {
        super(toString);
        this.organizations = toArray;
    }
}
