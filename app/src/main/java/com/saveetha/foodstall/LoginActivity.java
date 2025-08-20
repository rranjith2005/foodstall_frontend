package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText idEditText, passwordEditText;
    Button loginBtn;
    TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Linking UI elements
        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        signupText = findViewById(R.id.signupText);

        // Set up login button listener
        loginBtn.setOnClickListener(v -> {
            String userId = idEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter ID and password", Toast.LENGTH_SHORT).show();
            } else {
                // Admin login check
                if (userId.equals("admin") && password.equals("admin123")) {
                    Toast.makeText(this, "Logging in as Admin...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AhomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                // Owner login check
                else if (userId.matches("^S\\d{5}$")) {
                    Toast.makeText(this, "Logging in as Owner...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, OhomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                // User login check (default)
                else {
                    Toast.makeText(this, "Logging in as User...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, UhomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Set up signup text listener
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, UsignupActivity.class);
            startActivity(intent);
        });
    }
}