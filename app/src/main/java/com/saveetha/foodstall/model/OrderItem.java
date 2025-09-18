package com.saveetha.foodstall.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
    private String name;
    private int quantity;
    private double price;
    private boolean isParcel;       // For the simple ₹10 parcel fee
    private boolean isPreParcel;    // For identifying pre-parcel orders
    private String preParcelTime;   // The end-time for pre-parcels

    /**
     * Full constructor for creating an item with all possible options.
     */
    public OrderItem(String name, int quantity, double price, boolean isParcel, boolean isPreParcel, String preParcelTime) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isParcel = isParcel;
        this.isPreParcel = isPreParcel;
        this.preParcelTime = preParcelTime;
    }

    /**
     * A simpler constructor for creating a basic item.
     * Defaults to not being a parcel or pre-parcel.
     */
    public OrderItem(String name, int quantity, double price) {
        this(name, quantity, price, false, false, null);
    }


    // --- Getters and Setters ---
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void incrementQuantity() { this.quantity++; }
    public double getPrice() { return price; }

    public boolean isParcel() { return isParcel; }
    public void setParcel(boolean parcel) { isParcel = parcel; }

    public boolean isPreParcel() { return isPreParcel; }
    public void setPreParcel(boolean preParcel) { isPreParcel = preParcel; }

    public String getPreParcelTime() { return preParcelTime; }
    public void setPreParcelTime(String preParcelTime) { this.preParcelTime = preParcelTime; }


    // --- Parcelable Implementation (Updated for all fields) ---
    protected OrderItem(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
        isParcel = in.readByte() != 0;
        isPreParcel = in.readByte() != 0;
        preParcelTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeByte((byte) (isParcel ? 1 : 0));
        dest.writeByte((byte) (isPreParcel ? 1 : 0));
        dest.writeString(preParcelTime);
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