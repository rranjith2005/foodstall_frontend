package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.OwnerPendingOrdersAdapter;
import com.saveetha.foodstall.model.OwnerOrderPending;

import java.util.ArrayList;
import java.util.List;

public class Opending_ordersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opending_orders);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.searchButton).setOnClickListener(v -> {
            // Add search logic here
        });

        RecyclerView pendingOrdersRecyclerView = findViewById(R.id.pendingOrdersRecyclerView);
        pendingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<OwnerOrderPending> pendingOrders = getDummyPendingOrdersData();
        OwnerPendingOrdersAdapter adapter = new OwnerPendingOrdersAdapter(pendingOrders);
        pendingOrdersRecyclerView.setAdapter(adapter);
    }

    private List<OwnerOrderPending> getDummyPendingOrdersData() {
        List<OwnerOrderPending> orders = new ArrayList<>();
        orders.add(new OwnerOrderPending("ORD-2451", "Burger King", "Downtown Branch", "2x Whopper, 1x Fries, 1x Coke", "$24.50", "2h ago", "14:30"));
        orders.add(new OwnerOrderPending("ORD-2450", "Pizza Hut", "Central Mall", "1x Large Pepperoni Pizza, 2x Garlic Bread", "$32.75", "3h ago", "13:15"));
        orders.add(new OwnerOrderPending("ORD-2449", "Subway", "Station Square", "2x Italian BMT, 1x Chips", "$18.90", "4h ago", "12:45"));
        return orders;
    }
}
