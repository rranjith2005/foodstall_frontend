package com.saveetha.foodstall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class AhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahome);

        setupTopBar();
        setupMetricCards();
        setupBarChart();
        setupActionCards();
        setupBottomNavigation();
    }

    private void setupTopBar() {
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Hi, Admin");

        ImageView settingsIcon = findViewById(R.id.settingsIcon);
        settingsIcon.setOnClickListener(v -> {
            // Example: Navigate to settings activity
            startActivity(new Intent(AhomeActivity.this, AsettingsActivity.class));
        });
    }

    private void setupMetricCards() {
        View totalStallsCard = findViewById(R.id.totalStallsCard);
        View approvedStallsCard = findViewById(R.id.approvedStallsCard);
        View rejectedStallsCard = findViewById(R.id.rejectedStallsCard);
        View pendingStallsCard = findViewById(R.id.pendingStallsCard);

        // Populate metric cards with new icons
        populateMetricCard(totalStallsCard, R.drawable.ic_totalstalls, "124", "Total Stalls");
        populateMetricCard(approvedStallsCard, R.drawable.ic_approved, "98", "Approved");
        populateMetricCard(rejectedStallsCard, R.drawable.ic_rejected, "12", "Rejected");
        populateMetricCard(pendingStallsCard, R.drawable.ic_pending, "14", "Pending");

        // Set click listeners for navigation
        totalStallsCard.setOnClickListener(v -> startActivity(new Intent(AhomeActivity.this, Aall_stallsActivity.class)));
        approvedStallsCard.setOnClickListener(v -> startActivity(new Intent(AhomeActivity.this, Aapproved_stallsActivity.class)));
        rejectedStallsCard.setOnClickListener(v -> startActivity(new Intent(AhomeActivity.this, Arejected_stallsActivity.class)));
        pendingStallsCard.setOnClickListener(v -> startActivity(new Intent(AhomeActivity.this, Apending_stallsActivity.class)));
    }

    /**
     * Helper method to populate a metric card with data.
     * This version simply sets the icon and text, assuming the layout handles styling.
     */
    private void populateMetricCard(View cardView, int iconResId, String count, String title) {
        ImageView icon = cardView.findViewById(R.id.metricIcon);
        TextView countText = cardView.findViewById(R.id.metricCount);
        TextView titleText = cardView.findViewById(R.id.metricTitle);

        icon.setImageResource(iconResId);
        countText.setText(count);
        titleText.setText(title);

        // No background setting here as it's handled by the layout now
    }

    private void setupBarChart() {
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 98f));
        entries.add(new BarEntry(1, 12f));
        entries.add(new BarEntry(2, 14f));

        BarDataSet dataSet = new BarDataSet(entries, "Stall Status");
        dataSet.setColors(Color.parseColor("#4CAF50"), Color.parseColor("#F44336"), Color.parseColor("#FFC107"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setFitBars(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        final String[] labels = new String[]{"Approved", "Rejected", "Pending"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);

        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }

    private void setupActionCards() {
        setActionCardData(findViewById(R.id.setLocationAction), R.drawable.ic_location_pin, "Set Location",
                v -> startActivity(new Intent(AhomeActivity.this, Aset_locationActivity.class)));
        setActionCardData(findViewById(R.id.viewReportsAction), R.drawable.ic_reports, "View Reports",
                v -> startActivity(new Intent(AhomeActivity.this, Aview_reportsActivity.class)));
        setActionCardData(findViewById(R.id.manageNotificationsAction), R.drawable.ic_notifications, "Manage Notifications",
                v -> startActivity(new Intent(AhomeActivity.this, AnotificationsActivity.class)));
    }

    private void setActionCardData(View cardView, int iconResId, String title, View.OnClickListener listener) {
        ImageView icon = cardView.findViewById(R.id.actionIcon);
        TextView titleText = cardView.findViewById(R.id.actionTitle);
        icon.setImageResource(iconResId);
        titleText.setText(title);
        cardView.setOnClickListener(listener);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_admin_home) {
                return true;
            } else if (itemId == R.id.nav_admin_new_stall) {
                startActivity(new Intent(getApplicationContext(), Anew_stallActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_admin_reviews) {
                startActivity(new Intent(getApplicationContext(), AreviewsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_admin_profile) {
                startActivity(new Intent(getApplicationContext(), AprofileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}