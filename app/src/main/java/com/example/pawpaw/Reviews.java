package com.example.pawpaw;

public class Reviews {
    private String userID;
    private String locationID;
    private double price;
    private double rating;
    private String review;
    private String photo;

    public Reviews(){

    }

    public Reviews(String userID, String locationID, double price, double rating, String review, String photo){
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

    public double getPrice() {
        return price;
    }

    public double getRating() {
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
