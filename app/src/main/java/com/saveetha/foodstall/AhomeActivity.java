package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahome);

        // Populate the metric cards with data
        setMetricCardData(findViewById(R.id.totalStallsCard), R.drawable.ic_stall_count, "124", "Total Stalls");
        setMetricCardData(findViewById(R.id.approvedStallsCard), R.drawable.ic_approved, "98", "Approved");
        setMetricCardData(findViewById(R.id.rejectedStallsCard), R.drawable.ic_rejected, "12", "Rejected");
        setMetricCardData(findViewById(R.id.pendingStallsCard), R.drawable.ic_pending, "14", "Pending");

        // Set up click listeners for the action buttons
        findViewById(R.id.setLocationAction).setOnClickListener(v -> {
            Toast.makeText(AhomeActivity.this, "Set Location clicked", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.viewReportsAction).setOnClickListener(v -> {
            Toast.makeText(AhomeActivity.this, "View Reports clicked", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.manageNotificationsAction).setOnClickListener(v -> {
            Toast.makeText(AhomeActivity.this, "Manage Notifications clicked", Toast.LENGTH_SHORT).show();
        });

        // Set up Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_home);
    }

    private void setMetricCardData(View cardView, int iconResId, String count, String title) {
        ImageView icon = cardView.findViewById(R.id.metricIcon);
        TextView countText = cardView.findViewById(R.id.metricCount);
        TextView titleText = cardView.findViewById(R.id.metricTitle);

        icon.setImageResource(iconResId);
        countText.setText(count);
        titleText.setText(title);
    }
}