package com.saveetha.foodstall.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StallMenuResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private StallMenuData data;

    public String getStatus() { return status; }
    public StallMenuData getData() { return data; }

    public static class StallMenuData {
        @SerializedName("stall_details")
        private StallDetails stallDetails;

        @SerializedName("todays_special")
        private MenuItem todaysSpecial;

        @SerializedName("full_menu")
        private List<MenuItem> fullMenu;

        @SerializedName("popular_dish")
        private MenuItem popularDish;

        @SerializedName("reviews")
        private List<Review> reviews;

        // --- START OF ADDED SECTION ---
        @SerializedName("is_favorite")
        private boolean isFavorite;
        // --- END OF ADDED SECTION ---


        public StallDetails getStallDetails() { return stallDetails; }
        public MenuItem getTodaysSpecial() { return todaysSpecial; }
        public List<MenuItem> getFullMenu() { return fullMenu; }
        public MenuItem getPopularDish() { return popularDish; }
        public List<Review> getReviews() { return reviews; }

        // --- START OF ADDED SECTION ---
        // This is the getter method that your activity was missing.
        public boolean isFavorite() { return isFavorite; }
        // --- END OF ADDED SECTION ---
    }

    public static class StallDetails {
        @SerializedName("stallname")
        private String stallName;

        public String getStallName() { return stallName; }
    }
}

