package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.adapter.AdminStallStatusAdapter;
import com.saveetha.foodstall.model.StallStatus;

import java.util.ArrayList;
import java.util.List;

public class Apending_stallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apending_stalls);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView pendingStallsRecyclerView = findViewById(R.id.pendingStallsRecyclerView);
        pendingStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StallStatus> pendingStalls = getDummyPendingStallData();
        AdminStallStatusAdapter adapter = new AdminStallStatusAdapter(pendingStalls);
        pendingStallsRecyclerView.setAdapter(adapter);
    }

    private List<StallStatus> getDummyPendingStallData() {
        List<StallStatus> stalls = new ArrayList<>();
        stalls.add(new StallStatus(R.drawable.stall_sample3, "Campus Cafe", "John Smith", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample2, "Veg Delight", "Neha Kapoor", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample1, "Coffee Corner", "Mike Wilson", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample3, "Fresh Bites", "Sarah Chen", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample2, "Sandwich Stop", "David Brown", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample1, "Asian Fusion", "Lisa Wang", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample3, "Smoothie Bar", "Alex Johnson", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample2, "Pizza Point", "Mario Romano", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample1, "Salad Station", "Emma White", "Pending", ""));
        stalls.add(new StallStatus(R.drawable.stall_sample3, "Snack Shack", "Tom Anderson", "Pending", ""));
        return stalls;
    }
}
