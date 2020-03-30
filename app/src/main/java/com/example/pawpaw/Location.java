package com.example.pawpaw;

import java.util.List;

public class Location {
    private String locationID;
    private String locationName;
    private String locationType;
    private String locationAddress;
    private double avgPrice;
    private double avgRating;
    private List<String> photos;

    public Location (){

    }

    public Location (String locationID, String locationName, String locationType, String locationAddress,
                    double avgPrice, double avgRating, List<String> photos){
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationType = locationType;
        this.avgPrice = avgPrice;
        this.avgRating = avgRating;
        this.locationAddress = locationAddress;
        this.photos = photos;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationType() {
        return locationType;
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
}
