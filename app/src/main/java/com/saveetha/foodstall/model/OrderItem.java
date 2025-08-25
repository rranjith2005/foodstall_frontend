package com.saveetha.foodstall.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
    private String name;
    private int quantity;
    private double price;
    private boolean isPreParcel;
    private String preParcelTime;
    private boolean isParcel; // NEW FIELD for the simple parcel switch

    public OrderItem(String name, int quantity, double price, boolean isPreParcel, String preParcelTime) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isPreParcel = isPreParcel;
        this.preParcelTime = preParcelTime;
        this.isParcel = false; // Default to false
    }

    // --- Getters and Setters ---
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void incrementQuantity() { this.quantity++; }
    public double getPrice() { return price; }
    public boolean isPreParcel() { return isPreParcel; }
    public void setPreParcel(boolean preParcel) { isPreParcel = preParcel; }
    public String getPreParcelTime() { return preParcelTime; }
    public void setPreParcelTime(String preParcelTime) { this.preParcelTime = preParcelTime; }
    public boolean isParcel() { return isParcel; } // NEW GETTER
    public void setParcel(boolean parcel) { isParcel = parcel; } // NEW SETTER


    // --- Parcelable Implementation (Updated) ---
    protected OrderItem(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
        isPreParcel = in.readByte() != 0;
        preParcelTime = in.readString();
        isParcel = in.readByte() != 0; // UPDATED
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeByte((byte) (isPreParcel ? 1 : 0));
        dest.writeString(preParcelTime);
        dest.writeByte((byte) (isParcel ? 1 : 0)); // UPDATED
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}