package com.saveetha.foodstall.model;

public class PopularDish {
    public String dishName;
    public String price;
    public int imageResId;

    public PopularDish(String dishName, String price, int imageResId) {
        this.dishName = dishName;
        this.price = price;
        this.imageResId = imageResId;
    }
}