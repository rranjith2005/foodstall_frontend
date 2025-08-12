package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Ulocation_preferenceActivity extends AppCompatActivity {

    private RadioGroup locationRadioGroup;
    private EditText manualLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ulocation_preference);

        locationRadioGroup = findViewById(R.id.locationRadioGroup);
        manualLocationEditText = findViewById(R.id.manualLocationEditText);

        // Initially hide the EditText
        manualLocationEditText.setVisibility(View.GONE);

        // Back button listener
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Handle radio button selections
        locationRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.manualRadioButton) {
                manualLocationEditText.setVisibility(View.VISIBLE);
            } else {
                manualLocationEditText.setVisibility(View.GONE);
            }
        });

        // Save Preferences button listener
        findViewById(R.id.savePreferencesButton).setOnClickListener(v -> {
            int selectedId = locationRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a location preference", Toast.LENGTH_SHORT).show();
            } else if (selectedId == R.id.manualRadioButton && manualLocationEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter your location manually", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Preferences saved!", Toast.LENGTH_SHORT).show();
                // Add your navigation or data saving logic here
            }
        });

        // Skip for Now button listener
        findViewById(R.id.skipForNowTextView).setOnClickListener(v -> {
            Toast.makeText(this, "Skipping for now...", Toast.LENGTH_SHORT).show();
            // Add your navigation logic here
        });
    }
}