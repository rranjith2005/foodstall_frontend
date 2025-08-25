package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.adapter.OrdersAdapter;
import com.saveetha.foodstall.model.Order;
import com.saveetha.foodstall.model.OrderItem;

import java.util.ArrayList;

public class UordersActivity extends AppCompatActivity implements OrdersAdapter.OnOrderClickListener {

    private RecyclerView ordersRecyclerView;
    private ImageView searchButton;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uorders);

        searchButton = findViewById(R.id.searchButton);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);

        setupBottomNavigation();

        searchButton.setOnClickListener(v -> {
            startActivity(new Intent(UordersActivity.this, SearchpageActivity.class));
        });

        setupOrdersList();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_orders);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), UhomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_orders) {
                return true;
            } else if (itemId == R.id.nav_wallet) {
                startActivity(new Intent(getApplicationContext(), UwalletActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_profile) {
                // THIS IS THE NEW NAVIGATION PART
                startActivity(new Intent(getApplicationContext(), UeditprofileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    private void setupOrdersList() {
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Order> orderList = new ArrayList<>();

        // Order 1
        ArrayList<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem("Chicken Roll", 1, 60, false, null));
        items1.add(new OrderItem("Puffs", 2, 30, false, null));
        orderList.add(new Order("Pizza Hub", "ORD104783", "10 Jun, 11:45 AM", "Delivered", 120.00, items1));

        // Order 2
        ArrayList<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem("Pani Puri", 3, 15, false, null));
        items2.add(new OrderItem("Masala Dosa", 1, 40, false, null));
        orderList.add(new Order("Chaat Corner", "ORD104782", "10 Jun, 10:30 AM", "Pending", 85.00, items2));

        // Order 3
        ArrayList<OrderItem> items3 = new ArrayList<>();
        items3.add(new OrderItem("Tea", 3, 15, false, null));
        items3.add(new OrderItem("Chicken Roll", 1, 40, false, null));
        orderList.add(new Order("Sivan Unavagam", "ORD104780", "10 Jun, 9:00 AM", "Cancelled", 85.00, items3));

        // Order 4
        ArrayList<OrderItem> items4 = new ArrayList<>();
        items4.add(new OrderItem("Coffee", 3, 15, false, null));
        items4.add(new OrderItem("Egg Puffs", 1, 40, false, null));
        orderList.add(new Order("Aliyas", "ORD104779", "09 Jun, 5:30 PM", "Rejected", 85.00, items4));

        OrdersAdapter adapter = new OrdersAdapter(this, orderList, this);
        ordersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewReceiptClick(Order order) {
        Intent intent = new Intent(this, UDetailedReceiptActivity.class);
        intent.putExtra("ORDER_DETAILS", order);
        startActivity(intent);
    }
}