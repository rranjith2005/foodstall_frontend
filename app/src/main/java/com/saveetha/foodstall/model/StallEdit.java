package com.saveetha.foodstall.model;

public class StallEdit {
    private int imageResId; // Add this field
    private String stallName;
    private String ownerId;
    private String ownerName;
    private String latitude;
    private String longitude;

    // Update the constructor
    public StallEdit(int imageResId, String stallName, String ownerId, String ownerName, String latitude, String longitude) {
        this.imageResId = imageResId;
        this.stallName = stallName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Add the getter for the image
    public int getImageResId() {
        return imageResId;
    }

    // Existing getters
    public String getStallName() { return stallName; }
    public String getOwnerId() { return ownerId; }
    public String getOwnerName() { return ownerName; }
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
}