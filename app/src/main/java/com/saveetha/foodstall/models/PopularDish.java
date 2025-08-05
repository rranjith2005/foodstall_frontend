package com.saveetha.foodstall.models;

public class PopularDish {
    private String name;
    private String price; // Make sure this is String
    private int imageResId;

    public PopularDish(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
