package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AdminStallsAdapter;
import com.saveetha.foodstall.model.StallAdmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Aall_stallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aall_stalls);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView allStallsRecyclerView = findViewById(R.id.allStallsRecyclerView);
        allStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StallAdmin> stalls = getDummyStallData();
        AdminStallsAdapter adapter = new AdminStallsAdapter(stalls);
        allStallsRecyclerView.setAdapter(adapter);
    }

    private List<StallAdmin> getDummyStallData() {
        List<StallAdmin> stalls = new ArrayList<>();
        stalls.add(new StallAdmin(R.drawable.stall_sample1, "Food Fiesta", "John Smith", "Approved"));
        stalls.add(new StallAdmin(R.drawable.stall_sample2, "Spice Corner", "Rahul Kumar", "Pending"));
        stalls.add(new StallAdmin(R.drawable.stall_sample3, "Sweet Treats", "Sarah Wilson", "Approved"));
        stalls.add(new StallAdmin(R.drawable.stall_sample1, "Coffee Hub", "Mike Chen", "Rejected"));
        stalls.add(new StallAdmin(R.drawable.stall_sample2, "Fresh Bites", "Emma Davis", "Approved"));
        stalls.add(new StallAdmin(R.drawable.stall_sample3, "Veggie Delight", "Priya Patel", "Pending"));
        stalls.add(new StallAdmin(R.drawable.stall_sample1, "Burger Station", "Alex Thompson", "Rejected"));
        stalls.add(new StallAdmin(R.drawable.stall_sample2, "Noodle House", "Liu Wei", "Approved"));
        stalls.add(new StallAdmin(R.drawable.stall_sample3, "Sandwich Stop", "Maria Garcia", "Pending"));
        stalls.add(new StallAdmin(R.drawable.stall_sample1, "Ice Cream Paradise", "David Brown", "Rejected"));
        return stalls;
    }
}
