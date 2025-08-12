package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Upayment_addedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upayment_added);

        // Set up the button listener to go back to the wallet
        findViewById(R.id.backToWalletButton).setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to wallet...", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }
}