package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
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

public class AhelpActivity extends AppCompatActivity {

    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahelp);

        // Find views
        EditText feedbackEditText = findViewById(R.id.feedbackEditText);
        Button sendMessageButton = findViewById(R.id.sendMessageButton);
        CardView callSupportCard = findViewById(R.id.callSupportCard);
        CardView emailSupportCard = findViewById(R.id.emailSupportCard);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // "Send Message" button listener with animation
        sendMessageButton.setOnClickListener(v -> {
            String feedback = feedbackEditText.getText().toString().trim();
            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please enter your issue or feedback.", Toast.LENGTH_SHORT).show();
            } else {
                showLoadingOverlay(() -> {
                    Toast.makeText(this, "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                    feedbackEditText.setText(""); // Clear the text box
                });
            }
        });

        // "Talk to Support" card listener
        callSupportCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+919876543210"));
            startActivity(intent);
        });

        // "Email Support" card listener
        emailSupportCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:support@stallspot.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Support Request from Admin App");
            startActivity(intent);
        });
    }

    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            onComplete.run();
        }, 1500); // 1.5 second delay
    }
}