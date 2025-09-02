package com.saveetha.foodstall;

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

        // --- THIS IS THE UPDATED PART ---
        // Find views
        LinearLayout analysisLayout = findViewById(R.id.analysisLayout);
        LinearLayout editStallLayout = findViewById(R.id.editStallLayout);
        LinearLayout helpSupportLayout = findViewById(R.id.helpSupportLayout);
        LinearLayout privacyLayout = findViewById(R.id.privacyLayout);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Set up click listeners with correct navigation
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        analysisLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, AanalysisActivity.class));
        });

        editStallLayout.setOnClickListener(v -> {
            // TODO: Create AeditStallActivity and uncomment the line below
            // startActivity(new Intent(this, AeditStallActivity.class));
            startActivity(new Intent(this, AeditstallActivity.class));
        });

        helpSupportLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, AhelpActivity.class));
        });

        privacyLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, Aprivacy_policyActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            // Navigate to Login screen and clear all previous activities
            Intent intent = new Intent(AsettingsActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        // --- END OF UPDATED PART ---
    }
}