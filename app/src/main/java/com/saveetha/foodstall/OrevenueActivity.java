package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.RevenueDetailAdapter;
import com.saveetha.foodstall.model.RevenueDetail;

import java.util.ArrayList;
import java.util.List;

public class OrevenueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orevenue);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView revenueDetailsRecyclerView = findViewById(R.id.revenueDetailsRecyclerView);
        revenueDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<RevenueDetail> revenueDetails = getDummyRevenueData();
        RevenueDetailAdapter adapter = new RevenueDetailAdapter(revenueDetails);
        revenueDetailsRecyclerView.setAdapter(adapter);
    }

    private List<RevenueDetail> getDummyRevenueData() {
        List<RevenueDetail> data = new ArrayList<>();
        data.add(new RevenueDetail("2024-01-20", "156", "₹24567"));
        data.add(new RevenueDetail("2024-01-19", "142", "₹22345"));
        data.add(new RevenueDetail("2024-01-18", "168", "₹26789"));
        data.add(new RevenueDetail("2024-01-17", "134", "₹21234"));
        data.add(new RevenueDetail("2024-01-16", "158", "₹24567"));
        return data;
    }
}
