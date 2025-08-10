package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.TransactionAdapter;
import com.saveetha.foodstall.model.Transaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UwalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uwallet);

        RecyclerView transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TransactionAdapter adapter = new TransactionAdapter(getTransactions());
        transactionsRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        // This is the line that highlights the 'Wallet' icon and text
        bottomNavigationView.setSelectedItemId(R.id.nav_wallet);
    }

    private List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        // These are distinct transactions with unique titles and amounts
        transactions.add(new Transaction(R.drawable.stall_sample3, "Cafe Central", "Today, 12:30 PM", "-4.50", true));
        transactions.add(new Transaction(R.drawable.stall_sample2, "Student Dining Hall", "Today, 10:15 AM", "-8.75", true));
        transactions.add(new Transaction(R.drawable.ic_add_money, "Added Money", "Yesterday, 3:45 PM", "+50.00", true));
        transactions.add(new Transaction(R.drawable.stall_sample1, "Bite & Go", "Yesterday, 1:20 PM", "-6.25", true));
        transactions.add(new Transaction(R.drawable.ic_add_money, "Added Money", "Jan 15, 2:30 PM", "+100.00", true));
        return transactions;
    }
}