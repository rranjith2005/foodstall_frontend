package com.saveetha.foodstall;

// --- IMPORTS ---
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

// --- START OF FIX ---
// 1. Import the new, correct StatusResponse class from the model package
import com.saveetha.foodstall.model.StatusResponse;
// --- END OF FIX ---

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OsignupActivity extends AppCompatActivity {

    // --- UI ELEMENT DECLARATIONS ---
    EditText fullnameEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    Button signupBtn;
    TextView loginText;
    NestedScrollView scrollView;
    MaterialButton googleSignUpButton;
    ProgressBar progressBar;
    MaterialButtonToggleGroup toggleButtonGroup;

    // --- SIMULATED DATABASE (Unchanged) ---
    private static final List<String> KNOWN_OWNER_EMAILS = Arrays.asList(
            "owner.one@example.com", "another.owner@example.com"
    );
    private static final List<String> KNOWN_USER_EMAILS = Arrays.asList(
            "student.user@example.com", "another.user@example.com"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osignup);

        // --- Linking all UI elements ---
        fullnameEditText = findViewById(R.id.fullnameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        scrollView = findViewById(R.id.scrollView);
        googleSignUpButton = findViewById(R.id.googleSignUpButton);
        progressBar = findViewById(R.id.progressBar);
        toggleButtonGroup = findViewById(R.id.toggleButtonGroup);


        // --- SIGNUP BUTTON LOGIC (Unchanged) ---
        signupBtn.setOnClickListener(v -> {
            validateAndRegisterOwner();
        });


        // --- LOGIN TEXT LOGIC (Unchanged) ---
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(OsignupActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        // --- TOGGLE BUTTON GROUP LOGIC (Unchanged) ---
        toggleButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked && checkedId == R.id.userBtn) {
                Intent intent = new Intent(OsignupActivity.this, UsignupActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                group.check(R.id.ownerBtn);
            }
        });


        // --- GOOGLE BUTTON LOGIC (Unchanged) ---
        googleSignUpButton.setOnClickListener(v -> {
            showRealAccountChooser();
        });
    }


    // --- METHODS FOR VALIDATION AND REGISTRATION (Unchanged) ---
    private void validateAndRegisterOwner() {
        String fullName = fullnameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (fullName.isEmpty()) {
            fullnameEditText.setError("Full name is required");
            fullnameEditText.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            phoneEditText.setError("Contact number is required");
            phoneEditText.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            phoneEditText.setError("Please enter a valid 10-digit number");
            phoneEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        performOwnerRegistration(fullName, phone, password, confirmPassword);
    }

    private void performOwnerRegistration(String fullName, String phone, String password, String confirmPassword) {
        progressBar.setVisibility(View.VISIBLE);
        signupBtn.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // 2. The API call now correctly expects and uses StatusResponse
        Call<StatusResponse> call = apiService.registerOwner(fullName, phone, password, confirmPassword);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                progressBar.setVisibility(View.GONE);
                signupBtn.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    // 3. The response body is now correctly handled as a StatusResponse
                    StatusResponse statusResponse = response.body();
                    if ("success".equals(statusResponse.getStatus())) {
                        Toast.makeText(OsignupActivity.this, statusResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OsignupActivity.this, OstalldetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OsignupActivity.this, statusResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(OsignupActivity.this, "Registration failed. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                signupBtn.setEnabled(true);
                Log.e("OwnerSignupFailure", "onFailure: " + t.getMessage());
                Toast.makeText(OsignupActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- YOUR EXISTING HELPER METHODS (UNCHANGED) ---
    private void showRealAccountChooser() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        if (accounts.length == 0) {
            Toast.makeText(this, "No Google accounts found on this device.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> emailList = new ArrayList<>();
        for (Account account : accounts) {
            emailList.add(account.name);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an account to sign up");
        builder.setItems(emailList.toArray(new CharSequence[0]), (dialog, which) -> {
            String selectedEmail = emailList.get(which);
            handleAccountSelection(selectedEmail);
        });
        builder.create().show();
    }

    private void handleAccountSelection(String email) {
        String userName = deriveNameFromEmail(email);

        if (KNOWN_OWNER_EMAILS.contains(email)) {
            Toast.makeText(this, "Welcome back, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else if (KNOWN_USER_EMAILS.contains(email)) {
            Toast.makeText(this, "This account is already registered as a User. Logging in...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please provide your stall details, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OstalldetailsActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        }
    }

    private String deriveNameFromEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "User";
        }
        String namePart = email.split("@")[0];
        namePart = namePart.replace(".", " ").replace("_", " ");
        String[] nameTokens = namePart.split(" ");
        StringBuilder properName = new StringBuilder();
        for (String token : nameTokens) {
            if (token.length() > 0) {
                properName.append(Character.toUpperCase(token.charAt(0)))
                        .append(token.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return properName.toString().trim();
    }
}