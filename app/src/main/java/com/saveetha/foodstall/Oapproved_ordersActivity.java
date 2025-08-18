package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.adapter.OwnerApprovedOrdersAdapter;
import com.saveetha.foodstall.model.OwnerOrder;

import java.util.ArrayList;
import java.util.List;

public class Oapproved_ordersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oapproved_orders);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView approvedOrdersRecyclerView = findViewById(R.id.approvedOrdersRecyclerView);
        approvedOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<OwnerOrder> approvedOrders = getDummyApprovedOrdersData();
        OwnerApprovedOrdersAdapter adapter = new OwnerApprovedOrdersAdapter(approvedOrders);
        approvedOrdersRecyclerView.setAdapter(adapter);
    }

    private List<OwnerOrder> getDummyApprovedOrdersData() {
        List<OwnerOrder> orders = new ArrayList<>();
        orders.add(new OwnerOrder("ORD7902", "Sivan unavagam", "ID: 192210687", "4 items", "₹32.50", "2:30 PM"));
        orders.add(new OwnerOrder("ORD7903", "Sivan unavagam", "ID: 192210688", "2 items", "₹25.80", "2:25 PM"));
        orders.add(new OwnerOrder("ORD7904", "Sivan unavagam", "ID: 192210689", "3 items", "₹18.90", "2:20 PM"));
        orders.add(new OwnerOrder("ORD7905", "Sivan unavagam", "ID: 192210690", "1 item", "₹15.99", "2:15 PM"));
        orders.add(new OwnerOrder("ORD7906", "Sivan unavagam", "ID: 192210691", "5 items", "₹42.75", "2:10 PM"));
        orders.add(new OwnerOrder("ORD7907", "Sivan unavagam", "ID: 192210692", "2 items", "₹16.50", "2:05 PM"));
        orders.add(new OwnerOrder("ORD7908", "Sivan unavagam", "ID: 192210693", "3 items", "₹28.90", "2:00 PM"));
        return orders;
    }
}
