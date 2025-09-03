package com.saveetha.foodstall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OhomeActivity extends AppCompatActivity {

    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private TextView analyticsFilterText;
    private ImageView revenueChart;

    // ADDED: Request code for identifying the result
    private static final int PENDING_ORDERS_REQUEST_CODE = 1;

    // ADDED: Member variables to hold the counts
    private int ordersTodayCount = 32;
    private int pendingOrdersCount = 5;
    // You would fetch these initial values from your API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ohome);

        // Find views
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        analyticsFilterText = findViewById(R.id.analyticsFilterText);
        revenueChart = findViewById(R.id.revenueChart);

        CardView pendingOrdersCard = findViewById(R.id.pendingOrdersCard);

        // Update the cards with initial data
        updateMetricCards();

        // Set up click listeners for metric cards
        findViewById(R.id.ordersTodayCard).setOnClickListener(v -> startActivity(new Intent(this, Otoday_ordersActivity.class)));
        findViewById(R.id.revenueTodayCard).setOnClickListener(v -> startActivity(new Intent(this, OrevenueActivity.class)));
        findViewById(R.id.topSellingCard).setOnClickListener(v -> startActivity(new Intent(this, Otop_sellingActivity.class)));

        // UPDATED: Use startActivityForResult for the pending orders card
        pendingOrdersCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, Opending_ordersActivity.class);
            startActivityForResult(intent, PENDING_ORDERS_REQUEST_CODE);
        });

        // Settings icon navigation
        findViewById(R.id.settingsIcon).setOnClickListener(v -> {
            showLoadingOverlay(() -> startActivity(new Intent(this, OsettingsActivity.class)));
        });

        // Analytics filter click listener
        analyticsFilterText.setOnClickListener(v -> showAnalyticsFilterDialog());

        setupBottomNavigation();
    }

    // NEW METHOD: Handles the result from Opending_ordersActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PENDING_ORDERS_REQUEST_CODE && resultCode == RESULT_OK) {
            // The user approved or rejected an order. Update the counts.
            // In a real app, you would re-fetch all counts from your server
            // to ensure data is perfectly in sync. For this simulation, we'll adjust locally.
            pendingOrdersCount--;
            ordersTodayCount++;

            // Refresh the UI with the new numbers
            updateMetricCards();
            Toast.makeText(this, "Dashboard updated!", Toast.LENGTH_SHORT).show();
        }
    }

    // NEW METHOD: Updates all metric cards with the current count values
    private void updateMetricCards() {
        CardView ordersTodayCard = findViewById(R.id.ordersTodayCard);
        CardView revenueTodayCard = findViewById(R.id.revenueTodayCard);
        CardView pendingOrdersCard = findViewById(R.id.pendingOrdersCard);
        CardView topSellingCard = findViewById(R.id.topSellingCard);

        // Populate card data using the member variables
        setMetricCardData(ordersTodayCard, R.drawable.ic_orders, String.valueOf(ordersTodayCount), "Orders Today", Color.parseColor("#FF6B6B"));
        setMetricCardData(revenueTodayCard, R.drawable.ic_wallet, "₹4,580", "Revenue Today", Color.parseColor("#FF6B6B")); // Revenue would also be re-fetched
        setMetricCardData(pendingOrdersCard, R.drawable.ic_clock, String.valueOf(pendingOrdersCount), "Pending Orders", Color.parseColor("#FF6B6B"));
        setMetricCardData(topSellingCard, R.drawable.ic_dish, "Butter Chicken", "Top Selling", Color.parseColor("#FF6B6B")); // Top selling would also be re-fetched
    }

    private void showAnalyticsFilterDialog() {
        final String[] options = {"Last 7 days", "Last 1 month"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Time Range");
        builder.setItems(options, (dialog, which) -> {
            analyticsFilterText.setText(options[which]);
            if (which == 0) {
                revenueChart.setImageResource(R.drawable.ic_ochart1);
            } else {
                revenueChart.setImageResource(R.drawable.ic_ochart2);
            }
        });
        builder.show();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_owner_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_owner_home) {
                return true;
            } else if (itemId == R.id.nav_owner_orders) {
                startActivity(new Intent(getApplicationContext(), OordersActivity.class));
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

    private void setMetricCardData(View cardView, int iconResId, String count, String title, int iconColor) {
        ImageView icon = cardView.findViewById(R.id.metricIcon);
        TextView countText = cardView.findViewById(R.id.metricCount);
        TextView titleText = cardView.findViewById(R.id.metricTitle);

        icon.setImageResource(iconResId);
        icon.setColorFilter(iconColor);
        countText.setText(count);
        titleText.setText(title);
    }

    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            onComplete.run();
        }, 1000);
    }
}