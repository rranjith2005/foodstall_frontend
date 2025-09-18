package com.saveetha.foodstall;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.model.OwnerProfile;

import de.hdodenhof.circleimageview.CircleImageView;

public class OprofileActivity extends AppCompatActivity {

    private EditText stallNameEditText, ownerNameEditText, addressEditText, phoneNumberEditText, emailEditText, passwordEditText, fssaiNumberEditText, ownerIdEditText;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private TextView loadingText; // For changing loading text
    private CircleImageView profileImage;

    // --- UPDATED PART START ---
    private static final int CAMERA_PERMISSION_CODE = 101;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    // --- UPDATED PART END ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oprofile);

        // Find views
        stallNameEditText = findViewById(R.id.stallNameEditText);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        fssaiNumberEditText = findViewById(R.id.fssaiNumberEditText);
        ownerIdEditText = findViewById(R.id.ownerIdEditText);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        loadingText = findViewById(R.id.loadingText);
        profileImage = findViewById(R.id.profileImage);

        // Get and display profile data
        OwnerProfile ownerProfile = getDummyProfileData();
        populateForm(ownerProfile);


        findViewById(R.id.saveButton).setOnClickListener(v -> saveChanges());

        // --- UPDATED PART START ---
        // Register the activity result launchers
        registerCameraLauncher();
        registerGalleryLauncher();

        // Set up the click listener for the camera icon
        findViewById(R.id.editProfileImageButton).setOnClickListener(v -> showImagePickerDialog());
        // --- UPDATED PART END ---

        setupBottomNavigation();
    }

    // --- UPDATED PART START ---
    // Method to show the Camera/Gallery choice dialog
    private void showImagePickerDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(OprofileActivity.this);
        builder.setTitle("Change Profile Photo");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                checkCameraPermissionAndLaunch();
            } else if (options[item].equals("Choose from Gallery")) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(galleryIntent);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    // Method to check for camera permission before launching
    private void checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
        } else {
            // Request the permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, launch the camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(cameraIntent);
            } else {
                Toast.makeText(this, "Camera Permission is required to use the camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Register the launcher for the camera result
    private void registerCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        showLoadingOverlay("Uploading...", () -> {
                            profileImage.setImageBitmap(imageBitmap);
                            Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }

    // Register the launcher for the gallery result
    private void registerGalleryLauncher() {
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();

                        showLoadingOverlay("Processing...", () -> {
                            profileImage.setImageURI(imageUri);
                            Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }

    // Method to trigger the "Save Changes" animation
    private void saveChanges() {
        showLoadingOverlay("Saving changes...", () -> {
            Toast.makeText(this, "Changes saved successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    // Overloaded method to show animation with custom text
    private void showLoadingOverlay(String message, Runnable onComplete) {
        loadingText.setText(message);
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            if (onComplete != null) {
                onComplete.run();
            }
        }, 1500); // 1.5-second delay to simulate network activity
    }
    // --- UPDATED PART END ---

    private void populateForm(OwnerProfile profile) {
        stallNameEditText.setText(profile.stallName);
        ownerNameEditText.setText(profile.ownerName);
        addressEditText.setText(profile.fullAddress);
        phoneNumberEditText.setText(profile.phoneNumber);
        emailEditText.setText(profile.emailAddress);
        passwordEditText.setText(profile.password);
        fssaiNumberEditText.setText(profile.fssaiNumber);
        ownerIdEditText.setText(profile.ownerId);
    }

    private OwnerProfile getDummyProfileData() {
        return new OwnerProfile("Street Food Corner", "John Smith", "Block A, University Campus, Chennai", "+91 98765 43210", "john.smith@stallspot.com", "••••••••••", "220987654321", "OWN123456");
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_owner_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_owner_profile) {
                return true;
            } else if (itemId == R.id.nav_owner_home) {
                startActivity(new Intent(getApplicationContext(), OhomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_owner_orders) {
                startActivity(new Intent(getApplicationContext(), OordersActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_owner_menu) {
                startActivity(new Intent(getApplicationContext(), OmenuActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }
}