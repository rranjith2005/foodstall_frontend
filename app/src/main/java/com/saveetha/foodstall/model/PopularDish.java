package com.saveetha.foodstall.model;

public class PopularDish {
    public String name;
    public String price;
    public int imageResId;

    public PopularDish(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }
}