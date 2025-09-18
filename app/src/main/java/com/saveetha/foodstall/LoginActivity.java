package com.saveetha.foodstall;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.saveetha.foodstall.model.LoginResponse;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText idEditText;
    private TextInputEditText passwordEditText;
    private TextView signupText, forgotPasswordText;
    private Button loginBtn;
    private MaterialButton googleSignInButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;
    private static final List<String> KNOWN_OWNER_EMAILS = Arrays.asList("owner.one@example.com");
    private static final List<String> KNOWN_USER_EMAILS = Arrays.asList("student.user@example.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        signupText = findViewById(R.id.signupText);
        googleSignInButton = findViewById(R.id.googleSignInButton);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("YOUR_WEB_CLIENT_ID")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                        } catch (ApiException e) {
                            Log.w("GoogleSignIn", "Google sign in failed", e);
                        }
                    }
                });
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        loginBtn.setOnClickListener(v -> {
            String userId = idEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter ID and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userId.equals("admin") && password.equals("admin123")) {
                Toast.makeText(this, "Logging in as Admin...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, AhomeActivity.class));
                finish();
            } else {
                performLogin(userId, password);
            }
        });

        signupText.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, UsignupActivity.class)));
        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin(String identifier, String password) {
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(identifier, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                loginBtn.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus().equals("success")) {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        String role = loginResponse.getRole();
                        if (role == null) {
                            Toast.makeText(LoginActivity.this, "Role not found in response.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        LoginResponse.UserData userData = loginResponse.getData();
                        if (userData == null && (role.equals("owner_approved") || role.equals("student"))) {
                            Toast.makeText(LoginActivity.this, "User data missing in response.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        SharedPreferences sharedPreferences = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        switch (role) {
                            case "student":
                                // Save student details to SharedPreferences
                                editor.putString("STUDENT_ID", userData.getStudentId());
                                editor.putString("USER_NAME", userData.getFullname());
                                editor.putString("LOGGED_IN_ROLE", "student");
                                editor.apply();

                                // UPDATED: Ensure this intent points to UhomeActivity
                                Intent studentIntent = new Intent(LoginActivity.this, UhomeActivity.class);
                                startActivity(studentIntent);
                                finish();
                                break;

                            case "owner_approved":
                                // Save owner details to SharedPreferences
                                editor.putString("STALL_ID", userData.getStallId());
                                editor.putString("STALL_NAME", userData.getStallName());
                                editor.putString("LOGGED_IN_ROLE", "owner");
                                editor.apply();

                                Intent homeIntent = new Intent(LoginActivity.this, OhomeActivity.class);
                                startActivity(homeIntent);
                                finish();
                                break;

                            case "owner_status_check":
                                int stallStatus = (userData != null) ? userData.getStallStatus() : 0;
                                Intent statusIntent;
                                if (stallStatus == 1) { // Approved
                                    statusIntent = new Intent(LoginActivity.this, OapprovedActivity.class);
                                    if (userData != null) statusIntent.putExtra("STALL_ID", userData.getStallId());
                                } else if (stallStatus == -1) { // Rejected
                                    statusIntent = new Intent(LoginActivity.this, OrejectedActivity.class);
                                    if (userData != null) statusIntent.putExtra("REJECTION_REASON", userData.getRejectionReason());
                                } else { // Pending (status 0)
                                    statusIntent = new Intent(LoginActivity.this, OpendingActivity.class);
                                }
                                startActivity(statusIntent);
                                finish();
                                break;
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loginBtn.setEnabled(true);
                Log.e("LoginFailure", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        handleSuccessfulSignIn(mAuth.getCurrentUser());
                    } else {
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleSuccessfulSignIn(FirebaseUser user) {
        if (user == null) return;
        String email = user.getEmail();
        String userName = user.getDisplayName();
        if (userName == null || userName.isEmpty()){ userName = "User"; }

        if (email != null && KNOWN_OWNER_EMAILS.contains(email)) {
            Intent intent = new Intent(this, OhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else if (email != null && KNOWN_USER_EMAILS.contains(email)) {
            Intent intent = new Intent(this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else {
            showRoleSelectionDialog(userName);
        }
    }

    private void showRoleSelectionDialog(final String userName) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_role_selection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        Button ownerButton = dialog.findViewById(R.id.ownerButton);
        Button userButton = dialog.findViewById(R.id.userButton);
        ownerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, OstalldetailsActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });
        userButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });
        dialog.show();
    }
}