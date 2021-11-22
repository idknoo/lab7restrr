package lib.message;

import lib.commands.Command;
import lib.commands.User;

public class CommandMessage extends Message {
    private final Command command;
    private final User user;

    public CommandMessage(String content, Command command,  User user) {
        super(content);
        if (command == null)
            throw new NullPointerException();
        this.command=command;
        this.user=user;
    }
    public Command getCommand() {
        return command;
    }
    public User getUser() {
        return user;
    }
}