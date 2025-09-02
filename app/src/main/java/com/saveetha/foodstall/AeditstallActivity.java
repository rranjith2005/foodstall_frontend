package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import com.saveetha.foodstall.adapter.AeditstallAdapter;
import com.saveetha.foodstall.model.StallEdit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AeditstallActivity extends AppCompatActivity implements AeditstallAdapter.OnStallEditClickListener {

    private AeditstallAdapter adapter;
    private List<StallEdit> stallsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aeditstall);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Setup RecyclerView
        RecyclerView stallsRecyclerView = findViewById(R.id.stallsRecyclerView);
        stallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stallsList = getDummyStallData();
        adapter = new AeditstallAdapter(stallsList, this);
        stallsRecyclerView.setAdapter(adapter);

        // Setup SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No action needed on submit, filtering is real-time
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list as user types
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        List<StallEdit> filteredList = new ArrayList<>();
        for (StallEdit item : stallsList) {
            // Check if stall name or owner name contains the search text
            if (item.getStallName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) ||
                    item.getOwnerName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(item);
            }
        }
        // Update the adapter with the filtered list
        adapter.filterList(filteredList);
    }

    @Override
    public void onEditClick(StallEdit stall) {
        Intent intent = new Intent(this, AupdatestallActivity.class);
        startActivity(intent);
    }

    private List<StallEdit> getDummyStallData() {
        List<StallEdit> stalls = new ArrayList<>();
        int[] stallImages = {R.drawable.stall_sample1, R.drawable.stall_sample2, R.drawable.stall_sample3};

        stalls.add(new StallEdit(stallImages[0], "Food Fiesta", "S12345678", "John Smith", "12.345", "78.901"));
        stalls.add(new StallEdit(stallImages[1], "Spice Corner", "S12345679", "Rahul Kumar", "12.346", "78.902"));
        stalls.add(new StallEdit(stallImages[2], "Sweet Treats", "S12345680", "Sarah Wilson", "12.347", "78.903"));
        stalls.add(new StallEdit(stallImages[0], "Coffee Hub", "S12345681", "Mike Chen", "12.348", "78.904"));
        stalls.add(new StallEdit(stallImages[1], "Fresh Bites", "S12345682", "Emma Davis", "12.349", "78.905"));
        return stalls;
    }
}