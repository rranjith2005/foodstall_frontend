package com.saveetha.foodstall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.saveetha.foodstall.model.StallLocation;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.Locale;

public class Aset_locationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private EditText ownerIdEditText, stallNameEditText, latitudeEditText, longitudeEditText;
    private RadioGroup locationRadioGroup;
    private CardView mapCard;
    private Button setLocationButton;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker stallMarker;
    private GeoPoint selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));
        setContentView(R.layout.aset_location);

        // Find all views
        ownerIdEditText = findViewById(R.id.ownerIdEditText);
        stallNameEditText = findViewById(R.id.stallNameEditText);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        locationRadioGroup = findViewById(R.id.locationRadioGroup);
        mapCard = findViewById(R.id.mapCard);
        setLocationButton = findViewById(R.id.setLocationButton);
        mapView = findViewById(R.id.mapView);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setupMap();
        setupRadioGroupListener();

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        setLocationButton.setOnClickListener(v -> saveLocation());

        // Initial setup for Auto Fetch
        checkLocationPermissionAndGetLocation();
    }

    private void setupRadioGroupListener() {
        locationRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.autoFetchRadio) {
                mapCard.setVisibility(View.GONE);
                latitudeEditText.setEnabled(false);
                longitudeEditText.setEnabled(false);
                checkLocationPermissionAndGetLocation(); // Fetch location again
            } else if (checkedId == R.id.manualEntryRadio) {
                mapCard.setVisibility(View.GONE);
                latitudeEditText.setEnabled(true);
                longitudeEditText.setEnabled(true);
                latitudeEditText.requestFocus();
            } else if (checkedId == R.id.mapPickerRadio) {
                mapCard.setVisibility(View.VISIBLE);
                latitudeEditText.setEnabled(false);
                longitudeEditText.setEnabled(false);
            }
        });
    }

    private void saveLocation() {
        // ... (saveLocation logic is unchanged from the previous version)
    }

    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE); // Better quality map tiles
        mapView.getController().setZoom(15.5);
        mapView.setMultiTouchControls(true);

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                updateMarkerLocation(p);
                // Auto-fill the coordinates when map is tapped
                latitudeEditText.setText(String.format(Locale.US, "%.4f", p.getLatitude()));
                longitudeEditText.setText(String.format(Locale.US, "%.4f", p.getLongitude()));
                return true;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) { return false; }
        };
        mapView.getOverlays().add(new MapEventsOverlay(mapEventsReceiver));
    }

    private void updateMarkerLocation(GeoPoint location) {
        selectedLocation = location;
        if (stallMarker == null) {
            stallMarker = new Marker(mapView);
            stallMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapView.getOverlays().add(stallMarker);
        }
        stallMarker.setPosition(location);
        mapView.invalidate();
    }

    private void checkLocationPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchAndDisplayUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchAndDisplayUserLocation();
        } else {
            Toast.makeText(this, "Permission denied. Using default location.", Toast.LENGTH_SHORT).show();
            mapView.getController().setCenter(new GeoPoint(13.0827, 80.2707)); // Default to Chennai
        }
    }

    @SuppressLint("MissingPermission")
    private void fetchAndDisplayUserLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            GeoPoint startPoint;
            if (location != null) {
                startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                selectedLocation = startPoint;
                latitudeEditText.setText(String.format(Locale.US, "%.4f", location.getLatitude()));
                longitudeEditText.setText(String.format(Locale.US, "%.4f", location.getLongitude()));
                Toast.makeText(this, "Current location fetched!", Toast.LENGTH_SHORT).show();
            } else {
                startPoint = new GeoPoint(13.0827, 80.2707); // Default to Chennai
                Toast.makeText(this, "Could not get current location.", Toast.LENGTH_SHORT).show();
            }
            mapView.getController().animateTo(startPoint);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}