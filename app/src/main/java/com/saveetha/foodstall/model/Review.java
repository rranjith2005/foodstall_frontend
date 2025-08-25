package com.saveetha.foodstall.model;

public class Review {
    // Variables are now private
    private String name;
    private String text;
    private String time;
    private String rating;
    private int profileImageResId;

    public Review(String name, String text, String time, String rating, int profileImageResId) {
        this.name = name;
        this.text = text;
        this.time = time;
        this.rating = rating;
        this.profileImageResId = profileImageResId;
    }

    // Public "getters" to access the private data
    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getRating() {
        return rating;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }
}