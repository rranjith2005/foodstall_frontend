package com.saveetha.foodstall.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the JSON response from the Odashboard_data.php script.
 */
public class DashboardResponse {

    @SerializedName("status")
    private String status;

    // The "data" object in the JSON will be mapped to this nested class
    @SerializedName("data")
    private DashboardData data;

    // Getters for the main response fields
    public String getStatus() {
        return status;
    }

    public DashboardData getData() {
        return data;
    }

    /**
     * This nested class holds the actual dashboard metrics.
     */
    public static class DashboardData {

        @SerializedName("orders_today")
        private int ordersToday;

        @SerializedName("revenue_today")
        private double revenueToday;

        @SerializedName("pending_orders")
        private int pendingOrders;

        @SerializedName("top_selling")
        private String topSelling;

        // Getters for the metric fields
        public int getOrdersToday() {
            return ordersToday;
        }

        public double getRevenueToday() {
            return revenueToday;
        }

        public int getPendingOrders() {
            return pendingOrders;
        }

        public String getTopSelling() {
            return topSelling;
        }
    }
}