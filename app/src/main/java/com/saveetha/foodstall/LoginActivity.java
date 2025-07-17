package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvForgotPassword, tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Login button click listener
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Add login API call here
                Toast.makeText(this, "Login Clicked\nEmail: " + email + "\nPassword: " + password, Toast.LENGTH_SHORT).show();
            }
        });

        // Forgot Password click listener
        tvForgotPassword.setOnClickListener(v -> {
            // TODO: Navigate to ForgotPasswordActivity
            Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
        });

        // Sign Up click listener
        tvSignUp.setOnClickListener(v -> {
            // TODO: Navigate to SignUpActivity
            Toast.makeText(this, "Sign Up Clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
