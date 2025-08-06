package com.saveetha.foodstall.model; // This should be your app's package name followed by .model

/**
 * Data model for a special dish item.
 * This class holds the information for each item displayed in the "Today's Specials" RecyclerView.
 */
public class SpecialDish {

    // Public fields to make them easily accessible.
    // In a production app, you might use private fields with getter methods.
    public String name;
    public String price;
    public int imageResId; // Resource ID for the image (e.g., R.drawable.sd1)
    public String stallName;

    /**
     * Constructor to create a new SpecialDish object.
     *
     * @param name The name of the dish.
     * @param price The price of the dish.
     * @param imageResId The resource ID of the image for the dish.
     * @param stallName The name of the stall selling the dish.
     */
    public SpecialDish(String name, String price, int imageResId, String stallName) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.stallName = stallName;
    }
}