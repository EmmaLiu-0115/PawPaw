package com.example.pawpaw;

public class Reviews {
    private String userID;
    private String locationID;
    private int price;
    private int rating;
    private String review;
    private String photo;

    public Reviews(){

    }

    public Reviews(String userID, String locationID, int price, int rating, String review, String photo){
        this.locationID = locationID;
        this.photo = photo;
        this.price = price;
        this.rating = rating;
        this.userID = userID;
        this.review = review;
    }

    public String getLocationID() {
        return locationID;
    }

    public int getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public String getPhoto() {
        return photo;
    }

    public String getReview() {
        return review;
    }

    public String getUserID() {
        return userID;
    }
}
