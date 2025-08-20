package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.NeedsAttentionAdapter;
import com.saveetha.foodstall.adapter.TopPerformerAdapter;
import com.saveetha.foodstall.model.NeedsAttentionItem;
import com.saveetha.foodstall.model.TopPerformer;

import java.util.ArrayList;
import java.util.List;

public class Otop_sellingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otop_selling);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView topPerformersRecyclerView = findViewById(R.id.topPerformersRecyclerView);
        topPerformersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<TopPerformer> performers = getDummyTopPerformerData();
        TopPerformerAdapter topPerformerAdapter = new TopPerformerAdapter(performers);
        topPerformersRecyclerView.setAdapter(topPerformerAdapter);

        RecyclerView needsAttentionRecyclerView = findViewById(R.id.needsAttentionRecyclerView);
        needsAttentionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<NeedsAttentionItem> attentionItems = getDummyNeedsAttentionData();
        NeedsAttentionAdapter needsAttentionAdapter = new NeedsAttentionAdapter(attentionItems);
        needsAttentionRecyclerView.setAdapter(needsAttentionAdapter);
    }

    private List<TopPerformer> getDummyTopPerformerData() {
        List<TopPerformer> performers = new ArrayList<>();
        // Corrected constructor calls with 7 parameters
        performers.add(new TopPerformer("Chicken Biryani", 120, "₹18,000", R.drawable.stall_sample1, true, true, false));
        performers.add(new TopPerformer("Butter Chicken", 98, "₹14,700", R.drawable.stall_sample1, true, false, false));
        performers.add(new TopPerformer("Paneer Tikka", 85, "₹12,750", R.drawable.stall_sample1, false, false, true));
        return performers;
    }

    private List<NeedsAttentionItem> getDummyNeedsAttentionData() {
        List<NeedsAttentionItem> items = new ArrayList<>();
        items.add(new NeedsAttentionItem("Veg Soup", 12, "₹180"));
        items.add(new NeedsAttentionItem("Dal Makhani", 15, "₹240"));
        items.add(new NeedsAttentionItem("Naan Bread", 8, "₹160"));
        return items;
    }
}
