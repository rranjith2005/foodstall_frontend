package com.saveetha.foodstall.model;


public class Stall {
    public String name;
    public String rating;
    public String status;
    public String timings;
    public int imageResId;

    public Stall(String name, String rating, String status, String timings, int imageResId) {
        this.name = name;
        this.rating = rating;
        this.status = status;
        this.timings = timings;
        this.imageResId = imageResId;
    }
}