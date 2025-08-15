package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Aset_locationActivity extends AppCompatActivity {

    private EditText ownerIdEditText, stallNameEditText, latitudeEditText, longitudeEditText;
    private Button setLocationButton;
    private LinearLayout successMessageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aset_location);

        ownerIdEditText = findViewById(R.id.ownerIdEditText);
        stallNameEditText = findViewById(R.id.stallNameEditText);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        setLocationButton = findViewById(R.id.setLocationButton);
        successMessageLayout = findViewById(R.id.successMessageLayout);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        setLocationButton.setOnClickListener(v -> {
            String ownerId = ownerIdEditText.getText().toString();
            String stallName = stallNameEditText.getText().toString();
            String latitude = latitudeEditText.getText().toString();
            String longitude = longitudeEditText.getText().toString();

            if (ownerId.isEmpty() || stallName.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location set for " + stallName, Toast.LENGTH_SHORT).show();
                // This line makes the success message visible
                successMessageLayout.setVisibility(View.VISIBLE);
                ownerIdEditText.setText("");
                stallNameEditText.setText("");
                latitudeEditText.setText("");
                longitudeEditText.setText("");
            }
        });
    }
}