package com.saveetha.foodstall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.saveetha.foodstall.model.RazorpayOrderResponse;
import com.saveetha.foodstall.model.StatusResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Uadd_moneyActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    private EditText amountEditText;
    private Button addMoneyPayButton;
    private String studentId, studentEmail, studentPhone;
    private double amountToAdd = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uadd_money);

        Checkout.preload(getApplicationContext());

        amountEditText = findViewById(R.id.amountEditText);
        addMoneyPayButton = findViewById(R.id.addMoneyPayButton);

        SharedPreferences prefs = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
        studentId = prefs.getString("STUDENT_ID", "");
        studentEmail = prefs.getString("USER_EMAIL", "test@example.com");
        studentPhone = prefs.getString("USER_PHONE", "9876543210");

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        addMoneyPayButton.setOnClickListener(v -> startAddMoneyFlow());
    }

    private void startAddMoneyFlow() {
        String amountStr = amountEditText.getText().toString();
        if (amountStr.isEmpty() || Double.parseDouble(amountStr) <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        amountToAdd = Double.parseDouble(amountStr);
        addMoneyPayButton.setEnabled(false);

        // Step 1: Create a Razorpay Order
        ApiClient.getClient().create(ApiService.class).createRazorpayOrder(amountToAdd).enqueue(new Callback<RazorpayOrderResponse>() {
            @Override
            public void onResponse(Call<RazorpayOrderResponse> call, Response<RazorpayOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    initiateRazorpayPayment(response.body());
                } else {
                    Toast.makeText(Uadd_moneyActivity.this, "Could not connect to payment gateway.", Toast.LENGTH_SHORT).show();
                    addMoneyPayButton.setEnabled(true);
                }
            }
            @Override
            public void onFailure(Call<RazorpayOrderResponse> call, Throwable t) {
                Toast.makeText(Uadd_moneyActivity.this, "Network Error. Please try again.", Toast.LENGTH_SHORT).show();
                addMoneyPayButton.setEnabled(true);
            }
        });
    }

    private void initiateRazorpayPayment(RazorpayOrderResponse orderResponse) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Stall Spot Wallet");
            options.put("description", "Add Money to Wallet");
            options.put("order_id", orderResponse.getOrderId());
            options.put("theme.color", "#FF6B6B");
            options.put("currency", "INR");
            options.put("amount", orderResponse.getAmountInPaise());
            JSONObject prefill = new JSONObject();
            prefill.put("email", studentEmail);
            prefill.put("contact", studentPhone);
            options.put("prefill", prefill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            addMoneyPayButton.setEnabled(true);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
        // Step 2: Payment is successful, now tell our server to add the money
        addMoneyPayButton.setEnabled(true);
        updateWalletOnServer(razorpayPaymentId, paymentData.getOrderId(), paymentData.getSignature());
    }

    @Override
    public void onPaymentError(int code, String description, PaymentData paymentData) {
        Toast.makeText(this, "Payment failed: " + description, Toast.LENGTH_LONG).show();
        addMoneyPayButton.setEnabled(true);
    }

    private void updateWalletOnServer(String paymentId, String orderId, String signature) {
        ApiClient.getClient().create(ApiService.class)
                .addMoneyToWallet(studentId, amountToAdd, paymentId, orderId, signature)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                            Toast.makeText(Uadd_moneyActivity.this, "Money added to wallet!", Toast.LENGTH_LONG).show();
                            // Signal to the previous screen that the wallet was updated
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(Uadd_moneyActivity.this, "Payment successful, but failed to update wallet. Please contact support.", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Toast.makeText(Uadd_moneyActivity.this, "Network error while updating wallet. Please contact support.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}