package com.saveetha.foodstall.model;

/**
 * Represents a single item within a receipt.
 * This class is updated to include the 'isParcel' flag to correctly calculate fees.
 */
public class ReceiptItem {
    private String name;
    private int quantity;
    private double price;
    private boolean isParcel; // NEW: Flag to indicate if this item has a parcel charge

    public ReceiptItem(String name, int quantity, double price, boolean isParcel) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isParcel = isParcel;
    }

    // --- Getters for all fields ---
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public boolean isParcel() { return isParcel; } // NEW: Getter for the flag
}
