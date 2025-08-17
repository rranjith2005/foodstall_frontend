package com.saveetha.foodstall; // Replace with your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AsettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asettings);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Handle clicks for each settings option
        findViewById(R.id.analysisLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Analysis clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.editStallLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Edit Stall clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.helpSupportLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Help & Support clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.privacyLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Privacy Policy clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
        });
    }
}
