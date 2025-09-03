package com.saveetha.foodstall.model;

import java.util.List;

/**
 * Represents the entire details of a single order.
 * This class is inferred from its usage in Oview_receiptActivity.
 */
public class DetailedOrder {
    private String orderId;
    private String orderTime;
    private String stallName;
    private String studentId;
    private String ownerId;
    private String paymentMethod;
    private String parcelType; // e.g., "Parcel", "Pre-parcel", "Dine-In"
    private String parcelEndTime;
    private List<ReceiptItem> items;

    public DetailedOrder(String orderId, String orderTime, String stallName, String studentId, String ownerId, String paymentMethod, String parcelType, String parcelEndTime, List<ReceiptItem> items) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.stallName = stallName;
        this.studentId = studentId;
        this.ownerId = ownerId;
        this.paymentMethod = paymentMethod;
        this.parcelType = parcelType;
        this.parcelEndTime = parcelEndTime;
        this.items = items;
    }

    // --- Getters for all fields ---
    public String getOrderId() { return orderId; }
    public String getOrderTime() { return orderTime; }
    public String getStallName() { return stallName; }
    public String getStudentId() { return studentId; }
    public String getOwnerId() { return ownerId; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getParcelType() { return parcelType; }
    public String getParcelEndTime() { return parcelEndTime; }
    public List<ReceiptItem> getItems() { return items; }
}
