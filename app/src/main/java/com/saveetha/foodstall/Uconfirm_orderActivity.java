package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.saveetha.foodstall.adapter.OrderItemAdapter;
import com.saveetha.foodstall.model.OrderItem;
import com.saveetha.foodstall.model.TimeSlotsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Uconfirm_orderActivity extends AppCompatActivity implements OrderItemAdapter.OnDataChangedListener {

    private ArrayList<OrderItem> orderItems;
    private TextView subtotalTextView, parcelFeeTextView, totalTextView, addMoreItemsButton;
    private Button proceedToPayButton;
    private LinearLayout parcelFeeLayout;
    private static final double PARCEL_FEE_PER_ITEM = 10.0;
    private LinearLayout preParcelLayout;
    private Spinner pickupTimeSpinner;
    private String stallId;

    // --- START OF UPDATED SECTION ---
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    // --- END OF UPDATED SECTION ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uconfirm_order);

        orderItems = getIntent().getParcelableArrayListExtra("CART_ITEMS");
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        stallId = getIntent().getStringExtra("STALL_ID");

        subtotalTextView = findViewById(R.id.subtotalTextView);
        parcelFeeTextView = findViewById(R.id.parcelFeeTextView);
        totalTextView = findViewById(R.id.totalTextView);
        proceedToPayButton = findViewById(R.id.proceedToPayButton);
        parcelFeeLayout = findViewById(R.id.parcelFeeLayout);
        addMoreItemsButton = findViewById(R.id.addMoreItemsButton);
        preParcelLayout = findViewById(R.id.preParcelLayout);
        pickupTimeSpinner = findViewById(R.id.pickupTimeSpinner);

        // --- START OF UPDATED SECTION ---
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        // --- END OF UPDATED SECTION ---

        RecyclerView orderItemsRecyclerView = findViewById(R.id.orderItemsRecyclerView);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderItemAdapter adapter = new OrderItemAdapter(orderItems, this, this);
        orderItemsRecyclerView.setAdapter(adapter);

        addMoreItemsButton.setOnClickListener(v -> finish());

        // --- START OF UPDATED SECTION ---
        // The click listener now triggers the loading animation
        proceedToPayButton.setOnClickListener(v -> {
            showLoadingOverlay(() -> {
                Intent intent = new Intent(Uconfirm_orderActivity.this, Upayment_methodActivity.class);
                intent.putParcelableArrayListExtra("FINAL_ORDER_ITEMS", orderItems);
                intent.putExtra("STALL_ID", stallId);

                if (preParcelLayout.getVisibility() == View.VISIBLE && pickupTimeSpinner.getSelectedItem() != null) {
                    String selectedTime = pickupTimeSpinner.getSelectedItem().toString();
                    intent.putExtra("PICKUP_TIME", selectedTime);
                }

                startActivity(intent);
            });
        });
        // --- END OF UPDATED SECTION ---

        updateBillDetails();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("UPDATED_CART_ITEMS", orderItems);
        setResult(RESULT_OK, data);
        super.finish();
    }

    private void updateBillDetails() {
        if (orderItems.isEmpty()) {
            Toast.makeText(this, "Cart is now empty!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        double subtotal = 0;
        double totalParcelFee = 0;
        boolean hasPreParcelItem = false;

        for (OrderItem item : orderItems) {
            subtotal += item.getPrice() * item.getQuantity();
            if (item.isPreParcel()) {
                hasPreParcelItem = true;
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
            } else if (item.isParcel()) {
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
            }
        }

        if (totalParcelFee > 0) {
            parcelFeeLayout.setVisibility(View.VISIBLE);
            parcelFeeTextView.setText(String.format(Locale.getDefault(), "₹%.2f", totalParcelFee));
        } else {
            parcelFeeLayout.setVisibility(View.GONE);
        }

        if (hasPreParcelItem) {
            preParcelLayout.setVisibility(View.VISIBLE);
            if (stallId != null) {
                fetchPickupTimes(stallId);
            } else {
                Toast.makeText(this, "Stall ID missing, cannot fetch pickup times.", Toast.LENGTH_SHORT).show();
            }
        } else {
            preParcelLayout.setVisibility(View.GONE);
        }

        double total = subtotal + totalParcelFee;

        subtotalTextView.setText(String.format(Locale.getDefault(), "₹%.2f", subtotal));
        totalTextView.setText(String.format(Locale.getDefault(), "₹%.2f", total));
        proceedToPayButton.setText(String.format(Locale.getDefault(), "Proceed to Pay ₹%.2f", total));
    }

    private void fetchPickupTimes(String stallIdToFetch) {
        ApiClient.getClient().create(ApiService.class).getPickupSlots(stallIdToFetch)
                .enqueue(new Callback<TimeSlotsResponse>() {
                    @Override
                    public void onResponse(Call<TimeSlotsResponse> call, Response<TimeSlotsResponse> response) {
                        if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                            List<String> slots = response.body().getTimeSlots();
                            if (slots != null && !slots.isEmpty()) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(Uconfirm_orderActivity.this, android.R.layout.simple_spinner_item, slots);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                pickupTimeSpinner.setAdapter(adapter);
                            } else {
                                Toast.makeText(Uconfirm_orderActivity.this, "No pickup slots available.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Uconfirm_orderActivity.this, "Failed to load pickup times.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeSlotsResponse> call, Throwable t) {
                        Log.e("PickupSlots", "API Failure: " + t.getMessage());
                        Toast.makeText(Uconfirm_orderActivity.this, "Network Error: Could not load pickup times.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // --- START OF ADDED SECTION ---
    // Added the showLoadingOverlay method to enable the animation
    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        // Assuming you have an animation resource named 'hourglass_rotation' in res/anim
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        // Simulate a short delay before proceeding to the next screen
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            if (onComplete != null) {
                onComplete.run();
            }
        }, 800); // 0.8-second delay
    }
    // --- END OF ADDED SECTION ---

    @Override
    public void onDataChanged() {
        updateBillDetails();
    }
}