package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.UpiAppAdapter;
import com.saveetha.foodstall.model.OrderItem;
import com.saveetha.foodstall.UpiApp;

import java.util.ArrayList;
import java.util.Locale;

public class Upayment_methodActivity extends AppCompatActivity {

    private Button payButton;
    private TextView totalToPayTextView, insufficientBalanceWarning, walletBalanceTextView;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private ArrayList<OrderItem> orderItems;
    private double totalAmount = 0.0;

    private LinearLayout walletLayout;
    private RadioButton walletRadioButton;
    private RecyclerView upiAppsRecyclerView;
    private UpiAppAdapter upiAppAdapter;
    private String selectedPaymentMethodName = "";
    private Button addMoneyButton;

    // --- NEW: ActivityResultLauncher to handle the result from adding money ---
    private final ActivityResultLauncher<Intent> addMoneyLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // When we return from the "Add Money" flow, this code runs.
                // We just need to refresh the wallet display to show the new balance.
                if (result.getResultCode() == RESULT_OK) {
                    updateWalletDisplay();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upayment_method);

        // Find views
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        payButton = findViewById(R.id.payButton);
        totalToPayTextView = findViewById(R.id.totalToPayTextView);
        walletLayout = findViewById(R.id.wallet_layout);
        walletRadioButton = findViewById(R.id.walletRadioButton);
        insufficientBalanceWarning = findViewById(R.id.insufficientBalanceWarning);
        addMoneyButton = findViewById(R.id.addMoneyButton);
        walletBalanceTextView = findViewById(R.id.walletBalanceTextView);

        setupUpiRecyclerView();

        orderItems = getIntent().getParcelableArrayListExtra("FINAL_ORDER_ITEMS");
        if (orderItems != null && !orderItems.isEmpty()) {
            calculateTotalAmount();
        } else {
            Toast.makeText(this, "Error: No order details found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        walletLayout.setOnClickListener(v -> handleWalletSelection());

        // --- UPDATED: The "Add Money" button now uses the launcher ---
        addMoneyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Uadd_moneyActivity.class);
            intent.putParcelableArrayListExtra("FINAL_ORDER_ITEMS", orderItems);
            addMoneyLauncher.launch(intent);
        });

        payButton.setOnClickListener(v -> {
            if (selectedPaymentMethodName.isEmpty()) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedPaymentMethodName.equals("Wallet") && totalAmount > WalletManager.getBalance()) {
                Toast.makeText(this, "Insufficient balance in wallet", Toast.LENGTH_SHORT).show();
                return;
            }

            showLoadingOverlay(() -> {
                Intent intent = new Intent(this, UreceiptActivity.class);
                intent.putParcelableArrayListExtra("RECEIPT_ORDER_ITEMS", orderItems);
                intent.putExtra("PAYMENT_METHOD", selectedPaymentMethodName);
                intent.putExtra("TOTAL_AMOUNT", totalAmount);
                startActivity(intent);
            });
        });

        updateWalletDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loadingOverlay != null) {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
        updateWalletDisplay();
    }

    private void handleWalletSelection() {
        walletRadioButton.setChecked(true);
        if (upiAppAdapter != null) {
            upiAppAdapter.clearSelection();
        }
        selectedPaymentMethodName = "Wallet";
        updatePayButtonText();
    }

    private void updateWalletDisplay() {
        double currentBalance = WalletManager.getBalance();
        walletBalanceTextView.setText(String.format(Locale.getDefault(), "Wallet Balance: ₹%.2f", currentBalance));
        if (totalAmount > currentBalance) {
            insufficientBalanceWarning.setVisibility(View.VISIBLE);
            addMoneyButton.setVisibility(View.VISIBLE);
        } else {
            insufficientBalanceWarning.setVisibility(View.GONE);
            addMoneyButton.setVisibility(View.GONE);
        }
    }

    private void setupUpiRecyclerView() {
        upiAppsRecyclerView = findViewById(R.id.upiAppsRecyclerView);
        upiAppsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<UpiApp> upiApps = new ArrayList<>();
        upiApps.add(new UpiApp("Google Pay", R.drawable.ic_gpay));
        upiApps.add(new UpiApp("PhonePe", R.drawable.ic_phonepe));
        upiApps.add(new UpiApp("Paytm", R.drawable.ic_paytm));
        upiApps.add(new UpiApp("Cash on Delivery", R.drawable.ic_cash_on_delivery));

        upiAppAdapter = new UpiAppAdapter(this, upiApps, upiApp -> {
            walletRadioButton.setChecked(false);
            selectedPaymentMethodName = upiApp.getName();
            updatePayButtonText();
        });
        upiAppsRecyclerView.setAdapter(upiAppAdapter);
    }

    private void updatePayButtonText() {
        if (!selectedPaymentMethodName.isEmpty()) {
            payButton.setText(String.format(Locale.getDefault(), "Pay ₹%.2f via %s", totalAmount, selectedPaymentMethodName));
        } else {
            payButton.setText(String.format(Locale.getDefault(), "Pay ₹%.2f", totalAmount));
        }
    }

    private void calculateTotalAmount() {
        double subtotal = 0, totalParcelFee = 0;
        final double PARCEL_FEE_PER_ITEM = 10.0;
        for (OrderItem item : orderItems) {
            subtotal += item.getPrice() * item.getQuantity();
            if (item.isPreParcel() || item.isParcel()) {
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
            }
        }
        totalAmount = subtotal + totalParcelFee;
        totalToPayTextView.setText(String.format(Locale.getDefault(), "₹%.2f", totalAmount));
        updatePayButtonText();
    }

    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);
        new Handler(Looper.getMainLooper()).postDelayed(onComplete, 800);
    }
}