package edu.cs.scu.entity;

/**
 * Created by maicius on 2017/3/29.
 */
public class User {
    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
}
