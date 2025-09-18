package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout; // UPDATED: Import FrameLayout for the loading overlay
import android.widget.TextView;
import android.widget.Toast;

import com.saveetha.foodstall.model.StatusResponse;
import com.saveetha.foodstall.model.StallDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Astall_detailsActivity extends AppCompatActivity {

    // --- V V V START OF UPDATED SECTION V V V ---
    // These variables now match the IDs in your astall_details.xml
    private TextView stallNameValue, ownerNameValue, phoneValue, emailValue, addressValue, fssaiValue, dateRequestedValue;
    private Button approveButton, rejectButton;
    private FrameLayout loadingOverlay; // UPDATED: Changed from ProgressBar to FrameLayout

    private StallDetails stallDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.astall_details);

        // Bind views using the correct IDs from your XML layout
        stallNameValue = findViewById(R.id.stallNameValue);
        ownerNameValue = findViewById(R.id.ownerNameValue);
        phoneValue = findViewById(R.id.phoneTextView); // This ID was already correct in your XML
        emailValue = findViewById(R.id.emailAddressValue);
        addressValue = findViewById(R.id.addressValue);
        fssaiValue = findViewById(R.id.fssaiNumberValue);
        dateRequestedValue = findViewById(R.id.dateRequestedValue); // ADDED: Find the date TextView
        approveButton = findViewById(R.id.approveButton);
        rejectButton = findViewById(R.id.rejectButton);
        loadingOverlay = findViewById(R.id.loadingOverlay); // UPDATED: Find the overlay

        stallDetails = (StallDetails) getIntent().getSerializableExtra("STALL_DETAILS");

        if (stallDetails == null) {
            Toast.makeText(this, "Error: Stall details not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateStallDetails();

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        approveButton.setOnClickListener(v -> updateStallStatus("1"));
        rejectButton.setOnClickListener(v -> {
            Intent intent = new Intent(Astall_detailsActivity.this, AreasonActivity.class);
            intent.putExtra("STALL_EMAIL", stallDetails.getEmail());
            intent.putExtra("STATUS_UPDATE", "-1");
            startActivity(intent);
        });
    }

    private void populateStallDetails() {
        // Set text on the correct TextViews
        stallNameValue.setText(stallDetails.getStallName());
        ownerNameValue.setText(stallDetails.getOwnerName());
        phoneValue.setText(stallDetails.getPhoneNumber());
        emailValue.setText(stallDetails.getEmail());
        addressValue.setText(stallDetails.getFullAddress());
        fssaiValue.setText(stallDetails.getFssaiNumber());
        dateRequestedValue.setText(stallDetails.dateRequested); // ADDED: Set the date requested text
    }
    // --- ^ ^ ^ END OF UPDATED SECTION ^ ^ ^ ---

    private void updateStallStatus(String status) {
        loadingOverlay.setVisibility(View.VISIBLE); // UPDATED: Show the overlay
        approveButton.setEnabled(false);
        rejectButton.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<StatusResponse> call = apiService.updateStallStatus(stallDetails.getEmail(), status, null);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                loadingOverlay.setVisibility(View.GONE); // UPDATED: Hide the overlay

                if (response.isSuccessful() && response.body() != null) {
                    StatusResponse apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Toast.makeText(Astall_detailsActivity.this, "Stall has been approved.", Toast.LENGTH_LONG).show();

                        Intent approvalIntent = new Intent(Astall_detailsActivity.this, AapprovalActivity.class);
                        approvalIntent.putExtra("STALL_ID", apiResponse.getStallId());
                        startActivity(approvalIntent);

                        finishAffinity();
                        startActivity(new Intent(Astall_detailsActivity.this, AhomeActivity.class));

                    } else {
                        Toast.makeText(Astall_detailsActivity.this, "Failed to approve: " + apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                        approveButton.setEnabled(true);
                        rejectButton.setEnabled(true);
                    }
                } else {
                    Toast.makeText(Astall_detailsActivity.this, "Server error. Please try again.", Toast.LENGTH_SHORT).show();
                    approveButton.setEnabled(true);
                    rejectButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                loadingOverlay.setVisibility(View.GONE); // UPDATED: Hide the overlay
                approveButton.setEnabled(true);
                rejectButton.setEnabled(true);
                Log.e("API_FAILURE", "Failed to update stall status", t);
                Toast.makeText(Astall_detailsActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}