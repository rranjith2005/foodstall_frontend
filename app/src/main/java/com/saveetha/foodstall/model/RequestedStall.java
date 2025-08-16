package com.saveetha.foodstall.model;

public class RequestedStall {
    public int stallImageResId;
    public String stallName;
    public String ownerName;
    public String requestedDate;

    public RequestedStall(int stallImageResId, String stallName, String ownerName, String requestedDate) {
        this.stallImageResId = stallImageResId;
        this.stallName = stallName;
        this.ownerName = ownerName;
        this.requestedDate = requestedDate;
    }
}