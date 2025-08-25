package com.saveetha.foodstall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ulocation_preferenceActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    private RadioGroup locationRadioGroup;
    private RadioButton autoDetectRadioButton, manualRadioButton, mapRadioButton;
    private Button savePreferencesButton;
    private CardView autoDetectCard, enterManuallyCard, mapPickerCard;
    private TextView autoDetectLocationResultTextView;
    private FrameLayout loadingLocationOverlay;

    private final ActivityResultLauncher<Intent> locationResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    double latitude = data.getDoubleExtra("latitude", 0.0);
                    double longitude = data.getDoubleExtra("longitude", 0.0);

                    if (latitude != 0.0 && longitude != 0.0) {
                        GeoPoint selectedPoint = new GeoPoint(latitude, longitude);
                        LocationManager.saveUserLocation(this, selectedPoint);
                        updateLocationTextView(selectedPoint);
                        Toast.makeText(this, "Location saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ulocation_preference);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Find views
        locationRadioGroup = findViewById(R.id.locationRadioGroup);
        autoDetectRadioButton = findViewById(R.id.autoDetectRadioButton);
        manualRadioButton = findViewById(R.id.manualRadioButton);
        mapRadioButton = findViewById(R.id.mapRadioButton);
        savePreferencesButton = findViewById(R.id.savePreferencesButton);
        autoDetectCard = findViewById(R.id.autoDetectCard);
        enterManuallyCard = findViewById(R.id.enterManuallyCard);
        mapPickerCard = findViewById(R.id.mapPickerCard);
        autoDetectLocationResultTextView = findViewById(R.id.autoDetectLocationResultTextView);
        loadingLocationOverlay = findViewById(R.id.loadingLocationOverlay);

        setupClickListeners();
    }

    private void setupClickListeners() {
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.skipForNowTextView).setOnClickListener(v -> finish());

        // UPDATED: Listeners now call the new handleSelection method
        autoDetectCard.setOnClickListener(v -> handleSelection(R.id.autoDetectRadioButton));
        enterManuallyCard.setOnClickListener(v -> handleSelection(R.id.manualRadioButton));
        mapPickerCard.setOnClickListener(v -> handleSelection(R.id.mapRadioButton));

        findViewById(R.id.selectOnMapButton).setOnClickListener(v -> {
            Intent mapIntent = new Intent(this, Umap_pickerActivity.class);
            locationResultLauncher.launch(mapIntent);
        });

        savePreferencesButton.setOnClickListener(v -> {
            int selectedId = locationRadioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.autoDetectRadioButton) {
                checkPermissionAndFetchLocation();
            } else if (selectedId == R.id.mapRadioButton) {
                Intent mapIntent = new Intent(this, Umap_pickerActivity.class);
                locationResultLauncher.launch(mapIntent);
            } else {
                Toast.makeText(this, "Preference saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // NEW: This method ensures only one radio button is checked at a time
    private void handleSelection(int selectedRadioButtonId) {
        // Clear all checks
        autoDetectRadioButton.setChecked(false);
        manualRadioButton.setChecked(false);
        mapRadioButton.setChecked(false);

        // Set the new check
        if (selectedRadioButtonId == R.id.autoDetectRadioButton) {
            autoDetectRadioButton.setChecked(true);
            // Also start the location fetch immediately
            checkPermissionAndFetchLocation();
        } else if (selectedRadioButtonId == R.id.manualRadioButton) {
            manualRadioButton.setChecked(true);
        } else if (selectedRadioButtonId == R.id.mapRadioButton) {
            mapRadioButton.setChecked(true);
        }
    }

    private void checkPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            fetchLocationAndSave();
        }
    }

    private void fetchLocationAndSave() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return; }

        loadingLocationOverlay.setVisibility(View.VISIBLE);

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            loadingLocationOverlay.setVisibility(View.GONE);
            if (location != null) {
                GeoPoint userLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                LocationManager.saveUserLocation(this, userLocation);
                updateLocationTextView(userLocation);
                Toast.makeText(this, "Location saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Could not get location. Please enable GPS.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocationAndSave();
        } else {
            Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLocationTextView(GeoPoint point) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(point.getLatitude(), point.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                autoDetectLocationResultTextView.setText(addresses.get(0).getAddressLine(0));
            } else {
                autoDetectLocationResultTextView.setText("Location Saved");
            }
        } catch (IOException e) {
            autoDetectLocationResultTextView.setText("Location Saved");
            e.printStackTrace();
        }
    }
}