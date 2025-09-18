package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

// --- START OF FIX ---
// 1. Import the new, correct StatusResponse class from the model package
import com.saveetha.foodstall.model.StatusResponse;
// --- END OF FIX ---

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OstalldetailsActivity extends AppCompatActivity {

    private TextInputEditText stallNameEditText, ownerNameEditText, phoneNumberEditText, emailEditText, addressEditText, fssaiNumberEditText, passwordEditText;
    private Button submitButton, cancelButton;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ostalldetails);

        stallNameEditText = findViewById(R.id.stallNameEditText);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        fssaiNumberEditText = findViewById(R.id.fssaiNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        if (userName != null && !userName.isEmpty()) {
            ownerNameEditText.setText(userName);
        }

        submitButton.setOnClickListener(v -> {
            if (validateInput()) {
                String stallName = stallNameEditText.getText().toString().trim();
                String ownerName = ownerNameEditText.getText().toString().trim();
                String phone = phoneNumberEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String fssai = fssaiNumberEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                performStallSubmission(stallName, ownerName, phone, email, address, fssai, password);
            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(OstalldetailsActivity.this, OsignupActivity.class);
            startActivity(cancelIntent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void performStallSubmission(String stallName, String ownerName, String phone, String email, String address, String fssai, String password) {
        startLoadingAnimation();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // 2. The API call now correctly expects and uses StatusResponse
        Call<StatusResponse> call = apiService.submitStallDetails(stallName, ownerName, phone, email, address, fssai, password);

        // 3. The Callback now correctly uses the StatusResponse type
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                stopLoadingAnimation();

                if (response.isSuccessful() && response.body() != null) {
                    // 4. The response body is correctly handled as a StatusResponse
                    StatusResponse apiResponse = response.body();
                    Toast.makeText(OstalldetailsActivity.this, apiResponse.getMessage(), Toast.LENGTH_LONG).show();

                    if ("success".equals(apiResponse.getStatus())) {
                        Intent intent = new Intent(OstalldetailsActivity.this, OpendingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(OstalldetailsActivity.this, "Submission failed. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                stopLoadingAnimation();
                Log.e("StallSubmitFailure", "onFailure: " + t.getMessage());
                Toast.makeText(OstalldetailsActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput() {
        String stallName = stallNameEditText.getText().toString().trim();
        String ownerName = ownerNameEditText.getText().toString().trim();
        String phone = phoneNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String fssai = fssaiNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(stallName) || TextUtils.isEmpty(ownerName) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(address) || TextUtils.isEmpty(fssai) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email format");
            return false;
        }
        if (fssai.length() != 14) {
            fssaiNumberEditText.setError("FSSAI Number must be 14 digits");
            return false;
        }

        return true;
    }

    private void startLoadingAnimation() {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);
        submitButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    private void stopLoadingAnimation() {
        loadingIcon.clearAnimation();
        loadingOverlay.setVisibility(View.GONE);
        submitButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }
}