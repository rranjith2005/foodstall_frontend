package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class UsignupActivity extends AppCompatActivity {

    EditText fullnameEditText, idEditText, passwordEditText, confirmPasswordEditText;
    Button signupBtn, userBtn, ownerBtn;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usignup);

        // Linking UI elements
        fullnameEditText = findViewById(R.id.fullnameEditText);
        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        userBtn = findViewById(R.id.userBtn);
        ownerBtn = findViewById(R.id.ownerBtn);

        // Set default state: "User" is selected
        setButtonSelected(userBtn, ownerBtn);

        // --- Navigation and Button Logic ---

        // Navigate to UhomeActivity on 'Sign Up' button click
        signupBtn.setOnClickListener(v -> {
            // You would add your sign-up logic here first
            Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UsignupActivity.this, UhomeActivity.class);
            startActivity(intent);
        });

        // Navigate to LoginActivity on 'Login' TextView click
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(UsignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Handle 'User' button click
        userBtn.setOnClickListener(v -> {
            // Highlight the 'User' button and stay on this page
            setButtonSelected(userBtn, ownerBtn);
        });

        // Handle 'Owner' button click
        ownerBtn.setOnClickListener(v -> {
            // Highlight the 'Owner' button and navigate to OsignupActivity
            setButtonSelected(ownerBtn, userBtn);
            Intent intent = new Intent(UsignupActivity.this, OsignupActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Helper method to manage the visual state of the 'User' and 'Owner' buttons.
     */
    private void setButtonSelected(Button selectedButton, Button unselectedButton) {
        // Set the selected button's background to white with pink text
        selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.pink));

        // Set the unselected button's background to pink with white text
        unselectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
        unselectedButton.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

}