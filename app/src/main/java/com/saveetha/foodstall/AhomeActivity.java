package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahome);

        // Populate the metric cards with data
        setMetricCardData(findViewById(R.id.totalStallsCard), R.drawable.ic_stall_count, "124", "Total Stalls", Color.parseColor("#2196F3"));
        setMetricCardData(findViewById(R.id.approvedStallsCard), R.drawable.ic_approved, "98", "Approved", Color.parseColor("#4CAF50"));
        setMetricCardData(findViewById(R.id.rejectedStallsCard), R.drawable.ic_rejected, "12", "Rejected", Color.parseColor("#F44336"));
        setMetricCardData(findViewById(R.id.pendingStallsCard), R.drawable.ic_pending, "14", "Pending", Color.parseColor("#FFC107"));

        // Populate the action cards with data and set up click listeners
        // The drawable names have been corrected here.
        setActionCardData(findViewById(R.id.setLocationAction), R.drawable.ic_location_pin, "Set Location", Color.parseColor("#FF6B6B"), v -> {
            Toast.makeText(AhomeActivity.this, "Set Location clicked", Toast.LENGTH_SHORT).show();
        });
        setActionCardData(findViewById(R.id.viewReportsAction), R.drawable.ic_reports, "View Reports", Color.parseColor("#FF6B6B"), v -> {
            Toast.makeText(AhomeActivity.this, "View Reports clicked", Toast.LENGTH_SHORT).show();
        });
        setActionCardData(findViewById(R.id.manageNotificationsAction), R.drawable.ic_notifications, "Manage Notifications", Color.parseColor("#FF6B6B"), v -> {
            Toast.makeText(AhomeActivity.this, "Manage Notifications clicked", Toast.LENGTH_SHORT).show();
        });

        // Set up Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_home);
    }

    // Helper method to populate the metric cards
    private void setMetricCardData(View cardView, int iconResId, String count, String title, int color) {
        ImageView icon = cardView.findViewById(R.id.metricIcon);
        TextView countText = cardView.findViewById(R.id.metricCount);
        TextView titleText = cardView.findViewById(R.id.metricTitle);

        icon.setImageResource(iconResId);
        icon.setBackgroundResource(R.drawable.circle_background_blue);
        icon.setColorFilter(Color.WHITE);
        countText.setText(count);
        titleText.setText(title);
    }

    // Helper method to populate the action cards and set their click listeners
    private void setActionCardData(View cardView, int iconResId, String title, int color, View.OnClickListener listener) {
        ImageView icon = cardView.findViewById(R.id.actionIcon);
        TextView titleText = cardView.findViewById(R.id.actionTitle);

        icon.setImageResource(iconResId);
        icon.setColorFilter(color);
        titleText.setText(title);
        cardView.setOnClickListener(listener);
    }
}