package com.saveetha.foodstall.model;

public class RevenueDetail {
    public String date;
    public String orders;
    public String revenue;

    public RevenueDetail(String date, String orders, String revenue) {
        this.date = date;
        this.orders = orders;
        this.revenue = revenue;
    }
}