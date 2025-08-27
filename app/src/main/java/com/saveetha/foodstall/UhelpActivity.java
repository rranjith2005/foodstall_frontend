package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UhelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhelp);

        // Set up the back button to navigate back
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        EditText feedbackEditText = findViewById(R.id.feedbackEditText);
        Button sendMessageButton = findViewById(R.id.sendMessageButton);

        // Set up the "Send Message" button listener
        sendMessageButton.setOnClickListener(v -> {
            String feedback = feedbackEditText.getText().toString().trim();
            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please enter your issue or feedback.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                feedbackEditText.setText("");
            }
        });

        // Set up click listeners for the support cards
        findViewById(R.id.faqCard).setOnClickListener(v -> {
            startActivity(new Intent(this, Ufrequent_questionActivity.class));
        });

        // UPDATED: This now opens the phone dialer
        findViewById(R.id.callSupportCard).setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            // Replace with your actual support phone number
            dialIntent.setData(Uri.parse("tel:+911234567890"));
            startActivity(dialIntent);
        });

        // UPDATED: This now opens an email client
        findViewById(R.id.emailSupportCard).setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            // Replace with your actual support email
            emailIntent.setData(Uri.parse("mailto:support@stallspot.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Food Stall App Support Request");

            try {
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No email app found.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}