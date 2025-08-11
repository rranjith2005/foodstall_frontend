package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.saveetha.foodstall.adapter.OrderItemAdapter;
import com.saveetha.foodstall.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Uconfirm_orderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uconfirm_order);

        // Setup RecyclerView for order items
        RecyclerView orderItemsRecyclerView = findViewById(R.id.orderItemsRecyclerView);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderItemAdapter adapter = new OrderItemAdapter(getOrderItems());
        orderItemsRecyclerView.setAdapter(adapter);

        // Set up button listeners
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // The closeButton listener has been removed to match your XML

        findViewById(R.id.proceedToPayButton).setOnClickListener(v -> {
            Toast.makeText(this, "Proceeding to pay...", Toast.LENGTH_SHORT).show();
            // Add your payment logic here
        });
    }

    private List<OrderItem> getOrderItems() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Margherita Pizza", 2, 298, false, null));
        items.add(new OrderItem("Chicken Rice", 1, 129, true, "12:30"));
        items.add(new OrderItem("Garlic Bread", 2, 98, false, null));
        return items;
    }
}