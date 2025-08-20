package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.model.OwnerProfile;

public class OprofileActivity extends AppCompatActivity {

    private EditText stallNameEditText, ownerNameEditText, addressEditText, phoneNumberEditText, emailEditText, passwordEditText, fssaiNumberEditText, ownerIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oprofile);

        // Find all views by their IDs
        stallNameEditText = findViewById(R.id.stallNameEditText);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        fssaiNumberEditText = findViewById(R.id.fssaiNumberEditText);
        ownerIdEditText = findViewById(R.id.ownerIdEditText);

        // Get dummy profile data
        OwnerProfile ownerProfile = getDummyProfileData();

        // Populate the form fields
        populateForm(ownerProfile);

        // Set up the back button
        // Note: Your XML doesn't have a backButton ID, so this line will not work.
        // findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the "Save Changes" button
        findViewById(R.id.saveButton).setOnClickListener(v -> {
            Toast.makeText(this, "Changes saved successfully!", Toast.LENGTH_SHORT).show();
        });

        // Highlight the 'Profile' icon in the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_owner_profile);
    }

    private OwnerProfile getDummyProfileData() {
        return new OwnerProfile("Street Food Corner", "John Smith", "Block A, University Campus...", "+1 (555) 123-4567", "john.smith@stallspot.com", "••••••••••", "220987654321", "OWN123456");
    }

    private void populateForm(OwnerProfile profile) {
        stallNameEditText.setText(profile.stallName);
        ownerNameEditText.setText(profile.ownerName);
        addressEditText.setText(profile.fullAddress);
        phoneNumberEditText.setText(profile.phoneNumber);
        emailEditText.setText(profile.emailAddress);
        passwordEditText.setText(profile.password);
        fssaiNumberEditText.setText(profile.fssaiNumber);
        ownerIdEditText.setText(profile.ownerId);

    }
}