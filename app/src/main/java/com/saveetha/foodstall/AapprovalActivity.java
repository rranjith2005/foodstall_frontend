package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AapprovalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aapproval);

        // Find all the views from the layout
        ImageView backButton = findViewById(R.id.backButton);
        TextView approvalMessage = findViewById(R.id.approvalMessage);
        TextView ownerIdTextView = findViewById(R.id.ownerIdTextView);
        Button backToListButton = findViewById(R.id.backToListButton);

        // Get the data that was passed from Astall_detailsActivity
        Intent intent = getIntent();
        String stallName = intent.getStringExtra("STALL_NAME");
        String ownerId = intent.getStringExtra("OWNER_ID");

        // Use the data to set the text on the screen
        approvalMessage.setText("Stall '" + stallName + "' Approved Successfully!");
        ownerIdTextView.setText("Owner ID: " + ownerId);

        // Set click listeners for the buttons
        backButton.setOnClickListener(v -> finish());
        backToListButton.setOnClickListener(v -> finish());
    }
}