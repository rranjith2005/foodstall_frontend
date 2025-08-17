package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AeditstallAdapter;
import com.saveetha.foodstall.model.StallEdit;

import java.util.ArrayList;
import java.util.List;

public class AeditstallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aeditstall);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView stallsRecyclerView = findViewById(R.id.stallsRecyclerView);
        stallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StallEdit> stalls = getDummyStallData();
        AeditstallAdapter adapter = new AeditstallAdapter(stalls);
        stallsRecyclerView.setAdapter(adapter);
    }

    private List<StallEdit> getDummyStallData() {
        List<StallEdit> stalls = new ArrayList<>();
        stalls.add(new StallEdit("Food Fiesta", "S12345678", "John Smith", "12.345", "78.901"));
        stalls.add(new StallEdit("Spice Corner", "S12345679", "Rahul Kumar", "12.346", "78.902"));
        stalls.add(new StallEdit("Sweet Treats", "S12345680", "Sarah Wilson", "12.347", "78.903"));
        stalls.add(new StallEdit("Coffee Hub", "S12345681", "Mike Chen", "12.348", "78.904"));
        stalls.add(new StallEdit("Fresh Bites", "S12345682", "Emma Davis", "12.349", "78.905"));
        return stalls;
    }
}
