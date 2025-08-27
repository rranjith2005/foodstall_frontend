package com.saveetha.foodstall.model;

public class FavoriteStall {
    private String name;
    private int imageResId;

    public FavoriteStall(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
}