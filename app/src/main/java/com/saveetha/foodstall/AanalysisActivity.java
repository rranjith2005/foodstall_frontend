package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AnalysisComparisonAdapter;
import com.saveetha.foodstall.model.AnalysisComparison;

import java.util.ArrayList;
import java.util.List;

public class AanalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aanalysis);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView comparisonRecyclerView = findViewById(R.id.comparisonRecyclerView);
        comparisonRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AnalysisComparison> comparisonDataList = getDummyComparisonData();
        AnalysisComparisonAdapter adapter = new AnalysisComparisonAdapter(comparisonDataList);
        comparisonRecyclerView.setAdapter(adapter);
    }

    private List<AnalysisComparison> getDummyComparisonData() {
        List<AnalysisComparison> data = new ArrayList<>();
        data.add(new AnalysisComparison("Aliyas", "Past Month: 2,500", "Present Month: 3,100", 25));
        data.add(new AnalysisComparison("Campus Cafe", "Past Month: 1,800", "Present Month: 1,750", -2));
        data.add(new AnalysisComparison("Food Fiesta", "Past Month: 4,200", "Present Month: 4,800", 14));
        data.add(new AnalysisComparison("Coffee Haven", "Past Month: 1,500", "Present Month: 2,300", 53));
        return data;
    }
}
