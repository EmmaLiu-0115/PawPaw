package com.example.pawpaw;

public class Friend {
    private String user1ID;
    private String user2ID;

    public Friend(){}

    public Friend(String user1ID, String user2ID) {
        this.user1ID = user1ID;
        this.user2ID = user2ID;
    }

    public String getUser1ID() {
        return user1ID;
    }

    public String getUser2ID() {
        return user2ID;
    }
}
