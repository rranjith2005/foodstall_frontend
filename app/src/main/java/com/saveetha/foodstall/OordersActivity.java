package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.saveetha.foodstall.adapter.OrderManagementAdapter;
import com.saveetha.foodstall.model.Order;
import com.saveetha.foodstall.model.OrderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OordersActivity extends AppCompatActivity implements OrderManagementAdapter.OrderClickListener {

    private RecyclerView ordersRecyclerView;
    private OrderManagementAdapter adapter;
    private EditText searchEditText;
    private MaterialButton inProgressFilterButton, completedFilterButton, canceledFilterButton;
    private MaterialButton todayButton, thisWeekButton, customRangeButton;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    private List<Order> allOrders = new ArrayList<>();
    private String currentStatusFilter = "In Progress";
    private String currentDateFilter = "Today";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oorders);

        // Find all views
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        inProgressFilterButton = findViewById(R.id.inProgressFilterButton);
        completedFilterButton = findViewById(R.id.completedFilterButton);
        canceledFilterButton = findViewById(R.id.canceledFilterButton);
        todayButton = findViewById(R.id.todayButton);
        thisWeekButton = findViewById(R.id.thisWeekButton);
        customRangeButton = findViewById(R.id.customRangeButton);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadOrderData();
        adapter = new OrderManagementAdapter(this, new ArrayList<>(), this);
        ordersRecyclerView.setAdapter(adapter);

        applyFilters();
        updateFilterButtonUI();

        setupStatusFilterListeners();
        setupDateFilterListeners();
        setupSearchListener();
        setupBottomNavigation();
    }

    private void setupStatusFilterListeners() {
        inProgressFilterButton.setOnClickListener(v -> {
            currentStatusFilter = "In Progress";
            updateFilterButtonUI();
            applyFilters();
        });
        completedFilterButton.setOnClickListener(v -> {
            currentStatusFilter = "Completed";
            updateFilterButtonUI();
            applyFilters();
        });
        canceledFilterButton.setOnClickListener(v -> {
            currentStatusFilter = "Cancelled";
            updateFilterButtonUI();
            applyFilters();
        });
    }

    private void setupDateFilterListeners() {
        todayButton.setOnClickListener(v -> {
            currentDateFilter = "Today";
            showLoadingOverlay(() -> {
                updateFilterButtonUI();
                loadOrderData();
                applyFilters();
            });
        });
        thisWeekButton.setOnClickListener(v -> {
            currentDateFilter = "This Week";
            showLoadingOverlay(() -> {
                updateFilterButtonUI();
                loadOrderData();
                applyFilters();
            });
        });
        customRangeButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        // This logic remains the same
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener startDateSetListener = (view, year, month, dayOfMonth) -> {
            Calendar startDate = Calendar.getInstance();
            startDate.set(year, month, dayOfMonth);
            DatePickerDialog.OnDateSetListener endDateSetListener = (view2, year2, month2, dayOfMonth2) -> {
                Calendar endDate = Calendar.getInstance();
                endDate.set(year2, month2, dayOfMonth2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.getDefault());
                String dateRange = sdf.format(startDate.getTime()) + " - " + sdf.format(endDate.getTime());
                showLoadingOverlay(() -> {
                    currentDateFilter = "Custom";
                    updateFilterButtonUI();
                    customRangeButton.setText(dateRange);
                    loadOrderData();
                    applyFilters();
                });
            };
            new DatePickerDialog(this, endDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        };
        new DatePickerDialog(this, startDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { applyFilters(); }
        });
    }

    // --- UPDATED METHOD: Uses isActivated for a much simpler and more reliable UI update ---
    private void updateFilterButtonUI() {
        // Update Status Filters
        inProgressFilterButton.setActivated(currentStatusFilter.equals("In Progress"));
        completedFilterButton.setActivated(currentStatusFilter.equals("Completed"));
        canceledFilterButton.setActivated(currentStatusFilter.equals("Cancelled"));

        // Update Date Filters
        todayButton.setActivated(currentDateFilter.equals("Today"));
        thisWeekButton.setActivated(currentDateFilter.equals("This Week"));
        customRangeButton.setActivated(currentDateFilter.equals("Custom"));
        if (!currentDateFilter.equals("Custom")) {
            customRangeButton.setText("Custom Range");
        }
    }

    private void applyFilters() {
        // This logic remains unchanged
        List<Order> filteredList = new ArrayList<>();
        String searchQuery = searchEditText.getText().toString().toLowerCase(Locale.ROOT);
        for (Order order : allOrders) {
            if (order.getStatus().equals(currentStatusFilter)) {
                if (searchQuery.isEmpty() || order.getOrderId().toLowerCase(Locale.ROOT).contains(searchQuery)) {
                    filteredList.add(order);
                }
            }
        }
        adapter.filterList(filteredList);
    }

    private void loadOrderData() {
        // This logic remains unchanged
        allOrders.clear();
        ArrayList<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem("Chicken Biryani", 2, 120, true, false, null));
        allOrders.add(new Order("Sivan Unavagam", "ORD-S12345", "2:30 PM", "In Progress", 280, "Cash Payment", "", items1));
        ArrayList<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem("Mutton Curry", 1, 180, true, false, null));
        allOrders.add(new Order("Ranjith Foods", "ORD-S12346", "1:45 PM", "Completed", 220, "Card Payment", "", items2));
        ArrayList<OrderItem> items3 = new ArrayList<>();
        items3.add(new OrderItem("Chicken 65", 2, 80, true, false, null));
        allOrders.add(new Order("Kumar Mess", "ORD-S12347", "12:30 PM", "Cancelled", 160, "Card Payment", "Out of Stock - Chicken", items3));
    }

    @Override
    public void onMarkAsCompleteClick(int position) {
        // This logic remains unchanged
        Order order = adapter.getItem(position);
        showLoadingOverlay(() -> {
            order.setStatus("Completed");
            applyFilters();
            Toast.makeText(this, order.getOrderId() + " marked as complete!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onViewReceiptClick(int position) {
        // This logic remains unchanged
        Order order = adapter.getItem(position);
        Intent intent = new Intent(this, Oview_receiptActivity.class);
        startActivity(intent);
    }

    private void setupBottomNavigation() {
        // This logic remains unchanged
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_owner_orders);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_owner_orders) {
                return true;
            } else if (itemId == R.id.nav_owner_home) {
                startActivity(new Intent(getApplicationContext(), OhomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_owner_menu) {
                startActivity(new Intent(getApplicationContext(), OmenuActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_owner_profile) {
                startActivity(new Intent(getApplicationContext(), OprofileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    private void showLoadingOverlay(Runnable onComplete) {
        // This logic remains unchanged
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            if (onComplete != null) {
                onComplete.run();
            }
        }, 1000);
    }
}