package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.OwnerPendingOrdersAdapter;
import com.saveetha.foodstall.model.DetailedOrder;
import com.saveetha.foodstall.model.ReceiptItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Opending_ordersActivity extends AppCompatActivity implements OwnerPendingOrdersAdapter.OnOrderActionListener {

    private RecyclerView pendingOrdersRecyclerView;
    private OwnerPendingOrdersAdapter adapter;
    private List<DetailedOrder> pendingOrdersList = new ArrayList<>();
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opending_orders);

        // Setup UI
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());
        searchEditText = findViewById(R.id.searchEditText);
        pendingOrdersRecyclerView = findViewById(R.id.pendingOrdersRecyclerView);
        pendingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data and setup adapter
        fetchPendingOrders();
        // The adapter is now initialized with an unfiltered list
        adapter = new OwnerPendingOrdersAdapter(this, new ArrayList<>(pendingOrdersList), this);
        pendingOrdersRecyclerView.setAdapter(adapter);

        // Setup the search listener
        setupSearch();
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        List<DetailedOrder> filteredList = new ArrayList<>();
        for (DetailedOrder order : pendingOrdersList) {
            if (order.getOrderId().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) ||
                    order.getStudentId().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(order);
            }
        }
        adapter.filterList(filteredList);
    }

    private void fetchPendingOrders() {
        // In a real app, this data comes from your server
        // The list is stored in a member variable to be the "single source of truth"
        pendingOrdersList.clear();

        List<ReceiptItem> items1 = new ArrayList<>();
        items1.add(new ReceiptItem("Chicken Roll", 2, 40.00, true));
        items1.add(new ReceiptItem("Tea", 1, 15.00, false));
        DetailedOrder order1 = new DetailedOrder("ORD104780", "10 Jun, 9:00 AM", "Sivan Unavagam", "STU-54321", "OWNER123", "GPay", "Pre-parcel", "10 Jun, 10:00 AM", items1);

        List<ReceiptItem> items2 = new ArrayList<>();
        items2.add(new ReceiptItem("Veg Noodles", 1, 90.00, true));
        items2.add(new ReceiptItem("Water Bottle", 1, 20.00, false));
        DetailedOrder order2 = new DetailedOrder("ORD104781", "10 Jun, 9:05 AM", "Sivan Unavagam", "STU-12345", "OWNER123", "Cash", "Parcel", null, items2);

        pendingOrdersList.add(order1);
        pendingOrdersList.add(order2);
    }

    @Override
    public void onApproveClicked(int position) {
        // UPDATED: Use the new public method to get the item
        DetailedOrder order = adapter.getItem(position);
        Toast.makeText(this, "Approving " + order.getOrderId(), Toast.LENGTH_SHORT).show();

        // TODO: Add Retrofit API call to update the order status to "approved".
        // On success of your API call, execute the following lines:
        setResult(Activity.RESULT_OK); // Send a success result back to OhomeActivity
        finish(); // Close this activity
    }

    @Override
    public void onRejectClicked(int position) {
        // UPDATED: Use the new public method to get the item
        DetailedOrder order = adapter.getItem(position);
        Toast.makeText(this, "Rejecting " + order.getOrderId(), Toast.LENGTH_SHORT).show();

        // TODO: Add Retrofit API call to update the order status to "rejected".
        // On success of your API call, execute the following lines:
        setResult(Activity.RESULT_OK); // Also a "success" in that the action was completed
        finish(); // Close this activity
    }
}