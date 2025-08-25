package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.saveetha.foodstall.model.OrderItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class UreceiptActivity extends AppCompatActivity {

    private static final String OWNER_ID = "S12";
    private static final AtomicInteger orderCounter = new AtomicInteger(0);

    private ArrayList<OrderItem> orderItems;
    private double totalAmount;
    private String paymentMethod;

    private LinearLayout itemsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ureceipt);

        itemsContainer = findViewById(R.id.itemsContainer);

        Intent intent = getIntent();
        orderItems = intent.getParcelableArrayListExtra("RECEIPT_ORDER_ITEMS");
        totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0);
        paymentMethod = intent.getStringExtra("PAYMENT_METHOD");

        if (orderItems == null) {
            Toast.makeText(this, "Error: Could not load receipt details.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateReceiptDetails();

        findViewById(R.id.viewReceiptButton).setOnClickListener(v -> {
            startActivity(new Intent(this, UordersActivity.class));
        });

        findViewById(R.id.backToHomeButton).setOnClickListener(v -> {
            Intent homeIntent = new Intent(this, UhomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();
        });
    }

    private void populateReceiptDetails() {
        String uniqueOrderId = OWNER_ID + "-" + orderCounter.incrementAndGet();
        setRowText(R.id.orderIdRow, "Order ID", uniqueOrderId);

        String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
        setRowText(R.id.orderTimeRow, "Order Time", currentTime);

        setRowText(R.id.paymentMethodRow, "Paid via", paymentMethod);

        // --- DYNAMICALLY ADD ITEMS WITH DETAILS (UPDATED LOGIC) ---
        itemsContainer.removeAllViews();
        double subtotal = 0;
        double totalParcelFee = 0;
        final double PARCEL_FEE_PER_ITEM = 10.0;
        LayoutInflater inflater = LayoutInflater.from(this);

        for (OrderItem item : orderItems) {
            subtotal += item.getPrice() * item.getQuantity();

            // Inflate a new row for each item
            View itemView = inflater.inflate(R.layout.receipt_row, itemsContainer, false);
            TextView itemNameQty = itemView.findViewById(R.id.labelTextView);
            TextView itemPrice = itemView.findViewById(R.id.valueTextView);

            // Build the item description string
            StringBuilder itemDescription = new StringBuilder();
            itemDescription.append(item.getName());
            itemDescription.append(String.format(Locale.getDefault(), "\n× %d", item.getQuantity()));

            // Check for pre-parcel or parcel status
            if (item.isPreParcel()) {
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
                itemDescription.append(String.format(Locale.getDefault(), "\n(Pre-parcel: %s)", item.getPreParcelTime()));
            } else if (item.isParcel()) {
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
                itemDescription.append("\n(Parcel)");
            }

            itemNameQty.setText(itemDescription.toString());
            itemPrice.setText(String.format(Locale.getDefault(), "₹%.2f", item.getPrice() * item.getQuantity()));

            itemsContainer.addView(itemView);
        }

        // --- SET TOTALS (UPDATED LOGIC) ---
        setRowText(R.id.subtotalRow, "Subtotal", String.format(Locale.getDefault(), "₹%.2f", subtotal));

        View parcelFeeRow = findViewById(R.id.parcelFeeRow);
        if (totalParcelFee > 0) {
            parcelFeeRow.setVisibility(View.VISIBLE);
            setRowText(R.id.parcelFeeRow, "Parcel Fee", String.format(Locale.getDefault(), "₹%.2f", totalParcelFee));
        } else {
            parcelFeeRow.setVisibility(View.GONE);
        }

        setRowText(R.id.totalPaidRow, "Total Paid", String.format(Locale.getDefault(), "₹%.2f", totalAmount));
    }

    private void setRowText(int rowViewId, String label, String value) {
        View row = findViewById(rowViewId);
        if (row != null) {
            ((TextView) row.findViewById(R.id.labelTextView)).setText(label);
            ((TextView) row.findViewById(R.id.valueTextView)).setText(value);
        }
    }
}