package com.saveetha.foodstall; // Use your app's package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AhelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahelp);

        // Set up the back button to navigate back
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Get the EditText and Button from the layout
        EditText feedbackEditText = findViewById(R.id.feedbackEditText);
        Button sendMessageButton = findViewById(R.id.sendMessageButton);

        // Set up the "Send Message" button listener
        sendMessageButton.setOnClickListener(v -> {
            String feedback = feedbackEditText.getText().toString().trim();
            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please enter your issue or feedback.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                feedbackEditText.setText(""); // Clear the text box after sending
            }
        });

        // Set up click listeners for the support cards
        findViewById(R.id.callSupportCard).setOnClickListener(v -> {
            Toast.makeText(this, "Opening dialer to call support...", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.emailSupportCard).setOnClickListener(v -> {
            Toast.makeText(this, "Opening email client...", Toast.LENGTH_SHORT).show();
        });
    }
}