package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saveetha.foodstall.model.StallLocation;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;
import java.util.Locale;

public class Aset_locationActivity extends AppCompatActivity {

    private EditText stallIdEditText, stallNameEditText, latitudeEditText, longitudeEditText;
    private LinearLayout manualCoordinatesLayout;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private TextView loadingText;

    private static final ArrayList<StallLocation> savedLocations = new ArrayList<>();

    private final ActivityResultLauncher<Intent> autoFetchResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    double latitude = data.getDoubleExtra("latitude", 0.0);
                    double longitude = data.getDoubleExtra("longitude", 0.0);

                    latitudeEditText.setText(String.format(Locale.US, "%.6f", latitude));
                    longitudeEditText.setText(String.format(Locale.US, "%.6f", longitude));
                    manualCoordinatesLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Location fetched successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Could not fetch location.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aset_location);

        stallIdEditText = findViewById(R.id.stallIdEditText);
        stallNameEditText = findViewById(R.id.stallNameEditText);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        manualCoordinatesLayout = findViewById(R.id.manualCoordinatesLayout);
        CardView autoFetchCard = findViewById(R.id.autoFetchCard);
        CardView manualEntryCard = findViewById(R.id.manualEntryCard);
        Button setLocationButton = findViewById(R.id.setLocationButton);
        Button viewSavedLocationsButton = findViewById(R.id.viewSavedLocationsButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        loadingText = findViewById(R.id.loadingText);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        autoFetchCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, Ufetch_locationActivity.class);
            autoFetchResultLauncher.launch(intent);
        });

        manualEntryCard.setOnClickListener(v -> {
            manualCoordinatesLayout.setVisibility(manualCoordinatesLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            if(manualCoordinatesLayout.getVisibility() == View.VISIBLE) {
                latitudeEditText.requestFocus();
            }
        });

        viewSavedLocationsButton.setOnClickListener(v -> showSavedLocationsDialog());
        setLocationButton.setOnClickListener(v -> saveLocation());
    }

    private void saveLocation() {
        String stallId = stallIdEditText.getText().toString().trim();
        String stallName = stallNameEditText.getText().toString().trim();
        String latitudeStr = latitudeEditText.getText().toString().trim();
        String longitudeStr = longitudeEditText.getText().toString().trim();

        if (stallId.isEmpty() || stallName.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        for (StallLocation loc : savedLocations) {
            if (loc.getStallId().equalsIgnoreCase(stallId) || loc.getName().equalsIgnoreCase(stallName)) {
                new AlertDialog.Builder(this)
                        .setTitle("Duplicate Entry")
                        .setMessage("A location for Stall ID '" + stallId + "' or Stall Name '" + stallName + "' already exists.")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }
        }

        showLoadingOverlay(() -> {
            double lat = Double.parseDouble(latitudeStr);
            double lon = Double.parseDouble(longitudeStr);
            savedLocations.add(new StallLocation(stallId, stallName, new GeoPoint(lat, lon)));
            Toast.makeText(this, "Location set for " + stallName, Toast.LENGTH_SHORT).show();
            clearInputFields();
        });
    }

    // --- UPDATED PART START ---
    // This method now uses a BottomSheetDialog for the professional pop-up effect.
    private void showSavedLocationsDialog() {
        if (savedLocations.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Saved Locations")
                    .setMessage("No locations have been saved yet.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // Use the custom BottomSheetDialogTheme we created in styles.xml
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_location_table, null);
        bottomSheetDialog.setContentView(dialogView);

        TextView tableTextView = dialogView.findViewById(R.id.tableTextView);
        Button closeButton = dialogView.findViewById(R.id.closeButton);

        StringBuilder table = new StringBuilder();
        table.append(String.format("%-10s | %-15s | %-10s | %s\n", "Stall ID", "Stall Name", "Latitude", "Longitude"));
        table.append("------------------------------------------------------------\n");
        for (StallLocation loc : savedLocations) {
            table.append(String.format(Locale.US, "%-10s | %-15.15s | %-10.4f | %.4f\n",
                    loc.getStallId(),
                    loc.getName(),
                    loc.getLocation().getLatitude(),
                    loc.getLocation().getLongitude()));
        }
        tableTextView.setText(table.toString());

        closeButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }
    // --- UPDATED PART END ---

    private void showLoadingOverlay(Runnable onComplete) {
        loadingText.setText("Updating...");
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            if (onComplete != null) {
                onComplete.run();
            }
        }, 1500);
    }

    private void clearInputFields(){
        stallIdEditText.setText("");
        stallNameEditText.setText("");
        latitudeEditText.setText("");
        longitudeEditText.setText("");
        manualCoordinatesLayout.setVisibility(View.GONE);
        stallIdEditText.requestFocus();
    }
}