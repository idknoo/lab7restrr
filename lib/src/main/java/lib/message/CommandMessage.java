package lib.message;

import lib.commands.Command;

public class CommandMessage extends Message {
    private final Command command;

    public CommandMessage(String content, Command command) {
        super(content);
        if (command == null)
            throw new NullPointerException();
        this.command=command;
    }

    public Command getCommand() {
        return command;
    }
}

