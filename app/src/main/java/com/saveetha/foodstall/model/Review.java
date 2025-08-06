package com.saveetha.foodstall.model;

/**
 * Data model for a customer review.
 */
public class Review {
    public String name;
    public String text;
    public String time;
    public String rating;
    public int profileImageResId;

    public Review(String name, String text, String time, String rating, int profileImageResId) {
        this.name = name;
        this.text = text;
        this.time = time;
        this.rating = rating;
        this.profileImageResId = profileImageResId;
    }
}