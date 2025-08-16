package com.saveetha.foodstall; // Use your app's package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AreasonActivity extends AppCompatActivity {

    private TextView stallNameTextView;
    private TextView ownerNameTextView;
    private EditText reasonEditText;
    private Button sendToOwnerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areason);

        // Find views by their IDs
        stallNameTextView = findViewById(R.id.stallNameTextView);
        ownerNameTextView = findViewById(R.id.ownerNameTextView);
        reasonEditText = findViewById(R.id.reasonEditText);
        sendToOwnerButton = findViewById(R.id.sendToOwnerButton);

        // Get data from the intent (e.g., stall name and owner)
        // For this example, we'll use hardcoded values
        String stallName = "Fresh Food Corner";
        String ownerName = "Sarah Johnson";
        stallNameTextView.setText(stallName);
        ownerNameTextView.setText(ownerName);

        // Set up the back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the button to send the reason
        sendToOwnerButton.setOnClickListener(v -> {
            String reason = reasonEditText.getText().toString().trim();
            if (reason.isEmpty()) {
                Toast.makeText(this, "Please provide a reason", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Reason sent to owner.", Toast.LENGTH_SHORT).show();
                // Add your logic to send the reason here
                // For example, you might call a function to update the stall's status
                // stallService.rejectStall(stallId, reason);
                finish(); // Close the activity after sending
            }
        });
    }
}
