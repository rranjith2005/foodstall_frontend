package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView; // <-- ADD THIS LINE
import android.widget.TextView;
import android.widget.Toast;

import com.saveetha.foodstall.model.StallDetails;

public class Astall_detailsActivity extends AppCompatActivity {

    private StallDetails stallDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.astall_details);

        // Get dummy data for the stall
        stallDetails = getDummyStallDetails();

        // Set up the UI with the stall details
        populateStallDetails();

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the Approve and Reject buttons
        Button approveButton = findViewById(R.id.approveButton);
        Button rejectButton = findViewById(R.id.rejectButton);

        approveButton.setOnClickListener(v -> {
            Toast.makeText(this, stallDetails.stallName + " approved!", Toast.LENGTH_SHORT).show();
            // Add your logic to approve the stall here
        });

        rejectButton.setOnClickListener(v -> {
            Toast.makeText(this, stallDetails.stallName + " rejected.", Toast.LENGTH_SHORT).show();
            // Add your logic to reject the stall here
        });
    }

    private StallDetails getDummyStallDetails() {
        return new StallDetails(
                "Street Food Corner",
                "John Smith",
                "12345678901234",
                "+1 234-567-8900",
                "Block A, University Campus, Street Name, City, State, ZIP",
                "Oct 15, 2023",
                R.drawable.stall_sample1
        );
    }

    private void populateStallDetails() {
        TextView stallNameValue = findViewById(R.id.stallNameValue);
        TextView ownerNameValue = findViewById(R.id.ownerNameValue);
        TextView fssaiNumberValue = findViewById(R.id.fssaiNumberValue);
        TextView phoneNumberValue = findViewById(R.id.phoneNumberValue);
        TextView addressValue = findViewById(R.id.addressValue);
        TextView dateRequestedValue = findViewById(R.id.dateRequestedValue);
        ImageView stallDetailImage = findViewById(R.id.stallDetailImage);

        stallNameValue.setText(stallDetails.stallName);
        ownerNameValue.setText(stallDetails.ownerName);
        fssaiNumberValue.setText(stallDetails.fssaiNumber);
        phoneNumberValue.setText(stallDetails.phoneNumber);
        addressValue.setText(stallDetails.address);
        dateRequestedValue.setText(stallDetails.dateRequested);
        stallDetailImage.setImageResource(stallDetails.imageResId);
    }
}