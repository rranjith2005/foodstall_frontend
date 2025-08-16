package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.model.AdminProfile;

public class AprofileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aprofile);

        // Get dummy profile data
        AdminProfile adminProfile = getDummyProfileData();

        // Populate the profile details
        populateProfileDetails(adminProfile);

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the Edit Profile button
        findViewById(R.id.editProfileButton).setOnClickListener(v -> {
            Toast.makeText(this, "Editing profile...", Toast.LENGTH_SHORT).show();
            // Add navigation logic to the edit profile page here
        });

        // Set up the Logout button
        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            // Add your logout logic here
        });

        // Highlight the 'Profile' icon in the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_profile);
    }

    private AdminProfile getDummyProfileData() {
        return new AdminProfile("John Smith", "john.smith@stallspot.com", "+1 (555) 123-4567", "Administrator");
    }

    private void populateProfileDetails(AdminProfile profile) {
        ((TextView) findViewById(R.id.userName)).setText(profile.name);
        ((TextView) findViewById(R.id.userEmail)).setText(profile.email);
        ((TextView) findViewById(R.id.nameValue)).setText(profile.name);
        ((TextView) findViewById(R.id.emailValue)).setText(profile.email);
        ((TextView) findViewById(R.id.contactValue)).setText(profile.contact);
        ((TextView) findViewById(R.id.roleValue)).setText(profile.role);
    }
}
