package com.saveetha.foodstall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// --- START OF FIX ---
// 1. Import the correct DashboardResponse class from the model package
import com.saveetha.foodstall.model.DashboardResponse;
// --- END OF FIX ---

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OhomeActivity extends AppCompatActivity {

    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private TextView analyticsFilterText, stallNameTitle;
    private ImageView revenueChart;
    private CardView ordersTodayCard, revenueTodayCard, pendingOrdersCard, topSellingCard;
    private String stallId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ohome);

        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        analyticsFilterText = findViewById(R.id.analyticsFilterText);
        revenueChart = findViewById(R.id.revenueChart);
        stallNameTitle = findViewById(R.id.stallNameTitle);
        ordersTodayCard = findViewById(R.id.ordersTodayCard);
        revenueTodayCard = findViewById(R.id.revenueTodayCard);
        pendingOrdersCard = findViewById(R.id.pendingOrdersCard);
        topSellingCard = findViewById(R.id.topSellingCard);

        SharedPreferences sharedPreferences = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
        stallId = sharedPreferences.getString("STALL_ID", null);
        String stallName = sharedPreferences.getString("STALL_NAME", "Owner Dashboard");

        stallNameTitle.setText(stallName);

        if (stallId == null || stallId.isEmpty()) {
            Toast.makeText(this, "Error: Could not verify owner. Please log in again.", Toast.LENGTH_LONG).show();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
            return;
        }

        fetchDashboardData(stallId);

        findViewById(R.id.settingsIcon).setOnClickListener(v -> startActivity(new Intent(this, OsettingsActivity.class)));
        analyticsFilterText.setOnClickListener(v -> showAnalyticsFilterDialog());
        setupBottomNavigation();
    }

    private void fetchDashboardData(String stallId) {
        showLoadingOverlay(null);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // 2. This line will now compile correctly because of the new import
        Call<DashboardResponse> call = apiService.getDashboardData(stallId);

        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    loadingOverlay.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                        // The 'data' variable is now correctly typed
                        DashboardResponse.DashboardData data = response.body().getData();
                        updateUI(data);
                    } else {
                        Toast.makeText(OhomeActivity.this, "Failed to load dashboard data.", Toast.LENGTH_SHORT).show();
                    }
                }, 500);
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    loadingOverlay.setVisibility(View.GONE);
                    Log.e("DashboardFailure", "onFailure: " + t.getMessage());
                    Toast.makeText(OhomeActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }, 500);
            }
        });
    }

    // 3. The updateUI method now correctly accepts the nested DashboardData class
    private void updateUI(DashboardResponse.DashboardData data) {
        if (data == null) return;
        setMetricCardData(ordersTodayCard, R.drawable.ic_orders, String.valueOf(data.getOrdersToday()), "Orders Today");
        setMetricCardData(revenueTodayCard, R.drawable.ic_wallet, String.format(Locale.getDefault(), "₹%.2f", data.getRevenueToday()), "Revenue Today");
        setMetricCardData(pendingOrdersCard, R.drawable.ic_clock, String.valueOf(data.getPendingOrders()), "Pending Orders");
        setMetricCardData(topSellingCard, R.drawable.ic_dish, data.getTopSelling(), "Top Selling");
    }

    private void showAnalyticsFilterDialog() {
        final String[] options = {"Last 7 days", "Last 1 month"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Time Range");
        builder.setItems(options, (dialog, which) -> {
            analyticsFilterText.setText(options[which]);
            showLoadingOverlay(() -> {
                if (which == 0) {
                    revenueChart.setImageResource(R.drawable.ic_ochart1);
                } else {
                    revenueChart.setImageResource(R.drawable.ic_ochart2);
                }
                Toast.makeText(this, "Analytics updated", Toast.LENGTH_SHORT).show();
            });
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

    private void setMetricCardData(View cardView, int iconResId, String count, String title) {
        ImageView icon = cardView.findViewById(R.id.metricIcon);
        TextView countText = cardView.findViewById(R.id.metricCount);
        TextView titleText = cardView.findViewById(R.id.metricTitle);
        icon.setImageResource(iconResId);
        icon.setColorFilter(Color.parseColor("#FF6B6B"));
        countText.setText(count);
        titleText.setText(title);
    }

    private void showLoadingOverlay(@Nullable Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        if (onComplete != null) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                loadingIcon.clearAnimation();
                loadingOverlay.setVisibility(View.GONE);
                onComplete.run();
            }, 1000);
        }
    }
}