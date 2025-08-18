package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class OapprovedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oapproved);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to login...", Toast.LENGTH_SHORT).show();
            // Add your navigation logic to the login screen here
            // Intent intent = new Intent(this, LoginActivity.class);
            // startActivity(intent);
            finish();
        });
    }
}
