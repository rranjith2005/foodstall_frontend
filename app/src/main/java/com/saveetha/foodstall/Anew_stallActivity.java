package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.saveetha.foodstall.model.StallDetails;
import com.saveetha.foodstall.model.StallDetailsListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Anew_stallActivity extends AppCompatActivity implements StallDetailsAdapter.OnStallDetailsClickListener {

    private RecyclerView newStallsRecyclerView;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private TextView emptyViewText;
    private StallDetailsAdapter adapter;
    private List<StallDetails> stallList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anew_stall);

        newStallsRecyclerView = findViewById(R.id.newStallsRecyclerView);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        emptyViewText = findViewById(R.id.emptyViewText);

        setupRecyclerView();
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPendingStalls();
    }

    private void setupRecyclerView() {
        newStallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stallList = new ArrayList<>();
        adapter = new StallDetailsAdapter(stallList, this);
        newStallsRecyclerView.setAdapter(adapter);
    }

    private void fetchPendingStalls() {
        startLoadingAnimation();
        if (emptyViewText != null) emptyViewText.setVisibility(View.GONE);
        newStallsRecyclerView.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<StallDetailsListResponse> call = apiService.getOnlyPendingStalls();

        call.enqueue(new Callback<StallDetailsListResponse>() {
            @Override
            public void onResponse(Call<StallDetailsListResponse> call, Response<StallDetailsListResponse> response) {
                stopLoadingAnimation();
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    List<StallDetails> fetchedStalls = response.body().getStalls();

                    if (fetchedStalls != null && !fetchedStalls.isEmpty()) {
                        stallList.clear();
                        stallList.addAll(fetchedStalls);
                        adapter.notifyDataSetChanged();
                        newStallsRecyclerView.setVisibility(View.VISIBLE);
                    } else {
                        if (emptyViewText != null) emptyViewText.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(Anew_stallActivity.this, "Failed to load stalls.", Toast.LENGTH_SHORT).show();
                    if (emptyViewText != null) emptyViewText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StallDetailsListResponse> call, Throwable t) {
                stopLoadingAnimation();
                Log.e("FetchStallsFailure", "onFailure: " + t.getMessage());
                Toast.makeText(Anew_stallActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (emptyViewText != null) {
                    emptyViewText.setText("A network error occurred.");
                    emptyViewText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * This method will now correctly override the one in the listener interface.
     */
    @Override
    public void onViewClick(StallDetails stall) {
        Intent intent = new Intent(Anew_stallActivity.this, Astall_detailsActivity.class);
        intent.putExtra("STALL_DETAILS", stall);
        startActivity(intent);
    }

    private void startLoadingAnimation() {
        if (loadingOverlay != null && loadingIcon != null) {
            loadingOverlay.setVisibility(View.VISIBLE);
            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
            loadingIcon.startAnimation(rotation);
        }
    }

    private void stopLoadingAnimation() {
        if (loadingOverlay != null && loadingIcon != null) {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
    }

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
                return true;
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
}