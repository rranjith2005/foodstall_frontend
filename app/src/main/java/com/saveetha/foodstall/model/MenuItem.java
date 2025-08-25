package com.saveetha.foodstall.model;

public class MenuItem {
    private String name;
    private double price;
    private int imageResId;

    public MenuItem(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    // --- ADD THIS PUBLIC METHOD ---
    public int getImageResId() {
        return imageResId;
    }
}