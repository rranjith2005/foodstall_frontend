package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UsettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usettings);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Handle clicks for each settings option
        findViewById(R.id.notificationsLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.locationLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Location Preferences clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.changePasswordLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Change Password clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.privacyLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Privacy Policy clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.helpLayout).setOnClickListener(v -> {
            Toast.makeText(this, "Help & Support clicked", Toast.LENGTH_SHORT).show();
        });

        // This is the updated part, using the correct ID for the Log Out button
        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            Toast.makeText(this, "Log Out clicked", Toast.LENGTH_SHORT).show();
        });
    }
}