package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AdminStallStatusAdapter;
import com.saveetha.foodstall.model.StallStatus;

import java.util.ArrayList;
import java.util.List;

public class Arejected_stallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arejected_stalls);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView rejectedStallsRecyclerView = findViewById(R.id.rejectedStallsRecyclerView);
        rejectedStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StallStatus> rejectedStalls = getDummyRejectedStallData();

        // --- THIS IS THE UPDATED PART ---
        // Pass 'null' as the second argument because this screen doesn't need a click listener
        AdminStallStatusAdapter adapter = new AdminStallStatusAdapter(rejectedStalls, null);
        // --- END OF UPDATED PART ---

        rejectedStallsRecyclerView.setAdapter(adapter);
    }

    private List<StallStatus> getDummyRejectedStallData() {
        List<StallStatus> stalls = new ArrayList<>();
        int[] stallImages = {R.drawable.stall_sample3, R.drawable.stall_sample2, R.drawable.stall_sample1};
        for (int i = 0; i < 10; i++) {
            int imageIndex = i % stallImages.length;
            stalls.add(new StallStatus(
                    stallImages[imageIndex],
                    "Stall Name " + (i + 1),
                    "Owner " + (i + 1),
                    "Rejected",
                    "Reason: Incomplete documentation"
            ));
        }
        return stalls;
    }
}