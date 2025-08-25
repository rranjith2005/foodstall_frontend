package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.saveetha.foodstall.adapter.OrderItemAdapter;
import com.saveetha.foodstall.model.OrderItem;
import java.util.ArrayList;
import java.util.Locale;

public class Uconfirm_orderActivity extends AppCompatActivity implements OrderItemAdapter.OnDataChangedListener {

    private ArrayList<OrderItem> orderItems;
    private TextView subtotalTextView, parcelFeeTextView, totalTextView, addMoreItemsButton;
    private Button proceedToPayButton;
    private LinearLayout parcelFeeLayout;

    private static final double PARCEL_FEE_PER_ITEM = 10.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uconfirm_order);

        orderItems = getIntent().getParcelableArrayListExtra("CART_ITEMS");
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        subtotalTextView = findViewById(R.id.subtotalTextView);
        parcelFeeTextView = findViewById(R.id.parcelFeeTextView);
        totalTextView = findViewById(R.id.totalTextView);
        proceedToPayButton = findViewById(R.id.proceedToPayButton);
        parcelFeeLayout = findViewById(R.id.parcelFeeLayout);
        addMoreItemsButton = findViewById(R.id.addMoreItemsButton);

        RecyclerView orderItemsRecyclerView = findViewById(R.id.orderItemsRecyclerView);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderItemAdapter adapter = new OrderItemAdapter(orderItems, this, this);
        orderItemsRecyclerView.setAdapter(adapter);

        // REMOVED: The OnClickListener for the backButton has been deleted.
        // findViewById(R.id.backButton).setOnClickListener(v -> finish());

        addMoreItemsButton.setOnClickListener(v -> finish());

        // UPDATED: This button now navigates to the payment activity
        proceedToPayButton.setOnClickListener(v -> {
            Intent intent = new Intent(Uconfirm_orderActivity.this, Upayment_methodActivity.class);
            // Pass the final order details to the payment screen
            intent.putParcelableArrayListExtra("FINAL_ORDER_ITEMS", orderItems);
            startActivity(intent);
        });

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
        for (OrderItem item : orderItems) {
            subtotal += item.getPrice() * item.getQuantity();
            if (item.isPreParcel() || item.isParcel()) {
                totalParcelFee += (PARCEL_FEE_PER_ITEM * item.getQuantity());
            }
        }

        if (totalParcelFee > 0) {
            parcelFeeLayout.setVisibility(View.VISIBLE);
            parcelFeeTextView.setText(String.format(Locale.getDefault(), "₹%.2f", totalParcelFee));
        } else {
            parcelFeeLayout.setVisibility(View.GONE);
        }

        double total = subtotal + totalParcelFee;

        subtotalTextView.setText(String.format(Locale.getDefault(), "₹%.2f", subtotal));
        totalTextView.setText(String.format(Locale.getDefault(), "₹%.2f", total));
        proceedToPayButton.setText(String.format(Locale.getDefault(), "Proceed to Pay ₹%.2f", total));
    }

    @Override
    public void onDataChanged() {
        updateBillDetails();
    }
}