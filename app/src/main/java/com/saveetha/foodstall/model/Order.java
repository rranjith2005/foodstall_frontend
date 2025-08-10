package com.saveetha.foodstall.model;

public class Order {
    public String stallName;
    public String orderId;
    public String orderTime;
    public String orderedItems;
    public String orderStatus;
    public String totalAmount;
    public String pickupTime;

    public Order(String stallName, String orderId, String orderTime, String orderedItems, String orderStatus, String totalAmount, String pickupTime) {
        this.stallName = stallName;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderedItems = orderedItems;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.pickupTime = pickupTime;
    }
}