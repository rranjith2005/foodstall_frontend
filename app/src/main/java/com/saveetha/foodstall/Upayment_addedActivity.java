package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.saveetha.foodstall.model.OrderItem;
import java.util.ArrayList;
import java.util.Locale;

public class Upayment_addedActivity extends AppCompatActivity {

    private TextView amountAddedTextView;
    private Button doneButton;
    private ArrayList<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upayment_added);

        amountAddedTextView = findViewById(R.id.amountAddedTextView);
        doneButton = findViewById(R.id.doneButton);

        Intent intent = getIntent();
        String amount = intent.getStringExtra("ADDED_AMOUNT");
        orderItems = intent.getParcelableArrayListExtra("FINAL_ORDER_ITEMS");

        if (amount != null && !amount.isEmpty()) {
            double amountValue = Double.parseDouble(amount);
            amountAddedTextView.setText(String.format(Locale.getDefault(), "₹%.2f", amountValue));
        }

        // --- THIS IS THE UPDATED PART ---
        boolean cameFromWallet = getIntent().getBooleanExtra("came_from_wallet", false);

        doneButton.setOnClickListener(v -> {
            if (cameFromWallet) {
                // This logic is for when you add money directly from the wallet.
                Intent walletIntent = new Intent(Upayment_addedActivity.this, UwalletActivity.class);
                walletIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(walletIntent);
                finish();
            } else {
                // THIS IS THE FIX for when you add money during an order.
                // It sends the order data back to Upayment_methodActivity.
                Intent resultIntent = new Intent();
                resultIntent.putParcelableArrayListExtra("FINAL_ORDER_ITEMS", orderItems);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        // --- END OF UPDATED PART ---
    }
}