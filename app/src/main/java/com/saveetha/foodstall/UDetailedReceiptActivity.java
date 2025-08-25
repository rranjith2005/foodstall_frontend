package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saveetha.foodstall.model.Order;
import com.saveetha.foodstall.model.OrderItem;

import java.util.Locale;

public class UDetailedReceiptActivity extends AppCompatActivity {

    private Order order;
    private LinearLayout itemsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udetailed_receipt);

        itemsContainer = findViewById(R.id.itemsContainer);

        // Receive the full Order object from UordersActivity
        order = getIntent().getParcelableExtra("ORDER_DETAILS");

        if (order == null) {
            Toast.makeText(this, "Error: Could not load receipt details.", Toast.LENGTH_SHORT).show();
            finish(); // Go back if there's an error
            return;
        }

        populateReceiptDetails();

        // Set listeners for both back buttons to close this activity
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void populateReceiptDetails() {
        // Set the main details
        setRowText(R.id.orderIdRow, "Order ID", order.getOrderId());
        setRowText(R.id.orderTimeRow, "Order Time", order.getOrderTime());
        setRowText(R.id.stallNameRow, "Stall", order.getStallName());
        // You can add payment method to your Order model if you want to display it here
        findViewById(R.id.paymentMethodRow).setVisibility(View.GONE);

        // Dynamically add each ordered item to the list
        itemsContainer.removeAllViews();
        double subtotal = 0;
        double totalParcelFee = 0;
        final double PARCEL_FEE_PER_ITEM = 10.0;
        LayoutInflater inflater = LayoutInflater.from(this);

        for (OrderItem item : order.getOrderedItems()) {
            subtotal += item.getPrice() * item.getQuantity();
            if (item.isPreParcel() || item.isParcel()) {
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
            }

            View itemView = inflater.inflate(R.layout.receipt_row, itemsContainer, false);
            TextView itemNameQty = itemView.findViewById(R.id.labelTextView);
            TextView itemPrice = itemView.findViewById(R.id.valueTextView);

            itemNameQty.setText(String.format(Locale.getDefault(), "%s × %d", item.getName(), item.getQuantity()));
            itemPrice.setText(String.format(Locale.getDefault(), "₹%.2f", item.getPrice() * item.getQuantity()));
            itemsContainer.addView(itemView);
        }

        // Set the final totals
        setRowText(R.id.subtotalRow, "Subtotal", String.format(Locale.getDefault(), "₹%.2f", subtotal));

        View parcelFeeRow = findViewById(R.id.parcelFeeRow);
        if (totalParcelFee > 0) {
            parcelFeeRow.setVisibility(View.VISIBLE);
            setRowText(R.id.parcelFeeRow, "Parcel Fee", String.format(Locale.getDefault(), "₹%.2f", totalParcelFee));
        } else {
            parcelFeeRow.setVisibility(View.GONE);
        }

        setRowText(R.id.totalPaidRow, "Total Paid", String.format(Locale.getDefault(), "₹%.2f", order.getTotalPrice()));
    }

    private void setRowText(int rowViewId, String label, String value) {
        View row = findViewById(rowViewId);
        if (row != null) {
            ((TextView) row.findViewById(R.id.labelTextView)).setText(label);
            ((TextView) row.findViewById(R.id.valueTextView)).setText(value);
        }
    }
}