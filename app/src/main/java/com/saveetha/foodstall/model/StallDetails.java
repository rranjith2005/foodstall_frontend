package com.saveetha.foodstall.model;

public class StallDetails {
    public String stallName;
    public String ownerName;
    public String fssaiNumber;
    public String phoneNumber;
    public String address;
    public String dateRequested;
    public int imageResId;

    public StallDetails(String stallName, String ownerName, String fssaiNumber, String phoneNumber, String address, String dateRequested, int imageResId) {
        this.stallName = stallName;
        this.ownerName = ownerName;
        this.fssaiNumber = fssaiNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateRequested = dateRequested;
        this.imageResId = imageResId;
    }
}