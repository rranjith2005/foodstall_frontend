package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.RequestedStallsAdapter;
import com.saveetha.foodstall.model.RequestedStall;

import java.util.ArrayList;
import java.util.List;

public class Anew_stallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anew_stall);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView newStallsRecyclerView = findViewById(R.id.newStallsRecyclerView);
        newStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<RequestedStall> requestedStalls = getDummyRequestedStallData();
        RequestedStallsAdapter adapter = new RequestedStallsAdapter(requestedStalls);
        newStallsRecyclerView.setAdapter(adapter);

        // Set up Bottom Navigation Bar
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_new_stall);
    }

    private List<RequestedStall> getDummyRequestedStallData() {
        List<RequestedStall> stalls = new ArrayList<>();
        stalls.add(new RequestedStall(R.drawable.stall_sample1, "Fresh Juice Corner", "Sarah Johnson", "Oct 15, 2023"));
        stalls.add(new RequestedStall(R.drawable.stall_sample2, "Campus Coffee Hub", "Michael Chen", "Oct 14, 2023"));
        stalls.add(new RequestedStall(R.drawable.stall_sample3, "Sandwich Station", "Emma Williams", "Oct 14, 2023"));
        stalls.add(new RequestedStall(R.drawable.stall_sample1, "Healthy Bites", "David Miller", "Oct 13, 2023"));
        stalls.add(new RequestedStall(R.drawable.stall_sample2, "Sweet Treats", "Lisa Anderson", "Oct 13, 2023"));
        return stalls;
    }
}