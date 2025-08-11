package com.saveetha.foodstall; // Replace with your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UeditprofileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ueditprofile);

        // This will highlight the 'Profile' icon in the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the save button
        findViewById(R.id.saveButton).setOnClickListener(v -> {
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            // Add your logic to save the updated profile information here
        });
    }
}