package com.saveetha.foodstall.model;

public class NotificationAdmin {
    public String title;
    public String message;
    public String time;
    public boolean hasNewStatus;

    public NotificationAdmin(String title, String message, String time, boolean hasNewStatus) {
        this.title = title;
        this.message = message;
        this.time = time;
        this.hasNewStatus = hasNewStatus;
    }
}