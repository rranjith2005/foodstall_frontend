package com.saveetha.foodstall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.saveetha.foodstall.model.OrderItem;
import com.saveetha.foodstall.model.OrderPlacementResponse;
import com.saveetha.foodstall.model.RazorpayOrderResponse;
import com.saveetha.foodstall.model.WalletBalanceResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upayment_methodActivity extends AppCompatActivity implements PaymentResultListener {

    private Button payButton, addMoneyButton;
    private TextView totalToPayTextView, insufficientBalanceWarning, walletBalanceTextView;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private RadioButton walletRadioButton, razorpayRadioButton;
    private ArrayList<OrderItem> orderItems;
    private double totalAmount = 0.0;

    private String stallId, studentId, studentEmail, studentPhone, pickupTime;
    private double walletBalance = 0.0;
    private String selectedPaymentMethod = "";

    private final ActivityResultLauncher<Intent> addMoneyLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    fetchWalletBalance();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upayment_method);

        Checkout.preload(getApplicationContext());
        bindViews();
        getIntentData();

        if (orderItems == null || orderItems.isEmpty() || stallId == null) {
            Toast.makeText(this, "Error: Order details are missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        calculateTotalAmount();
        setupClickListeners();
        fetchWalletBalance();
    }

    private void bindViews() {
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        payButton = findViewById(R.id.payButton);
        totalToPayTextView = findViewById(R.id.totalToPayTextView);
        walletRadioButton = findViewById(R.id.walletRadioButton);
        razorpayRadioButton = findViewById(R.id.razorpayRadioButton);
        insufficientBalanceWarning = findViewById(R.id.insufficientBalanceWarning);
        addMoneyButton = findViewById(R.id.addMoneyButton);
        walletBalanceTextView = findViewById(R.id.walletBalanceTextView);
    }

    private void getIntentData() {
        orderItems = getIntent().getParcelableArrayListExtra("FINAL_ORDER_ITEMS");
        stallId = getIntent().getStringExtra("STALL_ID");
        pickupTime = getIntent().getStringExtra("PICKUP_TIME");

        SharedPreferences prefs = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
        studentId = prefs.getString("STUDENT_ID", "");
        studentEmail = prefs.getString("USER_EMAIL", "test@example.com");
        studentPhone = prefs.getString("USER_PHONE", "9876543210");
    }

    private void setupClickListeners() {
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.wallet_layout).setOnClickListener(v -> selectPaymentMethod("Wallet"));
        findViewById(R.id.razorpay_layout).setOnClickListener(v -> selectPaymentMethod("Razorpay"));

        addMoneyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Uadd_moneyActivity.class);
            addMoneyLauncher.launch(intent);
        });

        payButton.setOnClickListener(v -> {
            if (selectedPaymentMethod.equals("Wallet")) {
                if (totalAmount > walletBalance) {
                    Toast.makeText(this, "Insufficient balance in wallet.", Toast.LENGTH_SHORT).show();
                } else {
                    placeWalletOrder();
                }
            } else if (selectedPaymentMethod.equals("Razorpay")) {
                startRazorpayFlow();
            } else {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectPaymentMethod(String method) {
        selectedPaymentMethod = method;
        if ("Wallet".equals(method)) {
            walletRadioButton.setChecked(true);
            razorpayRadioButton.setChecked(false);
        } else {
            walletRadioButton.setChecked(false);
            razorpayRadioButton.setChecked(true);
        }
        updatePayButtonText();
    }

    private void fetchWalletBalance() {
        showLoadingOverlay();
        ApiClient.getClient().create(ApiService.class).getWalletBalance(studentId).enqueue(new Callback<WalletBalanceResponse>() {
            @Override
            public void onResponse(Call<WalletBalanceResponse> call, Response<WalletBalanceResponse> response) {
                hideLoadingOverlay();
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    walletBalance = response.body().getBalance();
                    updateWalletDisplay();
                }
            }
            @Override
            public void onFailure(Call<WalletBalanceResponse> call, Throwable t) {
                hideLoadingOverlay();
            }
        });
    }

    private void updateWalletDisplay() {
        walletBalanceTextView.setText(String.format(Locale.getDefault(), "Balance: ₹%.2f", walletBalance));
        if (totalAmount > walletBalance) {
            insufficientBalanceWarning.setVisibility(View.VISIBLE);
            addMoneyButton.setVisibility(View.VISIBLE);
        } else {
            insufficientBalanceWarning.setVisibility(View.GONE);
            addMoneyButton.setVisibility(View.GONE);
        }
    }

    private void placeWalletOrder() {
        showLoadingOverlay();
        String orderItemsJson = new Gson().toJson(orderItems);
        ApiClient.getClient().create(ApiService.class)
                .placeWalletOrder(stallId, studentId, totalAmount, pickupTime, orderItemsJson)
                .enqueue(new Callback<OrderPlacementResponse>() {
                    @Override
                    public void onResponse(Call<OrderPlacementResponse> call, Response<OrderPlacementResponse> response) {
                        hideLoadingOverlay();
                        if(response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                            String displayOrderId = response.body().getDisplayOrderId();
                            navigateToReceipt("Wallet", "wallet_txn_" + System.currentTimeMillis(), displayOrderId);
                        } else {
                            String errorMsg = response.body() != null ? response.body().getMessage() : "Failed to place order.";
                            Toast.makeText(Upayment_methodActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<OrderPlacementResponse> call, Throwable t) {
                        hideLoadingOverlay();
                        Toast.makeText(Upayment_methodActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void startRazorpayFlow() {
        showLoadingOverlay();
        ApiClient.getClient().create(ApiService.class).createRazorpayOrder(totalAmount).enqueue(new Callback<RazorpayOrderResponse>() {
            @Override
            public void onResponse(Call<RazorpayOrderResponse> call, Response<RazorpayOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    initiateRazorpayPayment(response.body());
                } else {
                    hideLoadingOverlay();
                    Toast.makeText(Upayment_methodActivity.this, "Could not connect to payment gateway.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RazorpayOrderResponse> call, Throwable t) {
                hideLoadingOverlay();
                Toast.makeText(Upayment_methodActivity.this, "Network Error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateRazorpayPayment(RazorpayOrderResponse orderResponse) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Stall Spot");
            options.put("description", "Food Order Payment");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("order_id", orderResponse.getOrderId());
            options.put("theme.color", "#FF6B6B");
            options.put("currency", "INR");
            options.put("amount", orderResponse.getAmountInPaise());

            JSONObject prefill = new JSONObject();
            prefill.put("email", studentEmail);
            prefill.put("contact", studentPhone);
            options.put("prefill", prefill);

            hideLoadingOverlay();
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            hideLoadingOverlay();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        placeRazorpayOrderOnServer(razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String description) {
        Toast.makeText(this, "Payment failed: " + description, Toast.LENGTH_LONG).show();
    }

    private void placeRazorpayOrderOnServer(String paymentId) {
        showLoadingOverlay();
        String orderItemsJson = new Gson().toJson(orderItems);
        ApiClient.getClient().create(ApiService.class)
                .placeOrder(stallId, studentId, totalAmount, paymentId, pickupTime, orderItemsJson)
                .enqueue(new Callback<OrderPlacementResponse>() {
                    @Override
                    public void onResponse(Call<OrderPlacementResponse> call, Response<OrderPlacementResponse> response) {
                        hideLoadingOverlay();
                        if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                            String displayOrderId = response.body().getDisplayOrderId();
                            navigateToReceipt("Razorpay", paymentId, displayOrderId);
                        } else {
                            Toast.makeText(Upayment_methodActivity.this, "Payment successful, but failed to save order.", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<OrderPlacementResponse> call, Throwable t) {
                        hideLoadingOverlay();
                        Toast.makeText(Upayment_methodActivity.this, "Network error while saving order.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // UPDATED: This method now accepts the String displayOrderId
    private void navigateToReceipt(String paymentMethod, String paymentId, String displayOrderId) {
        Intent intent = new Intent(Upayment_methodActivity.this, UreceiptActivity.class);
        intent.putParcelableArrayListExtra("RECEIPT_ORDER_ITEMS", orderItems);
        intent.putExtra("PAYMENT_METHOD", paymentMethod);
        intent.putExtra("PAYMENT_ID", paymentId);
        intent.putExtra("TOTAL_AMOUNT", totalAmount);
        intent.putExtra("DISPLAY_ORDER_ID", displayOrderId); // Pass the new custom ID
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    private void updatePayButtonText() {
        if (!selectedPaymentMethod.isEmpty()) {
            payButton.setText(String.format(Locale.getDefault(), "Pay ₹%.2f via %s", totalAmount, selectedPaymentMethod));
        } else {
            payButton.setText(String.format(Locale.getDefault(), "Pay ₹%.2f", totalAmount));
        }
    }

    private void showLoadingOverlay() {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);
    }

    private void hideLoadingOverlay() {
        if (loadingOverlay.getVisibility() == View.VISIBLE) {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
    }
}