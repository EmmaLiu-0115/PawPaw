package com.example.pawpaw;

public class User {
    private String userID;
    private String name;
    private String area;
    private String password;
    private String phoneNumber;
    private String intro;
    private int privacy;
    private String image;


    public User(){
    }

    public User(String userID, String name, String area, String password, String phoneNumber,
                String intro, int privacy, String image){
        this.userID = userID;
        this.name = name;
        this.area = area;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.intro = intro;
        this.privacy = privacy;
        this.image = image;
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

    public int getPrivacy() {
        return privacy;
    }

    public String getImage() {
        return image;
    }
}
