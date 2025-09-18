package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Uprivacy_policyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uprivacy_policy);

        // Set up the back button to navigate back to the previous screen (Settings)
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());
    }
}