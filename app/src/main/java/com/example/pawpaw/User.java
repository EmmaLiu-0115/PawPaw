package com.example.pawpaw;

public class User {
    private String userID;
    private String name;
    private String area;
    private String password;
    private int phoneNumber;
    private String intro;

    public User(){
    }

    public User(String userID, String name, String area, String password, int phoneNumber, String intro){
        this.userID = userID;
        this.name = name;
        this.area = area;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.intro = intro;
    }

    public String getUserID (){
        return userID;
    }

    public String getName (){
        return name;
    }

    public String getArea(){
        return area;
    }

    public String getPassword(){
        return password;
    }

    public String getIntro(){
        return intro;
    }

}
