package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.OrdersAdapter;
import com.saveetha.foodstall.model.Order;

import java.util.ArrayList;
import java.util.List;

public class UordersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uorders);

        RecyclerView ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrdersAdapter ordersAdapter = new OrdersAdapter(getOrders());
        ordersRecyclerView.setAdapter(ordersAdapter);
    }

    private List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Pizza Hub", "ORD104783", "11:45 AM, 10 June 2024", "Chicken Roll × 1\nPuffs × 2", "Delivered", "₹120.00", null));
        orders.add(new Order("Chaat Corner", "ORD104782", "10:30 AM, 10 June 2024", "Pani Puri × 3\nMasala Dosa × 1", "Ready", "₹85.00", "Pick up by 2:30 PM"));
        orders.add(new Order("Burger House", "ORD104781", "10:00 AM, 10 June 2024", "Chicken Burger × 1", "Pending", "₹95.00", null));
        orders.add(new Order("Salad Shop", "ORD104780", "09:15 AM, 09 June 2024", "Caesar Salad × 1", "Delivered", "₹150.00", null));
        orders.add(new Order("Coffee Corner", "ORD104779", "09:00 AM, 09 June 2024", "Latte × 2\nCroissant × 1", "Cancelled", "₹200.00", null));
        return orders;
    }
}