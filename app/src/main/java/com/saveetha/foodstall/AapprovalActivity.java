package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AapprovalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aapproval);

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the "Back to New Stall List" button
        findViewById(R.id.backToListButton).setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to new stall list...", Toast.LENGTH_SHORT).show();
            // Add your navigation logic here to go back to the list of stalls
            onBackPressed();
        });
    }
}
