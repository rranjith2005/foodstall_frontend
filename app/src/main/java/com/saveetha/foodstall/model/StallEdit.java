package com.saveetha.foodstall.model;

public class StallEdit {
    public String stallName;
    public String ownerId;
    public String ownerName;
    public String latitude;
    public String longitude;
    public boolean isClosed;
    public String closeReason;

    public StallEdit(String stallName, String ownerId, String ownerName, String latitude, String longitude) {
        this.stallName = stallName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isClosed = false;
        this.closeReason = "";
    }
}