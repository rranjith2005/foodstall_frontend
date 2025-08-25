package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UsettingsActivity extends AppCompatActivity {

    private ImageView backButton;
    private LinearLayout notificationsLayout, locationLayout, changePasswordLayout, privacyLayout, helpLayout;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usettings);

        // Initialize all views
        backButton = findViewById(R.id.backButton);
        notificationsLayout = findViewById(R.id.notificationsLayout);
        locationLayout = findViewById(R.id.locationLayout);
        changePasswordLayout = findViewById(R.id.changePasswordLayout);
        privacyLayout = findViewById(R.id.privacyLayout);
        helpLayout = findViewById(R.id.helpLayout);
        logoutButton = findViewById(R.id.logoutButton);

        // Set up click listeners for navigation
        setupNavigation();
    }

    private void setupNavigation() {
        // Back Button -> UhomeActivity
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UhomeActivity.class));
            finish();
        });

        // Notifications -> UnotificationsActivity
        notificationsLayout.setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UnotificationsActivity.class));
        });

        // Location -> Ulocation_preferenceActivity
        locationLayout.setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, Ulocation_preferenceActivity.class));
        });

        // Change Password -> UchangepasswordActivity
        changePasswordLayout.setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UchangepasswordActivity.class));
        });

        // Privacy Policy -> Uprivacy_policyActivity
        privacyLayout.setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, Uprivacy_policyActivity.class));
        });

        // Help and Support -> UhelpActivity
        helpLayout.setOnClickListener(v -> {
            startActivity(new Intent(UsettingsActivity.this, UhelpActivity.class));
        });

        // Logout Button
        logoutButton.setOnClickListener(v -> {
            // TODO: Add your actual logout logic here
            Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show();
        });
    }
}