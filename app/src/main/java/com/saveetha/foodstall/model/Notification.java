package com.saveetha.foodstall.model;

public class Notification {
    public String type;
    public String title;
    public String description;
    public String location;
    public String time;

    public Notification(String type, String title, String description, String location, String time) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.location = location;
        this.time = time;
    }
}