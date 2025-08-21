package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(Uconfirm_orderActivity.this, UviewmenuActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.proceedToPayButton).setOnClickListener(v -> {
            Toast.makeText(this, "Proceeding to pay...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Uconfirm_orderActivity.this, Upayment_methodActivity.class);
            startActivity(intent);
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