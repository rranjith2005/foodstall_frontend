package com.saveetha.foodstall.model;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("transaction_type")
    public String title;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("amount")
    public double amount;

    @SerializedName("description")
    public String description;

    // This constructor will be used by Gson to create objects from JSON
    public Transaction(String title, String timestamp, double amount, String description) {
        this.title = title;
        this.timestamp = timestamp;
        this.amount = amount;
        this.description = description;
    }
}