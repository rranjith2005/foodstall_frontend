package com.saveetha.foodstall;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class UchangepasswordActivity extends AppCompatActivity {

    private TextInputEditText currentPasswordEditText;
    private TextInputEditText newPasswordEditText;
    private TextInputEditText confirmPasswordEditText;

    // --- UPDATED PART START ---
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private TextView loadingText;
    // --- UPDATED PART END ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uchangepassword);

        // Find views by their IDs
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        Button updatePasswordButton = findViewById(R.id.updatePasswordButton);

        // --- UPDATED PART START ---
        // Find animation views
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        loadingText = findViewById(R.id.loadingText);
        // --- UPDATED PART END ---

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        updatePasswordButton.setOnClickListener(v -> {
            validateAndSaveChanges();
        });
    }

    private void validateAndSaveChanges() {
        String currentPassword = currentPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Basic Validation
        if (TextUtils.isEmpty(currentPassword)) {
            currentPasswordEditText.setError("Current password cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("New password cannot be empty");
            return;
        }

        if (newPassword.length() < 6) {
            newPasswordEditText.setError("Password must be at least 6 characters");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // --- UPDATED PART START ---
        // If all validation passes, show the animation
        showLoadingOverlay(() -> {
            Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to the previous screen
        });
        // --- UPDATED PART END ---
    }

    // --- UPDATED PART START ---
    // Added the showLoadingOverlay method to enable the animation
    private void showLoadingOverlay(Runnable onComplete) {
        loadingText.setText("Updating..."); // Set the correct text
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            if (onComplete != null) {
                onComplete.run();
            }
        }, 1500); // 1.5-second delay to simulate network activity
    }
    // --- UPDATED PART END ---
}