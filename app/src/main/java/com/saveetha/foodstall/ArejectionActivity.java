package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ArejectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arejection);

        // --- THIS IS THE UPDATED PART ---
        // Set up the "Back to New Stall List" button
        findViewById(R.id.backToListButton).setOnClickListener(v -> {
            Toast.makeText(this, "Navigating back to list...", Toast.LENGTH_SHORT).show();
            // Navigate to Anew_stallActivity and clear previous activities
            Intent intent = new Intent(ArejectionActivity.this, Anew_stallActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Close the current activity
        });
        // --- END OF UPDATED PART ---
    }
}