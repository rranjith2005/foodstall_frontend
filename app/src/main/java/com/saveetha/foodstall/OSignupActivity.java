package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OSignupActivity extends AppCompatActivity {

    EditText fullnameEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    Button signupBtn, userBtn, ownerBtn;
    TextView loginText;

    boolean isOwnerSelected = true; // default selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osignup);

        // Init views
        fullnameEditText = findViewById(R.id.fullnameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        userBtn = findViewById(R.id.userBtn);
        ownerBtn = findViewById(R.id.ownerBtn);

        // Button toggle logic
        userBtn.setOnClickListener(v -> {
            isOwnerSelected = false;
            userBtn.setBackgroundColor(getResources().getColor(android.R.color.white));
            userBtn.setTextColor(getResources().getColor(R.color.pink)); // FF6B6B
            ownerBtn.setBackgroundResource(R.drawable.outline_button);
            ownerBtn.setTextColor(getResources().getColor(android.R.color.white));
        });

        ownerBtn.setOnClickListener(v -> {
            isOwnerSelected = true;
            ownerBtn.setBackgroundColor(getResources().getColor(android.R.color.white));
            ownerBtn.setTextColor(getResources().getColor(R.color.pink)); // FF6B6B
            userBtn.setBackgroundResource(R.drawable.outline_button);
            userBtn.setTextColor(getResources().getColor(android.R.color.white));
        });

        // Go to login
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(OSignupActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Signup logic placeholder
        signupBtn.setOnClickListener(v -> {
            // TODO: Handle signup submission
        });
    }
}
