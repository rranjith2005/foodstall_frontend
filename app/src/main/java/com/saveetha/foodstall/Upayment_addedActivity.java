package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        doneButton.setOnClickListener(v -> {
            Intent paymentIntent = new Intent(Upayment_addedActivity.this, Upayment_methodActivity.class);
            paymentIntent.putParcelableArrayListExtra("FINAL_ORDER_ITEMS", orderItems);
            paymentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(paymentIntent);
            finish();
        });
    }
}
