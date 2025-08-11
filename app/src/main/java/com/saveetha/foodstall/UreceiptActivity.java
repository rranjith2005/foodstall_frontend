package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class UreceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ureceipt);

        findViewById(R.id.viewReceiptButton).setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to orders...", Toast.LENGTH_SHORT).show();
            // Add your navigation logic here
        });

        findViewById(R.id.backToHomeButton).setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to home...", Toast.LENGTH_SHORT).show();
            // Add your navigation logic here
        });
    }
}