package com.example.pawpaw;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private String locationID;
    private String locationName;
    private String locationType;
    private String locationAddress;
    private double avgPrice;
    private double avgRating;
    private double latitude;
    private double longitude;
    private List<String> photos;
    private List<String> reviewedUsers;
    private List<String> reviewedUsersContent;

    public Location (){

    }

    public Location (String locationID, String locationName, String locationType, String locationAddress,
                    double avgPrice, double avgRating, double longitude, double latitude, List<String> photos, List<String> reviewedUsers,List<String> reviewedUsersContent){
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationType = locationType;
        this.avgPrice = avgPrice;
        this.avgRating = avgRating;
        this.locationAddress = locationAddress;
        this.photos = photos;
        this.reviewedUsers = reviewedUsers;
        this.reviewedUsersContent = reviewedUsersContent;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public List<String> getReviewedUsers() {
        return reviewedUsers;
    }

    public void setReviewedUsers(List<String> reviewedUsers) {
        this.reviewedUsers = reviewedUsers;
    }

    public List<String> getReviewedUsersContent() {
        return reviewedUsersContent;
    }

    public void setReviewedUsersContent(List<String> reviewedUsersContent) {
        this.reviewedUsersContent = reviewedUsersContent;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }



    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location l = (Location) o;
        boolean same = true;
        for (int i = 0; i<this.getPhotos().size(); i++) {
            if (!this.getPhotos().get(i).equals(l.getPhotos().get(i))){
                same = false;
                break;
            }
        }

        return locationID.equals(l.locationID) &&
                locationType.equals(l.locationType) &&
                locationName.equals(l.locationName) &&
                locationAddress.equals(l.locationAddress) &&
                avgPrice == l.avgPrice &&
                avgRating == l.avgRating &&
                same
                ;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
