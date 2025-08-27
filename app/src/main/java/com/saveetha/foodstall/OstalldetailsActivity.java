package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class OstalldetailsActivity extends AppCompatActivity {

    private EditText stallNameEditText, ownerNameEditText, phoneNumberEditText, emailEditText, addressEditText, fssaiNumberEditText;
    private Button submitButton, cancelButton;

    // --- NEW: Add variables for the loading overlay ---
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostalldetails);

        // Find all views
        stallNameEditText = findViewById(R.id.stallNameEditText);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        fssaiNumberEditText = findViewById(R.id.fssaiNumberEditText);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        // --- NEW: Find the loading overlay views ---
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        // --- UPDATED: The submit button now calls the animation method ---
        submitButton.setOnClickListener(v -> {
            String stallName = stallNameEditText.getText().toString().trim();
            // ... get other text fields ...

            if (stallName.isEmpty() /* ... check other fields ... */) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                showLoadingOverlay(() -> {
                    Toast.makeText(this, "Stall details submitted for approval!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OstalldetailsActivity.this, OpendingActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(OstalldetailsActivity.this, OsignupActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    // --- NEW: This method handles the animation and navigation ---
    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        // Wait for 1.5 seconds before navigating
        new Handler(Looper.getMainLooper()).postDelayed(onComplete, 1500);
    }
}