package com.saveetha.foodstall;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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

public class UsignupActivity extends AppCompatActivity {

    // UI ELEMENT DECLARATIONS
    private EditText fullnameEditText, idEditText, passwordEditText, confirmPasswordEditText, emailEditText;
    private Button signupBtn;
    private TextView loginText;
    private NestedScrollView scrollView;
    private MaterialButton googleSignUpButton;
    private ProgressBar progressBar;
    private MaterialButtonToggleGroup toggleButtonGroup;

    // SIMULATED DATABASE (Unchanged)
    private static final List<String> KNOWN_OWNER_EMAILS = Arrays.asList(
            "owner.one@example.com", "another.owner@example.com"
    );
    private static final List<String> KNOWN_USER_EMAILS = Arrays.asList(
            "student.user@example.com", "another.user@example.com"
    );

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usignup);

        // --- Linking all UI elements from the updated XML ---
        fullnameEditText = findViewById(R.id.fullnameEditText);
        idEditText = findViewById(R.id.idEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        scrollView = findViewById(R.id.scrollView);
        googleSignUpButton = findViewById(R.id.googleSignUpButton);
        progressBar = findViewById(R.id.progressBar);
        toggleButtonGroup = findViewById(R.id.toggleButtonGroup);

        // SIGNUP BUTTON LOGIC
        signupBtn.setOnClickListener(v -> {
            validateAndRegisterUser();
        });

        // OTHER LISTENERS
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(UsignupActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        toggleButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked && checkedId == R.id.ownerBtn) {
                Intent intent = new Intent(UsignupActivity.this, OsignupActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                group.check(R.id.userBtn);
            }
        });

        googleSignUpButton.setOnClickListener(v -> {
            showRealAccountChooser();
        });
    }

    private void validateAndRegisterUser() {
        String fullName = fullnameEditText.getText().toString().trim();
        String studentId = idEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (fullName.isEmpty()) {
            fullnameEditText.setError("Full name is required");
            fullnameEditText.requestFocus();
            return;
        }
        if (studentId.isEmpty()) {
            idEditText.setError("Student ID is required");
            idEditText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
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
        performRegistration(fullName, studentId, email, password, confirmPassword);
    }

    private void performRegistration(String fullName, String studentId, String email, String password, String confirmPassword) {
        progressBar.setVisibility(View.VISIBLE);
        signupBtn.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // 2. The API call now correctly expects and uses StatusResponse
        Call<StatusResponse> call = apiService.registerUser(fullName, studentId, email, password, confirmPassword);

        // 3. The Callback now correctly uses the StatusResponse type
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                progressBar.setVisibility(View.GONE);
                signupBtn.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    // 4. The response body is now correctly handled as a StatusResponse
                    StatusResponse statusResponse = response.body();
                    if ("success".equals(statusResponse.getStatus())) {
                        Toast.makeText(UsignupActivity.this, statusResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UsignupActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UsignupActivity.this, statusResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UsignupActivity.this, "Registration failed. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                signupBtn.setEnabled(true);
                Log.e("SignupFailure", "onFailure: " + t.getMessage());
                Toast.makeText(UsignupActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // HELPER METHODS FOR GOOGLE SIGN-IN SIMULATION (Unchanged)
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
            Toast.makeText(this, "This account is already registered as an Owner. Logging in...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, OhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else if (KNOWN_USER_EMAILS.contains(email)) {
            Toast.makeText(this, "Welcome back, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Welcome, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UhomeActivity.class);
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