package com.saveetha.foodstall.model;

public class StallAdmin {
    public int stallImageResId;
    public String stallName;
    public String ownerName;
    public String status;

    public StallAdmin(int stallImageResId, String stallName, String ownerName, String status) {
        this.stallImageResId = stallImageResId;
        this.stallName = stallName;
        this.ownerName = ownerName;
        this.status = status;
    }
}