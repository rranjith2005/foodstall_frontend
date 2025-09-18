package com.saveetha.foodstall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrejectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orejected); // Use your new professional layout

        TextView reasonTextView = findViewById(R.id.reasonTextView);
        Button resubmitButton = findViewById(R.id.resubmitButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        // --- THIS IS THE FIX for displaying the reason ---
        // Get the rejection reason passed from LoginActivity
        Intent intent = getIntent();
        String rejectionReason = intent.getStringExtra("REJECTION_REASON");

        // Set the reason text on the screen
        if (rejectionReason != null && !rejectionReason.isEmpty()) {
            reasonTextView.setText(rejectionReason);
        } else {
            reasonTextView.setText("No specific reason was provided by the admin.");
        }

        // --- This logic navigates the user to resubmit their details ---
        resubmitButton.setOnClickListener(v -> {
            // In a real app, you would pass the existing stall data to pre-fill the form
            Intent editIntent = new Intent(this, OstalldetailsActivity.class);
            startActivity(editIntent);
            finish();
        });

        // --- This adds the logout confirmation dialog ---
        logoutButton.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrejectedActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null) // "No" does nothing, just closes the dialog
                .show();
    }
}