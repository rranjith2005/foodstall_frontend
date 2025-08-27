package com.saveetha.foodstall.model;

public class Review {
    // --- Fields for BOTH UIs ---
    private String name; // Used as reviewerName
    private String text;
    private String time; // Can also be used for the date
    private String ratingString; // For the old UI's TextView
    private float ratingFloat;   // For the new UI's RatingBar

    // --- Fields for specific UIs ---
    private int profileImageResId; // Only for the old UI
    private String stallName;      // Only for the new UI

    // --- CONSTRUCTOR for OLD UI data ---
    public Review(String name, String text, String time, String rating, int profileImageResId) {
        this.name = name;
        this.text = text;
        this.time = time;
        this.ratingString = rating;
        this.profileImageResId = profileImageResId;
    }

    // --- CONSTRUCTOR for NEW UI data ---
    public Review(String stallName, String name, float rating, String text, String date) {
        this.stallName = stallName;
        this.name = name; // 'name' is used for reviewerName
        this.ratingFloat = rating;
        this.text = text;
        this.time = date; // 'time' is used for the date
    }

    // --- Getters for all possible fields ---
    public String getName() { return name; }
    public String getText() { return text; }
    public String getTime() { return time; }
    public String getRatingString() { return ratingString; }
    public int getProfileImageResId() { return profileImageResId; }
    public String getStallName() { return stallName; }
    public float getRatingFloat() { return ratingFloat; }
}