package com.saveetha.foodstall.model;

public class OwnerOrderPending {
    public String orderId;
    public String stallName;
    public String stallLocation;
    public String orderedItems;
    public String totalAmount;
    public String timeAgo;
    public String orderedAtTime;

    public OwnerOrderPending(String orderId, String stallName, String stallLocation, String orderedItems, String totalAmount, String timeAgo, String orderedAtTime) {
        this.orderId = orderId;
        this.stallName = stallName;
        this.stallLocation = stallLocation;
        this.orderedItems = orderedItems;
        this.totalAmount = totalAmount;
        this.timeAgo = timeAgo;
        this.orderedAtTime = orderedAtTime;
    }
}
