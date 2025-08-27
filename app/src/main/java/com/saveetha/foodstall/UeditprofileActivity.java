package com.saveetha.foodstall;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
import androidx.annotation.NonNull;

public class UeditprofileActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int STORAGE_PERMISSION_CODE = 102;

    private CircleImageView profileImageView;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    profileImageView.setImageURI(imageUri);
                }
            });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    profileImageView.setImageBitmap((android.graphics.Bitmap) result.getData().getExtras().get("data"));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ueditprofile);

        profileImageView = findViewById(R.id.profileImageView);
        LinearLayout favoriteStallsLayout = findViewById(R.id.favoriteStallsLayout);
        Button saveButton = findViewById(R.id.saveButton);

        profileImageView.setOnClickListener(v -> showImagePickerDialog());

        favoriteStallsLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, Ufavorite_stallsActivity.class));
        });

        saveButton.setOnClickListener(v -> {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        // --- THIS IS THE UPDATED PART: Added the navigation listener ---
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), UhomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_orders) {
                startActivity(new Intent(getApplicationContext(), UordersActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_wallet) {
                startActivity(new Intent(getApplicationContext(), UwalletActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Already on this screen
                return true;
            }
            return false;
        });
        // --- END OF UPDATED PART ---
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"}, (dialog, which) -> {
            if (which == 0) {
                checkStoragePermissionAndOpenGallery();
            } else {
                checkCameraPermissionAndOpenCamera();
            }
        });
        builder.show();
    }

    private void checkStoragePermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, STORAGE_PERMISSION_CODE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private void checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Storage permission is required to select an image.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to take a picture.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}