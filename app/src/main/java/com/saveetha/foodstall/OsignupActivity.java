package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class OsignupActivity extends AppCompatActivity {

    EditText fullnameEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    Button signupBtn, userBtn, ownerBtn;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osignup);

        // Init views
        fullnameEditText = findViewById(R.id.fullnameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        userBtn = findViewById(R.id.userBtn);
        ownerBtn = findViewById(R.id.ownerBtn);

        // Set default state: "Owner" is selected
        setButtonSelected(ownerBtn, userBtn);

        // --- Navigation and Button Logic ---

        // Navigate to OstalldetailsActivity on 'Sign Up' button click
        signupBtn.setOnClickListener(v -> {
            // You would add your sign-up logic here first
            Toast.makeText(this, "Signing up as Owner...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OsignupActivity.this, OstalldetailsActivity.class);
            startActivity(intent);
        });

        // Navigate to LoginActivity on 'Login' TextView click
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(OsignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Handle 'User' button click
        userBtn.setOnClickListener(v -> {
            // Highlight the 'User' button and navigate to USignupActivity
            setButtonSelected(userBtn, ownerBtn);
            Intent intent = new Intent(OsignupActivity.this, UsignupActivity.class);
            startActivity(intent);
        });

        // Handle 'Owner' button click
        ownerBtn.setOnClickListener(v -> {
            // Highlight the 'Owner' button and stay on this page
            setButtonSelected(ownerBtn, userBtn);
        });
    }

    /**
     * Helper method to manage the visual state of the 'User' and 'Owner' buttons.
     */
    private void setButtonSelected(Button selectedButton, Button unselectedButton) {
        // Set the selected button's background to white with pink text
        selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.pink));

        // Set the unselected button's background to gray with white text
        unselectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
        unselectedButton.setTextColor(ContextCompat.getColor(this, R.color.white));
    }
}