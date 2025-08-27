package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.adapter.RequestedStallsAdapter;
import com.saveetha.foodstall.model.RequestedStall;
import java.util.ArrayList;
import java.util.List;

public class Anew_stallActivity extends AppCompatActivity implements RequestedStallsAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anew_stall);

        RecyclerView newStallsRecyclerView = findViewById(R.id.newStallsRecyclerView);
        newStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<RequestedStall> requestedStalls = getDummyRequestedStallData();
        // Pass 'this' as the listener to handle clicks
        RequestedStallsAdapter adapter = new RequestedStallsAdapter(requestedStalls, this);
        newStallsRecyclerView.setAdapter(adapter);

        setupBottomNavigation();
    }

    /**
     * This method is called from the adapter when the "View" button is clicked.
     */
    @Override
    public void onViewClick(RequestedStall stall) {
        Intent intent = new Intent(Anew_stallActivity.this, Astall_detailsActivity.class);

        // Package all the stall's data to send to the details screen
        intent.putExtra("STALL_NAME", stall.getStallName());
        intent.putExtra("OWNER_NAME", stall.getOwnerName());
        intent.putExtra("REQUEST_DATE", stall.getRequestedDate());
        intent.putExtra("STALL_EMAIL", stall.getEmailAddress()); // Pass the new email field
        intent.putExtra("STALL_IMAGE_RES_ID", stall.getStallImageResId());

        startActivity(intent);
    }

    /**
     * Sets up the listener and selected item for the bottom navigation bar.
     */
    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_new_stall);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_admin_home) {
                startActivity(new Intent(getApplicationContext(), AhomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_admin_new_stall) {
                return true; // We are on this screen
            } else if (itemId == R.id.nav_admin_reviews) {
                startActivity(new Intent(getApplicationContext(), AreviewsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_admin_profile) {
                startActivity(new Intent(getApplicationContext(), AprofileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    /**
     * Creates a sample list of requested stalls with email addresses.
     */
    private List<RequestedStall> getDummyRequestedStallData() {
        List<RequestedStall> stalls = new ArrayList<>();
        stalls.add(new RequestedStall(R.drawable.stall_sample1, "Fresh Juice Corner", "Sarah Johnson", "sarah.j@example.com", "Oct 15, 2025"));
        stalls.add(new RequestedStall(R.drawable.stall_sample2, "Campus Coffee Hub", "Michael Chen", "michael.c@example.com", "Oct 14, 2025"));
        stalls.add(new RequestedStall(R.drawable.stall_sample3, "Sandwich Station", "Emma Williams", "emma.w@example.com", "Oct 14, 2025"));
        return stalls;
    }
}