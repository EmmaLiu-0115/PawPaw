package com.example.pawpaw;

import java.util.List;

public class Friend {
    private String user1ID;
    private List<String> user2IDs;

    public Friend(){}

    public Friend(String user1ID, List<String> user2IDs) {
        this.user1ID = user1ID;
        this.user2IDs = user2IDs;
    }

    public String getUser1ID() {
        return user1ID;
    }

    public List<String> getUser2IDs() {
        return user2IDs;
    }
}
