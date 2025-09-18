package com.saveetha.foodstall.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("role")
    private String role; // Will be "student", "owner_approved", etc.

    @SerializedName("data")
    private UserData data; // A nested object for user details

    // Getters
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public String getRole() { return role; }
    public UserData getData() { return data; }

    // This nested class now contains ALL possible fields for any user type
    public static class UserData {
        @SerializedName("id")
        private int id;

        @SerializedName("fullname")
        private String fullname;

        @SerializedName("email")
        private String email;

        // --- THIS FIELD WAS MISSING ---
        @SerializedName("student_id")
        private String studentId;

        // --- THESE FIELDS WERE FOR OWNERS ---
        @SerializedName("phonenumber")
        private String phonenumber;

        @SerializedName("stall_status")
        private int stallStatus;

        @SerializedName("stall_id")
        private String stallId;

        @SerializedName("stall_name")
        private String stallName;

        @SerializedName("rejection_reason")
        private String rejectionReason;

        // Getters for all fields
        public int getId() { return id; }
        public String getFullname() { return fullname; }
        public String getEmail() { return email; }
        public String getStudentId() { return studentId; }
        public String getPhonenumber() { return phonenumber; }
        public int getStallStatus() { return stallStatus; }
        public String getStallId() { return stallId; }
        public String getStallName() { return stallName; }
        public String getRejectionReason() { return rejectionReason; }
    }
}
