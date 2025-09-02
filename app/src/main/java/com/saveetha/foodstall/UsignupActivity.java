package com.saveetha.foodstall;

// --- ADDED THESE IMPORTS ---
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
// --- END OF ADDED IMPORTS ---

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsignupActivity extends AppCompatActivity {

    EditText fullnameEditText, idEditText, passwordEditText, confirmPasswordEditText;
    Button signupBtn, userBtn, ownerBtn;
    TextView loginText;
    NestedScrollView scrollView;
    MaterialButton googleSignUpButton;

    // --- ADDED: SIMULATED DATABASE FOR KNOWN USERS ---
    // Note: We do NOT include the admin email on signup pages.
    private static final List<String> KNOWN_OWNER_EMAILS = Arrays.asList(
            "owner.one@example.com", "another.owner@example.com"
    );
    private static final List<String> KNOWN_USER_EMAILS = Arrays.asList(
            "student.user@example.com", "another.user@example.com"
    );
    // --- END OF ADDED SECTION ---

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usignup);

        // --- Linking UI elements (Unchanged) ---
        fullnameEditText = findViewById(R.id.fullnameEditText);
        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupBtn = findViewById(R.id.signupBtn);
        loginText = findViewById(R.id.loginText);
        userBtn = findViewById(R.id.userBtn);
        ownerBtn = findViewById(R.id.ownerBtn);
        scrollView = findViewById(R.id.scrollView);
        googleSignUpButton = findViewById(R.id.googleSignUpButton);

        setButtonSelected(userBtn, ownerBtn);

        // --- YOUR EXISTING LOGIC (UNCHANGED) ---
        signupBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UsignupActivity.this, UhomeActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(UsignupActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        userBtn.setOnClickListener(v -> {
            setButtonSelected(userBtn, ownerBtn);
            scrollView.smoothScrollTo(0, 0);
        });
        ownerBtn.setOnClickListener(v -> {
            setButtonSelected(ownerBtn, userBtn);
            Intent intent = new Intent(UsignupActivity.this, OsignupActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        // --- END OF EXISTING LOGIC ---


        // --- GOOGLE BUTTON LOGIC IS NOW UPDATED ---
        googleSignUpButton.setOnClickListener(v -> {
            showRealAccountChooser();
        });
        // --- END OF UPDATE ---
    }

    // --- ALL NEW METHODS ADDED BELOW ---

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
            // This user is already an owner, log them in.
            Toast.makeText(this, "This account is already registered as an Owner. Logging in...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, OhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else if (KNOWN_USER_EMAILS.contains(email)) {
            // This user is already a user, log them in.
            Toast.makeText(this, "Welcome back, " + userName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UhomeActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        } else {
            // This is a new account. Since they are on the USER signup page, register them as a USER.
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

    private void setButtonSelected(Button selectedButton, Button unselectedButton) {
        selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.pink));
        unselectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
        unselectedButton.setTextColor(ContextCompat.getColor(this, R.color.white));
    }
}