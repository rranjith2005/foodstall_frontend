package com.saveetha.foodstall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class OsettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osettings);

        // --- UPDATED PART START ---
        // Setup click listeners for all the options with the correct navigation
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.editProfileLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, OprofileActivity.class));
        });

        findViewById(R.id.notificationsLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, OnotificationsActivity.class));
        });

        findViewById(R.id.helpSupportLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, OhelpActivity.class));
        });

        findViewById(R.id.privacyLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, OpolicyActivity.class));
        });

        findViewById(R.id.changePasswordLayout).setOnClickListener(v -> {
            Intent intent = new Intent(this, UchangepasswordActivity.class);
            // Pass the "OWNER" role flag to the reusable activity
            intent.putExtra("USER_ROLE", "OWNER");
            startActivity(intent);
        });

        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });
        // --- UPDATED PART END ---
    }

    // --- UPDATED PART START ---
    // Updated this method to navigate to the LoginActivity and clear the activity stack
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OsettingsActivity.this, LoginActivity.class);
                    // Clear all previous activities from the stack so the user can't go back
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", null) // Do nothing if "No" is pressed
                .show();
    }
    // --- UPDATED PART END ---
}