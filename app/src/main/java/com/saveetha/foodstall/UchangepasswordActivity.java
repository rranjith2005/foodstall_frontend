package com.saveetha.foodstall;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class UchangepasswordActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextInputEditText currentPasswordEditText, newPasswordEditText, confirmNewPasswordEditText;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uchangepassword);

        // Initialize Views
        backButton = findViewById(R.id.backButton);
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPasswordEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        // Back button listener
        backButton.setOnClickListener(v -> finish());

        // Save changes listener
        saveChangesButton.setOnClickListener(v -> validateAndSaveChanges());
    }

    private void validateAndSaveChanges() {
        String currentPassword = Objects.requireNonNull(currentPasswordEditText.getText()).toString().trim();
        String newPassword = Objects.requireNonNull(newPasswordEditText.getText()).toString().trim();
        String confirmNewPassword = Objects.requireNonNull(confirmNewPasswordEditText.getText()).toString().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- IMPORTANT ---
        // TODO: Add your logic here to verify if the 'currentPassword' is correct.
        // This usually involves checking against a stored password in a database or Firebase Auth.
        // For this example, we'll assume it's correct if it's not empty.
        boolean isCurrentPasswordCorrect = true; // Replace with your actual check

        if (isCurrentPasswordCorrect) {
            // TODO: Add your logic to update the password in your backend/database.
            Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to the settings screen
        } else {
            Toast.makeText(this, "Incorrect current password", Toast.LENGTH_SHORT).show();
        }
    }
}