package com.saveetha.foodstall;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class AupdatestallActivity extends AppCompatActivity implements LocationContract.View {

    private EditText latitudeEditText, longitudeEditText;
    private Button autoFetchLocationButton, deleteButton, updateButton;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    private LocationPresenter locationPresenter;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    locationPresenter.requestLocation();
                } else {
                    Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aupdatestall);

        locationPresenter = new LocationPresenter(this, this);

        // Find all views
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        autoFetchLocationButton = findViewById(R.id.autoFetchLocationButton);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Set up click listeners with animation
        updateButton.setOnClickListener(v -> {
            showLoadingOverlay(() -> {
                Toast.makeText(this, "Stall details updated!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to the previous screen after animation
            });
        });

        deleteButton.setOnClickListener(v -> {
            // You can add a confirmation dialog here before deleting
            showLoadingOverlay(() -> {
                Toast.makeText(this, "Stall deleted!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to the previous screen after animation
            });
        });

        autoFetchLocationButton.setOnClickListener(v -> {
            checkLocationPermissionAndFetch();
        });
    }

    private void checkLocationPermissionAndFetch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPresenter.requestLocation();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            onComplete.run();
        }, 1500); // 1.5 second delay
    }

    @Override
    public void showLocation(double latitude, double longitude) {
        latitudeEditText.setText(String.valueOf(latitude));
        longitudeEditText.setText(String.valueOf(longitude));
    }

    @Override
    public void showLocationError() {
        Toast.makeText(this, "Could not fetch location. Please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean isLoading) {
        if (isLoading) {
            autoFetchLocationButton.setText("Fetching...");
        } else {
            autoFetchLocationButton.setText("Auto Fetch Location");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationPresenter.detachView();
    }
}