package com.saveetha.foodstall.model;

public class NeedsAttentionItem {
    public String dishName;
    public int unitsSold;
    public String revenue;

    public NeedsAttentionItem(String dishName, int unitsSold, String revenue) {
        this.dishName = dishName;
        this.unitsSold = unitsSold;
        this.revenue = revenue;
    }
}