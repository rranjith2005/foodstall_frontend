package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AdminStallStatusAdapter;
import com.saveetha.foodstall.model.StallStatus;

import java.util.ArrayList;
import java.util.List;

public class Aapproved_stallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aapproved_stalls);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView approvedStallsRecyclerView = findViewById(R.id.approvedStallsRecyclerView);
        approvedStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StallStatus> approvedStalls = getDummyApprovedStallData();

        // --- THIS IS THE UPDATED PART ---
        // Pass 'null' as the second argument because this screen doesn't need a click listener
        AdminStallStatusAdapter adapter = new AdminStallStatusAdapter(approvedStalls, null);
        // --- END OF UPDATED PART ---

        approvedStallsRecyclerView.setAdapter(adapter);
    }

    private List<StallStatus> getDummyApprovedStallData() {
        List<StallStatus> stalls = new ArrayList<>();
        int[] stallImages = {R.drawable.stall_sample1, R.drawable.stall_sample2, R.drawable.stall_sample3};
        for (int i = 0; i < 10; i++) {
            int imageIndex = i % stallImages.length;
            stalls.add(new StallStatus(
                    stallImages[imageIndex],
                    "Stall Name " + (i + 1),
                    "Owner " + (i + 1),
                    "Approved",
                    ""
            ));
        }
        return stalls;
    }
}