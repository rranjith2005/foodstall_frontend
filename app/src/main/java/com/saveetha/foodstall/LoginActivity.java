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
        setContentView(R.layout.activity_login);

        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        signupText = findViewById(R.id.signupText);

        loginBtn.setOnClickListener(v -> {
            String id = idEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter ID and Password", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Add your login logic here (API call, validation, etc.)
            Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show();
        });

        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, USignupActivity.class);
            startActivity(intent);
        });
    }
}
