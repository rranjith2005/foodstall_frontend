package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button getStartedButton = findViewById(R.id.getStartedButton);

        // --- THIS IS THE UPDATED PART ---
        // Set a click listener for the "Get Started" button
        getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UsignupActivity.class);
            startActivity(intent);
            // Add this line to remove the transition animation
            overridePendingTransition(0, 0);
            finish();
        });
        // --- END OF UPDATED PART ---
    }
}