package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UviewcartActivity extends AppCompatActivity {

    private TextView cartSummaryText;
    private Button viewCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uviewcart);

        // Find views by their IDs
        cartSummaryText = findViewById(R.id.cartDetailsText);
        viewCartButton = findViewById(R.id.viewCartButton);

        // This is where you would get the cart data passed from the previous activity
        Intent intent = getIntent();
        if (intent != null) {
            String itemsCount = intent.getStringExtra("cartItemsCount");
            String totalAmount = intent.getStringExtra("cartTotalAmount");
            cartSummaryText.setText(itemsCount + " items • " + totalAmount);
        }

        // Set up the "View Cart" button listener
        viewCartButton.setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to checkout...", Toast.LENGTH_SHORT).show();
            // Add your navigation logic to the checkout page here
        });
    }
}