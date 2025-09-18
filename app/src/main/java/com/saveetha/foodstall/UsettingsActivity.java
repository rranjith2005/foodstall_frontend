package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UsettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usettings);

        // --- UPDATED PART START ---
        // Setup click listeners for the reorganized layout
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.editProfileLayout).setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UeditprofileActivity.class));
        });

        findViewById(R.id.notificationsLayout).setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UnotificationsActivity.class));
        });

        findViewById(R.id.locationLayout).setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, Ulocation_preferenceActivity.class));
        });

        findViewById(R.id.changePasswordLayout).setOnClickListener(v -> {
            Intent intent = new Intent(UsettingsActivity.this, UchangepasswordActivity.class);
            intent.putExtra("USER_ROLE", "USER");
            startActivity(intent);
        });

        findViewById(R.id.privacyLayout).setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, Uprivacy_policyActivity.class));
        });

        findViewById(R.id.helpLayout).setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UhelpActivity.class));
        });

        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });

        // The setupBottomNavigation() call has been removed.
        // --- UPDATED PART END ---
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UsettingsActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }
}