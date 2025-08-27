package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AreasonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areason);

        // This button now correctly returns to Astall_detailsActivity
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Find views using the IDs from your XML
        TextView stallNameTextView = findViewById(R.id.stallNameTextView);
        TextView ownerNameTextView = findViewById(R.id.ownerNameTextView);
        EditText reasonEditText = findViewById(R.id.reasonEditText);
        Button sendToOwnerButton = findViewById(R.id.sendToOwnerButton);

        // Get data from the previous activity
        Intent intent = getIntent();
        String stallName = intent.getStringExtra("STALL_NAME");
        String ownerName = intent.getStringExtra("OWNER_NAME");

        // Set the text for the stall and owner
        stallNameTextView.setText(stallName);
        ownerNameTextView.setText(ownerName); // Owner name will now appear

        // Set listener for the "Send to Owner" button
        sendToOwnerButton.setOnClickListener(v -> {
            String reason = reasonEditText.getText().toString().trim();
            if (reason.isEmpty()) {
                Toast.makeText(this, "Please provide a reason for rejection", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Rejection reason sent to owner", Toast.LENGTH_SHORT).show();

                // UPDATED NAVIGATION: This finishes the current screen and returns to Astall_detailsActivity
                finish();
            }
        });
    }
}