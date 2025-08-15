package com.saveetha.foodstall.model;

public class ReportItem {
    public String rank;
    public String stallName;
    public String stallId;
    public String totalRevenue;
    public String bestSellingItem;

    public ReportItem(String rank, String stallName, String stallId, String totalRevenue, String bestSellingItem) {
        this.rank = rank;
        this.stallName = stallName;
        this.stallId = stallId;
        this.totalRevenue = totalRevenue;
        this.bestSellingItem = bestSellingItem;
    }
}