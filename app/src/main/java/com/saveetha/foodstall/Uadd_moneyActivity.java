package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saveetha.foodstall.adapter.UpiAppAdapter;
import com.saveetha.foodstall.model.OrderItem;
import com.saveetha.foodstall.UpiApp;

import java.util.ArrayList;

public class Uadd_moneyActivity extends AppCompatActivity {

    private EditText amountEditText;
    private Button addMoneyPayButton;
    private RecyclerView paymentMethodsRecyclerView;
    private UpiAppAdapter upiAppAdapter;
    private String selectedPaymentMethodName = "";

    private ArrayList<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uadd_money);

        orderItems = getIntent().getParcelableArrayListExtra("FINAL_ORDER_ITEMS");

        amountEditText = findViewById(R.id.amountEditText);
        addMoneyPayButton = findViewById(R.id.addMoneyPayButton);

        setupPaymentRecyclerView();

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        addMoneyPayButton.setOnClickListener(v -> {
            String amount = amountEditText.getText().toString();

            if (amount.isEmpty() || Double.parseDouble(amount) <= 0) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedPaymentMethodName.isEmpty()) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            WalletManager.addMoney(Double.parseDouble(amount));

            Intent intent = new Intent(this, Upayment_addedActivity.class);
            intent.putExtra("ADDED_AMOUNT", amount);
            intent.putExtra("PAYMENT_METHOD", selectedPaymentMethodName);

            if (orderItems != null && !orderItems.isEmpty()) {
                intent.putParcelableArrayListExtra("FINAL_ORDER_ITEMS", orderItems);
            }

            intent.putExtra("came_from_wallet", getIntent().getBooleanExtra("came_from_wallet", false));

            startActivity(intent);

            // --- THIS IS THE UPDATED PART ---
            // Close this screen so the user doesn't return to it
            finish();
        });
    }

    private void setupPaymentRecyclerView() {
        paymentMethodsRecyclerView = findViewById(R.id.paymentMethodsRecyclerView);
        paymentMethodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<UpiApp> paymentMethods = new ArrayList<>();
        paymentMethods.add(new UpiApp("Google Pay", R.drawable.ic_gpay));
        paymentMethods.add(new UpiApp("PhonePe", R.drawable.ic_phonepe));
        paymentMethods.add(new UpiApp("Paytm", R.drawable.ic_paytm));

        upiAppAdapter = new UpiAppAdapter(this, paymentMethods, upiApp -> {
            selectedPaymentMethodName = upiApp.getName();
        });
        paymentMethodsRecyclerView.setAdapter(upiAppAdapter);
    }
}