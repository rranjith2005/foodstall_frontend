package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ohome);

        setMetricCardData(findViewById(R.id.ordersTodayCard), R.drawable.ic_orders, "32", "Orders Today", Color.parseColor("#FF6B6B"));
        setMetricCardData(findViewById(R.id.revenueTodayCard), R.drawable.ic_wallet, "₹4,580", "Revenue Today", Color.parseColor("#FF6B6B"));
        setMetricCardData(findViewById(R.id.pendingOrdersCard), R.drawable.ic_clock, "5", "Pending Orders", Color.parseColor("#FF6B6B"));
        setMetricCardData(findViewById(R.id.topSellingCard), R.drawable.ic_dish, "Butter Chicken", "Top Selling", Color.parseColor("#FF6B6B"));

        findViewById(R.id.settingsIcon).setOnClickListener(v -> {
            Toast.makeText(OhomeActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_owner_home);
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
}