package com.saveetha.foodstall.model;

public class OrderItem {
    public String name;
    public int quantity;
    public double price;
    public boolean isPreParcel;
    public String preParcelTime; // New field for pre-parcel time

    public OrderItem(String name, int quantity, double price, boolean isPreParcel, String preParcelTime) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isPreParcel = isPreParcel;
        this.preParcelTime = preParcelTime;
    }
}