package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
// --- START OF FIX ---
// 1. Import the new, correct StatusResponse class from the model package
import com.saveetha.foodstall.model.StatusResponse;
// --- END OF FIX ---

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreasonActivity extends AppCompatActivity {

    private TextInputEditText reasonEditText;
    private Button rejectButton;
    private ProgressBar progressBar;

    private String email;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areason);

        reasonEditText = findViewById(R.id.reasonEditText);
        rejectButton = findViewById(R.id.rejectButton);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        email = intent.getStringExtra("STALL_EMAIL");
        status = intent.getStringExtra("STATUS_UPDATE");

        if (email == null || status == null) {
            Toast.makeText(this, "Error: Missing required data.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        rejectButton.setOnClickListener(v -> {
            String reason = reasonEditText.getText().toString().trim();
            if (reason.isEmpty()) {
                Toast.makeText(AreasonActivity.this, "Please provide a reason for rejection.", Toast.LENGTH_SHORT).show();
                return;
            }
            updateStallStatus(reason);
        });
    }

    private void updateStallStatus(String reason) {
        progressBar.setVisibility(View.VISIBLE);
        rejectButton.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // 2. The Call object now correctly expects a StatusResponse
        Call<StatusResponse> call = apiService.updateStallStatus(email, status, reason);

        // 3. The Callback now correctly uses the StatusResponse type
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                progressBar.setVisibility(View.GONE);
                rejectButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    // 4. The response body is correctly cast to StatusResponse
                    if ("success".equals(response.body().getStatus())) {
                        Toast.makeText(AreasonActivity.this, "Stall has been rejected.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AreasonActivity.this, AhomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AreasonActivity.this, "Failed to update: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AreasonActivity.this, "Server error. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                rejectButton.setEnabled(true);
                Log.e("API_FAILURE", "Failed to update stall status", t);
                Toast.makeText(AreasonActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}