package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Oview_receiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oview_receipt);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Here you would get the order data from an Intent
        // For now, we'll use hardcoded values to populate the TextViews
        ((TextView) findViewById(R.id.orderIdTextView)).setText("Order ID: ORD7902");
        ((TextView) findViewById(R.id.studentIdTextView)).setText("Student ID: 192210687");
        // ... and so on for all the other TextViews

    }
}
