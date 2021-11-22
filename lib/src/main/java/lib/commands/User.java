package lib.commands;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String password;

    public User(String name, String password) {
        if (name == null || password == null)
            throw new NullPointerException("null name or password");

        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}