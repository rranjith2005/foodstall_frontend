package com.saveetha.foodstall.models;

public class SpecialItem {
    private String name;
    private String price;
    private String stallName;
    private int imageResId;

    public SpecialItem(String name, String price, String stallName, int imageResId) {
        this.name = name;
        this.price = price;
        this.stallName = stallName;
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getStallName() {
        return stallName;
    }
}
