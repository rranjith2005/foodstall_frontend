package com.saveetha.foodstall;

// --- IMPORTS FOR FIREBASE AND GOOGLE SIGN-IN ---
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Arrays;
import java.util.List;
// --- END OF IMPORTS ---

public class LoginActivity extends AppCompatActivity {

    EditText idEditText;
    TextInputEditText passwordEditText;
    Button loginBtn;
    TextView signupText;
    MaterialButton googleSignInButton;

    // --- VARIABLES FOR FIREBASE AUTHENTICATION ---
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;


    // --- "DATABASE" OF KNOWN USERS AND ROLES ---
    // This is where we define which email belongs to which role.
    private static final String ADMIN_EMAIL = "admin@stallspot.com"; // Your special admin email for Google Sign-In
    private static final List<String> KNOWN_OWNER_EMAILS = Arrays.asList("owner.one@example.com"); // Add known owner emails here
    private static final List<String> KNOWN_USER_EMAILS = Arrays.asList("student.user@example.com"); // Add known user emails here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        signupText = findViewById(R.id.signupText);
        googleSignInButton = findViewById(R.id.googleSignInButton);

        // --- CONFIGURE FIREBASE AUTH AND GOOGLE SIGN-IN ---
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("PASTE YOUR WEB CLIENT ID HERE") // <-- PASTE YOUR KEY HERE
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
                            Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // --- BUTTON CLICK LISTENERS ---
        googleSignInButton.setOnClickListener(v -> {
            signInWithGoogle();
        });

        // Condition 1: Manual Admin Login
        loginBtn.setOnClickListener(v -> {
            String userId = idEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter ID and password", Toast.LENGTH_SHORT).show();
            } else {
                // Admin login check
                if (userId.equals("admin") && password.equals("admin123")) {
                    Toast.makeText(this, "Logging in as Admin...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AhomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                // Owner login check
                else if (userId.matches("^S\\d{5}$")) {
                    Toast.makeText(this, "Logging in as Owner...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, OhomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                // User login check (default)
                else {
                    Toast.makeText(this, "Logging in as User...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, UhomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, UsignupActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    // --- METHODS FOR GOOGLE SIGN-IN FLOW ---

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        // This method decides where to navigate
                        handleSuccessfulSignIn(user);
                    } else {
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Condition 2: Logic for Google Sign-In Navigation
    private void handleSuccessfulSignIn(FirebaseUser user) {
        if (user == null) return;

        String email = user.getEmail();
        String userName = user.getDisplayName();
        if (userName == null || userName.isEmpty()){
            userName = "User";
        }

        // Check if the email is a known Owner
        if (email != null && KNOWN_OWNER_EMAILS.contains(email)) {
            Toast.makeText(this, "Welcome back, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
            // Check if the email is a known User
        } else if (email != null && KNOWN_USER_EMAILS.contains(email)) {
            Toast.makeText(this, "Welcome back, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
            // Condition 3: If it's a new user, ask for their role
        } else {
            Toast.makeText(this, "First time with Google? Please select your role.", Toast.LENGTH_LONG).show();
            showRoleSelectionDialog(userName); // This dialog asks the user for their role.
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
            // New Owner goes to stall details page
            Toast.makeText(this, "Please provide your stall details, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, OstalldetailsActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });

        userButton.setOnClickListener(v -> {
            // New User goes to user home page
            Toast.makeText(this, "Welcome, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });

        dialog.show();
    }
}