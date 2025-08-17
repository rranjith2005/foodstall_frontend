package com.saveetha.foodstall; // Use your app's package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Aprivacy_policyActivity extends AppCompatActivity {

    private CheckBox privacyPolicyCheckbox;
    private Button acceptContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aprivacy_policy);

        privacyPolicyCheckbox = findViewById(R.id.privacyPolicyCheckbox);
        acceptContinueButton = findViewById(R.id.acceptContinueButton);

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the "Accept & Continue" button listener
        acceptContinueButton.setOnClickListener(v -> {
            if (privacyPolicyCheckbox.isChecked()) {
                Toast.makeText(this, "Privacy policy accepted!", Toast.LENGTH_SHORT).show();
                // Add your navigation logic here to move to the next screen
            } else {
                Toast.makeText(this, "Please read and accept the Admin Privacy Policy", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the "Go Back" button listener
        findViewById(R.id.goBackButton).setOnClickListener(v -> {
            onBackPressed();
        });
    }
}