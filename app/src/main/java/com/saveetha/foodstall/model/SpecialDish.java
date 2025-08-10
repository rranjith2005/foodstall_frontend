package com.saveetha.foodstall.model;

public class SpecialDish {
    public String dishName;
    public String stallName;
    public String price;
    public int imageResId;

    public SpecialDish(String dishName, String stallName, String price, int imageResId) {
        this.dishName = dishName;
        this.stallName = stallName;
        this.price = price;
        this.imageResId = imageResId;
    }
}