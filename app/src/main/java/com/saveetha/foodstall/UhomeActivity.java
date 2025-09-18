package com.saveetha.foodstall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.adapter.PopularDishesAdapter;
import com.saveetha.foodstall.adapter.SpecialsAdapter;
import com.saveetha.foodstall.adapter.StallsAdapter;
import com.saveetha.foodstall.model.HomeDataResponse;
import com.saveetha.foodstall.model.PopularDish;
import com.saveetha.foodstall.model.SpecialDish;
import com.saveetha.foodstall.model.Stall;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UhomeActivity extends AppCompatActivity {

    private RecyclerView specialsRecyclerView, popularDishesRecyclerView, stallsRecyclerView;
    private SpecialsAdapter specialsAdapter;
    private PopularDishesAdapter popularDishesAdapter;
    private StallsAdapter stallsAdapter;
    private List<Stall> allStalls = new ArrayList<>();
    private List<SpecialDish> allSpecials = new ArrayList<>();
    private List<PopularDish> allPopularDishes = new ArrayList<>();
    private Button openNowBtn, topRatedBtn, favoriteBtn;
    private TextView welcomeTextView;
    private EditText searchBar;
    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;
    private String studentId;
    private String activeFilter = "open_now";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhome);

        bindViews();
        setFilterButtonSelected(openNowBtn);

        SharedPreferences sharedPreferences = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
        studentId = sharedPreferences.getString("STUDENT_ID", null);
        String userName = sharedPreferences.getString("USER_NAME", "User");
        welcomeTextView.setText("Hi, " + userName);

        if (studentId == null || studentId.isEmpty()) {
            Toast.makeText(this, "Error: Could not verify user. Please log in again.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            return;
        }

        setupRecyclerViews();
        fetchHomeData();
        setupFilterAndSearch();
        setupBottomNavigation();
    }

    private void bindViews(){
        welcomeTextView = findViewById(R.id.welcomeTextView);
        searchBar = findViewById(R.id.search_bar);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        openNowBtn = findViewById(R.id.openNowBtn);
        topRatedBtn = findViewById(R.id.topRatedBtn);
        favoriteBtn = findViewById(R.id.favoriteBtn);
        findViewById(R.id.settingsIcon).setOnClickListener(v -> startActivity(new Intent(UhomeActivity.this, UsettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (studentId != null) {
            fetchHomeData();
        }
    }

    private void setupRecyclerViews() {
        specialsRecyclerView = findViewById(R.id.specials_recycler_view);
        specialsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        specialsAdapter = new SpecialsAdapter();
        specialsRecyclerView.setAdapter(specialsAdapter);
        specialsAdapter.setOnItemClickListener(dish -> navigateToViewMenu(dish.getStallId()));

        popularDishesRecyclerView = findViewById(R.id.popular_dishes_recycler_view);
        popularDishesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularDishesAdapter = new PopularDishesAdapter();
        popularDishesRecyclerView.setAdapter(popularDishesAdapter);
        popularDishesAdapter.setOnItemClickListener(dish -> navigateToViewMenu(dish.getStallId()));

        stallsRecyclerView = findViewById(R.id.stalls_recycler_view);
        stallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stallsAdapter = new StallsAdapter();
        stallsRecyclerView.setAdapter(stallsAdapter);
        stallsAdapter.setOnItemClickListener(stall -> navigateToViewMenu(stall.getStallId()));
    }

    private void navigateToViewMenu(String stallId) {
        if (stallId != null && !stallId.isEmpty()) {
            Intent intent = new Intent(UhomeActivity.this, UviewmenuActivity.class);
            intent.putExtra("STALL_ID", stallId);
            startActivity(intent);
        }
    }

    private void fetchHomeData() {
        showLoadingOverlay();
        ApiClient.getClient().create(ApiService.class).getHomeData(studentId).enqueue(new Callback<HomeDataResponse>() {
            @Override
            public void onResponse(Call<HomeDataResponse> call, Response<HomeDataResponse> response) {
                hideLoadingOverlay();
                if (response.isSuccessful() && response.body() != null && response.body().getStatus().equals("success")) {
                    updateUI(response.body().getData());
                } else {
                    Toast.makeText(UhomeActivity.this, "Failed to load home screen data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeDataResponse> call, Throwable t) {
                hideLoadingOverlay();
                Toast.makeText(UhomeActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UhomeFailure", "API Call Failed: ", t);
            }
        });
    }

    // This is the only 'updateUI' method you need.
    private void updateUI(HomeDataResponse.HomeData data) {
        if (data == null) return;
        welcomeTextView.setText("Hi, " + data.getUserFullname());
        allSpecials.clear();
        allSpecials.addAll(data.getSpecials());
        allPopularDishes.clear();
        allPopularDishes.addAll(data.getPopularDishes());
        allStalls.clear();
        allStalls.addAll(data.getStalls());
        performSearchAndFilter();
    }

    private void setupFilterAndSearch() {
        openNowBtn.setOnClickListener(v -> { activeFilter = "open_now"; setFilterButtonSelected(openNowBtn); performSearchAndFilter(); });
        topRatedBtn.setOnClickListener(v -> { activeFilter = "top_rated"; setFilterButtonSelected(topRatedBtn); performSearchAndFilter(); });
        favoriteBtn.setOnClickListener(v -> { activeFilter = "favorite"; setFilterButtonSelected(favoriteBtn); performSearchAndFilter(); });
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { performSearchAndFilter(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearchAndFilter() {
        String query = searchBar.getText().toString().toLowerCase(Locale.getDefault());

        List<SpecialDish> filteredSpecials = allSpecials.stream()
                .filter(dish -> dish.getDishName().toLowerCase().contains(query) ||
                        dish.getStallName().toLowerCase().contains(query) ||
                        String.valueOf(dish.getPrice()).contains(query))
                .collect(Collectors.toList());
        specialsAdapter.submitList(filteredSpecials);

        List<PopularDish> filteredPopular = allPopularDishes.stream()
                .filter(dish -> dish.getDishName().toLowerCase().contains(query) ||
                        dish.getStallName().toLowerCase().contains(query) ||
                        String.valueOf(dish.getPrice()).contains(query))
                .collect(Collectors.toList());
        popularDishesAdapter.submitList(filteredPopular);

        List<Stall> searchedStalls = allStalls.stream()
                .filter(stall -> stall.getStallName().toLowerCase(Locale.getDefault()).contains(query))
                .collect(Collectors.toList());

        List<Stall> finalFilteredStalls;
        switch (activeFilter) {
            case "top_rated":
                finalFilteredStalls = new ArrayList<>(searchedStalls);
                Collections.sort(finalFilteredStalls, (s1, s2) -> Double.compare(s2.getRating(), s1.getRating()));
                break;
            case "favorite":
                finalFilteredStalls = searchedStalls.stream().filter(Stall::isFavorite).collect(Collectors.toList());
                break;
            case "open_now":
            default:
                finalFilteredStalls = searchedStalls.stream().filter(Stall::isOpen).collect(Collectors.toList());
                break;
        }
        stallsAdapter.submitList(finalFilteredStalls);
    }

    private void setFilterButtonSelected(Button selectedButton) {
        Button[] allButtons = {openNowBtn, topRatedBtn, favoriteBtn};
        for (Button button : allButtons) {
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.button_filter_active);
                button.setTextColor(ContextCompat.getColor(this, R.color.brand_pink));
            } else {
                button.setBackgroundResource(R.drawable.button_filter_inactive);
                button.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
        }
    }

    private void showLoadingOverlay() {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);
    }

    private void hideLoadingOverlay() {
        if (loadingOverlay.getVisibility() == View.VISIBLE) {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation_bar);
        bnv.setSelectedItemId(R.id.nav_home);
        bnv.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            if(i == R.id.nav_home) return true;
            else if (i == R.id.nav_orders) { startActivity(new Intent(getApplicationContext(), UordersActivity.class)); overridePendingTransition(0,0); finish(); return true; }
            else if (i == R.id.nav_wallet) { startActivity(new Intent(getApplicationContext(), UwalletActivity.class)); overridePendingTransition(0,0); finish(); return true; }
            else if (i == R.id.nav_profile) { startActivity(new Intent(getApplicationContext(), UeditprofileActivity.class)); overridePendingTransition(0,0); finish(); return true; }
            return false;
        });
    }
}