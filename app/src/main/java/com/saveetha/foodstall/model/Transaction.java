package com.saveetha.foodstall.model;

public class Transaction {
    public int iconResId;
    public String title;
    public String timestamp;
    public String amount;
    public boolean isCompleted;

    public Transaction(int iconResId, String title, String timestamp, String amount, boolean isCompleted) {
        this.iconResId = iconResId;
        this.title = title;
        this.timestamp = timestamp;
        this.amount = amount;
        this.isCompleted = isCompleted;
    }
}