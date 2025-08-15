package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.saveetha.foodstall.adapter.ReportAdapter;
import com.saveetha.foodstall.model.ReportItem;

import java.util.ArrayList;
import java.util.List;

public class Aview_reportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aview_reports);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView reportsRecyclerView = findViewById(R.id.reportsRecyclerView);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ReportItem> reportItems = getDummyReportData();
        ReportAdapter adapter = new ReportAdapter(reportItems);
        reportsRecyclerView.setAdapter(adapter);
    }

    private List<ReportItem> getDummyReportData() {
        List<ReportItem> reports = new ArrayList<>();
        reports.add(new ReportItem("#1", "Tasty Bites Corner", "S12345678", "₹45,500", "Veg Roll - 320 sold"));
        reports.add(new ReportItem("#2", "Fresh Juice Hub", "S12345679", "₹38,750", "Mango Shake - 280 sold"));
        reports.add(new ReportItem("#3", "Snack Express", "S12345680", "₹32,200", "Samosa - 640 sold"));
        reports.add(new ReportItem("#4", "Coffee Paradise", "S12345681", "₹28,900", "Cold Coffee - 410 sold"));
        reports.add(new ReportItem("#5", "Healthy Bowls", "S12345682", "₹25,300", "Fruit Bowl - 180 sold"));
        return reports;
    }
}
