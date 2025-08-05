package com.saveetha.foodstall.models;

public class Stall {
    private String name;
    private float rating;
    private String timings;
    private boolean isOpen;
    private int imageResId;

    public Stall(String name, float rating, String timings, boolean isOpen, int imageResId) {
        this.name = name;
        this.rating = rating;
        this.timings = timings;
        this.isOpen = isOpen;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public String getTimings() {
        return timings;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getImageResId() {
        return imageResId;
    }
}
