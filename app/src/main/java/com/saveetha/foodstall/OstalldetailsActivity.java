package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OstalldetailsActivity extends AppCompatActivity {

    private EditText stallNameEditText, ownerNameEditText, phoneNumberEditText, emailEditText, addressEditText, fssaiNumberEditText;
    private Button submitButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostalldetails);

        // Find all views by their IDs
        stallNameEditText = findViewById(R.id.stallNameEditText);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        fssaiNumberEditText = findViewById(R.id.fssaiNumberEditText);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set up the back button listener
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up the submit button listener
        submitButton.setOnClickListener(v -> {
            String stallName = stallNameEditText.getText().toString();
            String ownerName = ownerNameEditText.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String fssaiNumber = fssaiNumberEditText.getText().toString();

            if (stallName.isEmpty() || ownerName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty() || fssaiNumber.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Stall details submitted for approval!", Toast.LENGTH_SHORT).show();
                // Add your logic to submit the data to a server here
            }
        });

        // Set up the cancel button listener to go back
        cancelButton.setOnClickListener(v -> onBackPressed());
    }
}
