package com.saveetha.foodstall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class USignupActivity extends AppCompatActivity {

    EditText fullnameEditText, idEditText, passwordEditText, confirmPasswordEditText;
    Button signupBtn;
    TextView loginText;
    ImageView togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usignup); // Make sure this layout XML exists!

        // Linking UI elements
        fullnameEditText = findViewById(R.id.fullnameEditText);
        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);

        // Example functionality
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(USignupActivity.this, MainActivity.class);
            startActivity(intent);
        });

        signupBtn.setOnClickListener(v -> {
            // Add sign-up logic here
        });
    }
}
