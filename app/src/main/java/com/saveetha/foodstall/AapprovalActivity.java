package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AapprovalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aapproval);

        // Find views from the layout
        TextView approvalMessage = findViewById(R.id.approvalMessage);
        TextView ownerIdTextView = findViewById(R.id.ownerIdTextView);
        Button backToListButton = findViewById(R.id.backToListButton);

        // Get data from the previous activity
        Intent intent = getIntent();
        String stallName = intent.getStringExtra("STALL_NAME");
        String ownerId = intent.getStringExtra("OWNER_ID");

        // Set the text on the screen
        approvalMessage.setText("Stall '" + stallName + "' Approved Successfully!");
        ownerIdTextView.setText("Owner ID: " + ownerId);

        // --- THIS IS THE UPDATED PART ---
        // Set listener for the "Back to New Stall List" button
        backToListButton.setOnClickListener(v -> {
            // Navigate to Anew_stallActivity and clear all previous screens
            Intent backIntent = new Intent(AapprovalActivity.this, Anew_stallActivity.class);
            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backIntent);
            finish();
        });
        // --- END OF UPDATED PART ---
    }
}