package com.saveetha.foodstall.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

// IMPLEMENTS Serializable allows you to pass this object between activities
public class StallDetails implements Serializable {

    // These annotations tell the app how to match the JSON from your PHP script
    // to the variables in this class. Your backend variable names can stay the same.
    @SerializedName("stallname")
    public String stallName;

    @SerializedName("ownername")
    public String ownerName;

    @SerializedName("fssainumber")
    public String fssaiNumber;

    @SerializedName("phonenumber")
    public String phoneNumber;

    @SerializedName("fulladdress")
    public String address;

    // We don't expect these from the server list, so no annotation is needed
    public String dateRequested;
    public int imageResId;

    // Your existing constructor is untouched and will continue to work
    public StallDetails(String stallName, String ownerName, String fssaiNumber, String phoneNumber, String address, String dateRequested, int imageResId) {
        this.stallName = stallName;
        this.ownerName = ownerName;
        this.fssaiNumber = fssaiNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateRequested = dateRequested;
        this.imageResId = imageResId;
    }

    // ADDED: A no-argument constructor is required by libraries like Gson/Retrofit.
    public StallDetails() {}

    // ADDED: Getters are good practice and are needed by other parts of the code.
    public String getStallName() { return stallName; }
    public String getOwnerName() { return ownerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() {
        // Assuming your backend might not send an email in this list,
        // we can add a placeholder or handle it.
        // For now, let's just return a default if it's needed elsewhere.
        // If your StallDetailsActivity needs the email, it must be sent from the server.
        return "Not available";
    }
    public String getFullAddress() { return address; }
    public String getFssaiNumber() { return fssaiNumber; }
}