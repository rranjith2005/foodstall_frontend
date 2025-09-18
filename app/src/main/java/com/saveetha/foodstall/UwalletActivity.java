package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.TransactionAdapter;
import com.saveetha.foodstall.model.Transaction;
import com.saveetha.foodstall.model.WalletDetailsResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UwalletActivity extends AppCompatActivity {

    private TextView walletBalanceTextView;
    private RecyclerView transactionsRecyclerView;
    private TransactionAdapter adapter;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uwallet);

        walletBalanceTextView = findViewById(R.id.walletBalanceTextView);
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);

        SharedPreferences prefs = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
        studentId = prefs.getString("STUDENT_ID", null);

        if (studentId == null) {
            Toast.makeText(this, "Error: Could not verify user.", Toast.LENGTH_SHORT).show();
            finish(); return;
        }

        setupRecyclerView();

        Button addMoneyButton = findViewById(R.id.addMoneyButton);
        addMoneyButton.setOnClickListener(v -> {
            Intent intent = new Intent(UwalletActivity.this, Uadd_moneyActivity.class);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    private void setupRecyclerView() {
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(this, new ArrayList<>());
        transactionsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch fresh wallet data every time the screen is shown
        fetchWalletDetails();
    }

    private void fetchWalletDetails() {
        ApiClient.getClient().create(ApiService.class).getWalletDetails(studentId).enqueue(new Callback<WalletDetailsResponse>() {
            @Override
            public void onResponse(Call<WalletDetailsResponse> call, Response<WalletDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    WalletDetailsResponse.WalletData data = response.body().getData();
                    // Update the balance TextView
                    walletBalanceTextView.setText(String.format(Locale.getDefault(), "₹%.2f", data.getBalance()));
                    // Update the transaction list in the adapter
                    adapter.updateList(data.getTransactions());
                } else {
                    Toast.makeText(UwalletActivity.this, "Failed to load wallet details.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<WalletDetailsResponse> call, Throwable t) {
                Toast.makeText(UwalletActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigation() {
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
}