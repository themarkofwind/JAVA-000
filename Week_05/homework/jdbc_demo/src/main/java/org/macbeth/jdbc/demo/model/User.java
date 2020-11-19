package org.macbeth.jdbc.demo.model;

public class User {

    private long userId;

    private String loginName;

    private int loginNameType;

    public User(long userId, String loginName, int loginNameType) {
        this.userId = userId;
        this.loginName = loginName;
        this.loginNameType = loginNameType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getLoginNameType() {
        return loginNameType;
    }

    public void setLoginNameType(int loginNameType) {
        this.loginNameType = loginNameType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", loginNameType=" + loginNameType +
                '}';
    }
}
