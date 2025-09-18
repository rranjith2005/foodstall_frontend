package com.saveetha.foodstall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ufetch_locationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufetch_location);

        ImageView locationPulseIcon = findViewById(R.id.locationPulseIcon);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.anim_location_pulse);
        locationPulseIcon.startAnimation(pulse);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        checkPermissionAndFetchLocation();
    }

    private void checkPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            fetchLocation();
        }
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission required to fetch location.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (location != null) {
                    // --- UPDATED PART START ---
                    // Geocode the location to get the address
                    String addressString = getAddressFromLocation(location);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("latitude", location.getLatitude());
                    resultIntent.putExtra("longitude", location.getLongitude());
                    resultIntent.putExtra("address", addressString); // Add the address to the result
                    setResult(RESULT_OK, resultIntent);
                    finish();
                    // --- UPDATED PART END ---
                } else {
                    Toast.makeText(this, "Could not get location. Please enable GPS and try again.", Toast.LENGTH_LONG).show();
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }, 1500);
        });
    }

    // --- UPDATED PART START ---
    // New helper method to convert coordinates to an address string
    private String getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0); // Returns the full address
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Fallback if geocoding fails
        return String.format(Locale.getDefault(), "Lat: %.4f, Lon: %.4f", location.getLatitude(), location.getLongitude());
    }
    // --- UPDATED PART END ---

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            } else {
                Toast.makeText(this, "Location permission was denied.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }
}
