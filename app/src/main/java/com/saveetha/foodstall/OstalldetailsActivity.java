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

    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostalldetails);

        // Find all views (Unchanged)
        stallNameEditText = findViewById(R.id.stallNameEditText);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        fssaiNumberEditText = findViewById(R.id.fssaiNumberEditText);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        // --- ADDED THIS SECTION TO PRE-FILL THE OWNER'S NAME ---
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        if (userName != null && !userName.isEmpty()) {
            ownerNameEditText.setText(userName);
        }
        // --- END OF ADDED SECTION ---

        // Submit button logic (Unchanged)
        submitButton.setOnClickListener(v -> {
            String stallName = stallNameEditText.getText().toString().trim();
            // ... get other text fields ...

            if (stallName.isEmpty() /* ... check other fields ... */) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                showLoadingOverlay(() -> {
                    Toast.makeText(this, "Stall details submitted for approval!", Toast.LENGTH_SHORT).show();
                    Intent approvalIntent = new Intent(OstalldetailsActivity.this, OhomeActivity.class);
                    startActivity(approvalIntent);
                    finish();
                });
            }
        });

        // Cancel button logic (Unchanged)
        cancelButton.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(OstalldetailsActivity.this, OsignupActivity.class);
            startActivity(cancelIntent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    // Animation method (Unchanged)
    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(onComplete, 1500);
    }
}