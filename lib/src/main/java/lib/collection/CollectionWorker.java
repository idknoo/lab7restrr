package lib.collection;

import lib.commands.ReadOrganizationOperation;
import lib.commands.User;
import lib.message.Message;
import lib.organization.Organization;

public interface CollectionWorker {

    Message help();

    Message clear(User user);

    Message info();

    Message info(User user);

    Message maxByName(User user);

    Message printFieldDescending(User user);

    Message removeAllByCount(int employeesCount, User user);

    Message removeGreatKey(Organization organization, User user);

    Message removeKey(Integer id, User user);

    Message removeLowerKey(Organization organization, User user);

    Message replaceIfLowe(Organization organization, User user);

//    Message save();

    Message show();

    Message updateId(Organization organization, Integer id, User user);


    Message insert(Organization organization, User user);


}
