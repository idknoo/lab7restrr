package lib.collection;

import lib.commands.ReadOrganizationOperation;
import lib.commands.User;
import lib.message.Message;
import lib.organization.Organization;

public interface CollectionWorker {

    Message help();

    Message clear();

    Message info();

    Message info(User user);

    Message maxByName();

    Message printFieldDescending();

    Message removeAllByCount(int employeesCount);

    Message removeGreatKey(Integer id);

    Message removeKey(Integer id);

    Message removeLowerKey(Integer id);

    Message replaceIfLowe(Organization organization);

    Message save();

    Message show();

    Message updateId(ReadOrganizationOperation readOrganizationOperation, Integer id, User user);


    Message insert(Organization organization, User user);


}
