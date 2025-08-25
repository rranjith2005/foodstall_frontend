package com.saveetha.foodstall;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private EditText ownerIdEditText, stallNameEditText;
    private Button setLocationButton;
    private TextView coordinatesTextView;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker stallMarker;
    private GeoPoint selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));
        setContentView(R.layout.aset_location);

        ownerIdEditText = findViewById(R.id.ownerIdEditText);
        stallNameEditText = findViewById(R.id.stallNameEditText);
        setLocationButton = findViewById(R.id.setLocationButton);
        coordinatesTextView = findViewById(R.id.coordinatesTextView);
        mapView = findViewById(R.id.mapView);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setupMap();

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        setLocationButton.setOnClickListener(v -> {
            String ownerId = ownerIdEditText.getText().toString();
            String stallName = stallNameEditText.getText().toString();

            if (ownerId.isEmpty() || stallName.isEmpty()) {
                Toast.makeText(this, "Please fill Owner ID and Stall Name", Toast.LENGTH_SHORT).show();
            } else if (selectedLocation == null) {
                Toast.makeText(this, "Please tap the map to set a location", Toast.LENGTH_SHORT).show();
            } else {
                // Save the new location using the manager
                StallLocation newStall = new StallLocation(stallName, selectedLocation);
                StallLocationManager.addStallLocation(newStall);

                Toast.makeText(this, "Location set for " + stallName, Toast.LENGTH_LONG).show();
                finish(); // Go back after setting the location
            }
        });

        checkLocationPermissionAndGetLocation();
    }

    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(15.5);
        mapView.setMultiTouchControls(true);

        // This listener waits for the user to tap on the map
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                updateMarkerLocation(p);
                return true;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) { return false; }
        };
        mapView.getOverlays().add(new MapEventsOverlay(mapEventsReceiver));
    }

    private void updateMarkerLocation(GeoPoint location) {
        selectedLocation = location;
        coordinatesTextView.setText(String.format(Locale.US, "Lat: %.4f, Lon: %.4f",
                location.getLatitude(), location.getLongitude()));

        if (stallMarker == null) {
            stallMarker = new Marker(mapView);
            stallMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapView.getOverlays().add(stallMarker);
        }
        stallMarker.setPosition(location);
        mapView.invalidate(); // Refresh map to show the marker
    }

    private void checkLocationPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            centerMapOnUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            centerMapOnUserLocation();
        } else {
            Toast.makeText(this, "Permission denied. Centering map on default location.", Toast.LENGTH_SHORT).show();
            mapView.getController().setCenter(new GeoPoint(9.9252, 78.1198)); // Default to Madurai
        }
    }

    private void centerMapOnUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                mapView.getController().setCenter(startPoint);
            } else {
                Toast.makeText(this, "Could not get current location. Centering on default.", Toast.LENGTH_SHORT).show();
                mapView.getController().setCenter(new GeoPoint(9.9252, 78.1198)); // Default to Madurai
            }
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