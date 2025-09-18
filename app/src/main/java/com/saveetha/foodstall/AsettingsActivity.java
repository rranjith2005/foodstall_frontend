package com.saveetha.foodstall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AsettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asettings);

        // --- UPDATED PART START ---
        // Find views from the redesigned layout
        LinearLayout analysisLayout = findViewById(R.id.analysisLayout);
        LinearLayout editStallLayout = findViewById(R.id.editStallLayout);
        LinearLayout helpSupportLayout = findViewById(R.id.helpSupportLayout);
        LinearLayout privacyLayout = findViewById(R.id.privacyLayout);
        LinearLayout changePasswordLayout = findViewById(R.id.changePasswordLayout); // Added this
        Button logoutButton = findViewById(R.id.logoutButton);

        // Set up click listeners
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.editProfileLayout).setOnClickListener(v -> {
            startActivity(new Intent(this, AprofileActivity.class));
        });

        analysisLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, AanalysisActivity.class));
        });

        editStallLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, AeditstallActivity.class));
        });

        // Added listener for Change Password
        changePasswordLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, UchangepasswordActivity.class);
            // Pass the "ADMIN" role flag to the reusable activity
            intent.putExtra("USER_ROLE", "ADMIN");
            startActivity(intent);
        });

        helpSupportLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, AhelpActivity.class));
        });

        privacyLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, Aprivacy_policyActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });

        // Bottom navigation setup has been removed
    }

    // New professional logout dialog
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AsettingsActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
    // --- UPDATED PART END ---
}