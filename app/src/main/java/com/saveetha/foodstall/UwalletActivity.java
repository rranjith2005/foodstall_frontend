package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.TransactionAdapter;
import com.saveetha.foodstall.model.Transaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UwalletActivity extends AppCompatActivity {

    private TextView walletBalanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uwallet);

        walletBalanceTextView = findViewById(R.id.walletBalanceTextView);

        // --- THIS IS THE UPDATED PART ---
        Button addMoneyButton = findViewById(R.id.addMoneyButton);
        addMoneyButton.setOnClickListener(v -> {
            Intent intent = new Intent(UwalletActivity.this, Uadd_moneyActivity.class);
            // Add a flag to signal where the navigation came from
            intent.putExtra("came_from_wallet", true);
            startActivity(intent);
        });
        // --- END OF UPDATED PART ---

        RecyclerView transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TransactionAdapter adapter = new TransactionAdapter(this, getTransactions());
        transactionsRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_wallet);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), UhomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_orders) {
                startActivity(new Intent(getApplicationContext(), UordersActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_wallet) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(getApplicationContext(), UeditprofileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        walletBalanceTextView.setText(String.format(Locale.getDefault(), "₹%.2f", WalletManager.getBalance()));
    }

    private List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(R.drawable.ic_add_money, "Added to Wallet", "Yesterday, 3:45 PM", "+ ₹500.00", true));
        transactions.add(new Transaction(R.drawable.stall_sample3, "Cafe Central", "Today, 12:30 PM", "- ₹120.00", true));
        transactions.add(new Transaction(R.drawable.stall_sample2, "Student Dining Hall", "Today, 10:15 AM", "- ₹85.00", true));
        return transactions;
    }
}