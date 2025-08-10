package com.saveetha.foodstall.model;

public class Stall {
    public String stallName;
    public String rating;
    public String timings;
    public boolean isOpen;
    public int imageResId;

    public Stall(String stallName, String rating, String timings, boolean isOpen, int imageResId) {
        this.stallName = stallName;
        this.rating = rating;
        this.timings = timings;
        this.isOpen = isOpen;
        this.imageResId = imageResId;
    }
}