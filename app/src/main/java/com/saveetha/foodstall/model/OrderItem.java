package com.saveetha.foodstall.model;

public class OrderItem {
    public String name;
    public int quantity;
    public int price;
    public boolean isPreParcel;
    public String pickupTime;

    public OrderItem(String name, int quantity, int price, boolean isPreParcel, String pickupTime) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isPreParcel = isPreParcel;
        this.pickupTime = pickupTime;
    }
}