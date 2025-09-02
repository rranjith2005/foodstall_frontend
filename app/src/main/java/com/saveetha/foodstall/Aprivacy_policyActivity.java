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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Aprivacy_policyActivity extends AppCompatActivity {

    private CheckBox privacyPolicyCheckbox;
    private Button acceptContinueButton;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aprivacy_policy);

        // Find views
        privacyPolicyCheckbox = findViewById(R.id.privacyPolicyCheckbox);
        acceptContinueButton = findViewById(R.id.acceptContinueButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        TextView skipButton = findViewById(R.id.goBackButton);

        // Initially disable the button
        acceptContinueButton.setEnabled(false);
        acceptContinueButton.setAlpha(0.5f);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Listener to enable/disable the button based on checkbox state
        privacyPolicyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            acceptContinueButton.setEnabled(isChecked);
            acceptContinueButton.setAlpha(isChecked ? 1.0f : 0.5f);
        });

        acceptContinueButton.setOnClickListener(v -> {
            showLoadingOverlay(() -> {
                Toast.makeText(this, "Privacy policy accepted!", Toast.LENGTH_SHORT).show();
                navigateToSettings();
            });
        });

        // "Skip for Now" button listener
        skipButton.setOnClickListener(v -> {
            navigateToSettings();
        });
    }

    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(onComplete, 1500);
    }

    private void navigateToSettings() {
        Intent intent = new Intent(Aprivacy_policyActivity.this, AsettingsActivity.class);
        startActivity(intent);
        finish();
    }
}