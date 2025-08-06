package com.saveetha.foodstall.model;

/**
 * Data model for a menu item.
 */
public class MenuItem {
    public String name;
    public String price;
    public int imageResId;

    public MenuItem(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }
}