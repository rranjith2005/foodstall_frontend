package com.saveetha.foodstall.model;

public class TopPerformer {
    public String dishName;
    public int unitsSold;
    public String revenue;
    public int dishImageResId;
    public boolean isTrendingUp;
    public boolean isTopSeller;
    public boolean isLowestSeller;

    public TopPerformer(String dishName, int unitsSold, String revenue, int dishImageResId, boolean isTrendingUp, boolean isTopSeller, boolean isLowestSeller) {
        this.dishName = dishName;
        this.unitsSold = unitsSold;
        this.revenue = revenue;
        this.dishImageResId = dishImageResId;
        this.isTrendingUp = isTrendingUp;
        this.isTopSeller = isTopSeller;
        this.isLowestSeller = isLowestSeller;
    }
}