package com.saveetha.foodstall.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Order implements Parcelable {
    private String stallName;
    private String orderId;
    private String orderTime;
    private String status;
    private double totalPrice;
    private ArrayList<OrderItem> orderedItems;

    public Order(String stallName, String orderId, String orderTime, String status, double totalPrice, ArrayList<OrderItem> orderedItems) {
        this.stallName = stallName;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderedItems = orderedItems;
    }

    // --- Getters ---
    public String getStallName() { return stallName; }
    public String getOrderId() { return orderId; }
    public String getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }
    public ArrayList<OrderItem> getOrderedItems() { return orderedItems; }

    // --- Parcelable Implementation ---
    protected Order(Parcel in) {
        stallName = in.readString();
        orderId = in.readString();
        orderTime = in.readString();
        status = in.readString();
        totalPrice = in.readDouble();
        orderedItems = in.createTypedArrayList(OrderItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stallName);
        dest.writeString(orderId);
        dest.writeString(orderTime);
        dest.writeString(status);
        dest.writeDouble(totalPrice);
        dest.writeTypedList(orderedItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}