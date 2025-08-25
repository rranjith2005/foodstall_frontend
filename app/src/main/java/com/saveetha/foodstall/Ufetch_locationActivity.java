package com.saveetha.foodstall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Ufetch_locationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private ProgressBar progressBar;
    private TextView fetchingTitle;
    private LinearLayout useCurrentLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufetch_location);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        progressBar = findViewById(R.id.progressBar);
        fetchingTitle = findViewById(R.id.fetchingTitle);
        useCurrentLocationButton = findViewById(R.id.useCurrentLocationButton);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        useCurrentLocationButton.setOnClickListener(v -> checkPermissionAndFetchLocation());
    }

    private void checkPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            fetchLocation();
        }
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return; }

        // Show loading state
        fetchingTitle.setText("Fetching your location...");
        progressBar.setVisibility(View.VISIBLE);
        useCurrentLocationButton.setVisibility(View.GONE);

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", location.getLatitude());
                resultIntent.putExtra("longitude", location.getLongitude());
                setResult(RESULT_OK, resultIntent);
                finish(); // Send result back and close
            } else {
                Toast.makeText(this, "Could not get location. Please enable GPS.", Toast.LENGTH_SHORT).show();
                // Reset UI
                fetchingTitle.setText("Enter area, street name...");
                progressBar.setVisibility(View.GONE);
                useCurrentLocationButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
        }
    }
}