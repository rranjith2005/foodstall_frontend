package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrejectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orejected);

        // Set up the "Edit Details & Resubmit" button click listener
        Button resubmitButton = findViewById(R.id.resubmitButton);
        resubmitButton.setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to edit stall details...", Toast.LENGTH_SHORT).show();
            // In a real app, you would navigate to the stall details editing page here
            // Intent intent = new Intent(this, OstalldetailsActivity.class);
            // startActivity(intent);
        });

        // Set up the "Logout" button click listener
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            // Add your logout logic here
            finish(); // Close this activity
        });
    }
}