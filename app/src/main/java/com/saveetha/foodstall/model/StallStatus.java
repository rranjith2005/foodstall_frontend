package com.saveetha.foodstall.model;

public class StallStatus {
    public int stallImageResId;
    public String stallName;
    public String ownerName;
    public String status;
    public String reason;

    public StallStatus(int stallImageResId, String stallName, String ownerName, String status, String reason) {
        this.stallImageResId = stallImageResId;
        this.stallName = stallName;
        this.ownerName = ownerName;
        this.status = status;
        this.reason = reason;
    }
}