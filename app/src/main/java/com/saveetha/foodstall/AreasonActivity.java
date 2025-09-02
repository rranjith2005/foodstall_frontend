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
import android.widget.TextView;
import android.widget.Toast;

public class AreasonActivity extends AppCompatActivity {

    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areason);

        // Find views
        TextView stallNameTextView = findViewById(R.id.stallNameTextView);
        TextView ownerNameTextView = findViewById(R.id.ownerNameTextView);
        EditText reasonEditText = findViewById(R.id.reasonEditText);
        Button sendToOwnerButton = findViewById(R.id.sendToOwnerButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Get and set data from the previous activity
        Intent intent = getIntent();
        String stallName = intent.getStringExtra("STALL_NAME");
        String ownerName = intent.getStringExtra("OWNER_NAME");
        stallNameTextView.setText(stallName);
        ownerNameTextView.setText(ownerName);

        // Set listener for the "Send to Owner" button
        sendToOwnerButton.setOnClickListener(v -> {
            String reason = reasonEditText.getText().toString().trim();
            if (reason.isEmpty()) {
                Toast.makeText(this, "Please provide a reason for rejection", Toast.LENGTH_SHORT).show();
            } else {
                // Show animation, then navigate
                showLoadingOverlay(() -> {
                    Toast.makeText(this, "Rejection reason sent to owner", Toast.LENGTH_SHORT).show();

                    // Navigate to ArejectedActivity
                    Intent rejectIntent = new Intent(AreasonActivity.this, ArejectionActivity.class);
                    // Pass any necessary data to the next screen if needed
                    // rejectIntent.putExtra("REASON", reason);
                    startActivity(rejectIntent);

                    // Finish the current and previous activity
                    finishAffinity();
                });
            }
        });
    }

    // Helper method to show animation and then run an action
    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        // Wait for 1.5 seconds before completing the action
        new Handler(Looper.getMainLooper()).postDelayed(onComplete, 1500);
    }
}