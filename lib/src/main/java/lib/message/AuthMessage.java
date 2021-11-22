package lib.message;

import lib.commands.User;

public class AuthMessage extends Message {
    private User user;
    private boolean isRegister;
    public AuthMessage(User user, boolean isRegister){
        super("ЬЬЬ");
        this.isRegister=isRegister;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public boolean getBoolean() {
        return isRegister;
    }
}
