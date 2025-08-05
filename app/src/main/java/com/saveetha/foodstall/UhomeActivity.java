package com.saveetha.foodstall;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.saveetha.foodstall.adapters.PopularDishesAdapter;
import com.saveetha.foodstall.adapters.SpecialsAdapter;
import com.saveetha.foodstall.adapters.StallsAdapter;
import com.saveetha.foodstall.models.PopularDish;
import com.saveetha.foodstall.models.SpecialItem;
import com.saveetha.foodstall.models.Stall;

import java.util.ArrayList;
import java.util.List;

public class UhomeActivity extends AppCompatActivity {

    private RecyclerView recyclerSpecials, recyclerPopularDishes, recyclerStalls;
    private SpecialsAdapter specialsAdapter;
    private PopularDishesAdapter popularDishesAdapter;
    private StallsAdapter stallsAdapter;

    private ChipGroup filterChips;
    private Chip chipOpenNow, chipTopRated;
    private TextView greetingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhome);

        // Header greeting
        greetingText = findViewById(R.id.greetingText);
        greetingText.setText("Hi, Ranjith"); // You can dynamically set user's name

        // RecyclerViews
        recyclerSpecials = findViewById(R.id.recyclerSpecials);
        recyclerPopularDishes = findViewById(R.id.recyclerPopularDishes);
        recyclerStalls = findViewById(R.id.recyclerStalls);

        // Filter Chips
        filterChips = findViewById(R.id.filterChips);
        chipOpenNow = findViewById(R.id.chipOpenNow);
        chipTopRated = findViewById(R.id.chipTopRated);

        // Setup adapters
        specialsAdapter = new SpecialsAdapter(this, getSpecialItems());
        popularDishesAdapter = new PopularDishesAdapter(this, getPopularDishes());
        stallsAdapter = new StallsAdapter(this, getStalls());

        // Setup layout managers
        recyclerSpecials.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerSpecials.setAdapter(specialsAdapter);

        recyclerPopularDishes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerPopularDishes.setAdapter(popularDishesAdapter);

        recyclerStalls.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerStalls.setAdapter(stallsAdapter);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_orders:
                        // startActivity(new Intent(UhomeActivity.this, OrdersActivity.class));
                        return true;
                    case R.id.nav_wallet:
                        // startActivity(new Intent(UhomeActivity.this, WalletActivity.class));
                        return true;
                    case R.id.nav_profile:
                        // startActivity(new Intent(UhomeActivity.this, ProfileActivity.class));
                        return true;
                }
                return false;
            }
        });

        // Chip click listeners (You can implement filters here)
        chipOpenNow.setOnClickListener(v -> filterStalls(true, null));
        chipTopRated.setOnClickListener(v -> filterStalls(null, 4.5f));
    }

    // Filter stalls
    private void filterStalls(Boolean openNow, Float minRating) {
        List<Stall> allStalls = getStalls();
        List<Stall> filtered = new ArrayList<>();
        for (Stall stall : allStalls) {
            boolean pass = true;
            if (openNow != null && stall.isOpen() != openNow) pass = false;
            if (minRating != null && stall.getRating() < minRating) pass = false;
            if (pass) filtered.add(stall);
        }
        stallsAdapter.setStalls(filtered);
    }

    // Dummy data for specials
    private List<SpecialItem> getSpecialItems() {
        List<SpecialItem> specials = new ArrayList<>();
        specials.add(new SpecialItem("Biryani", "Rs.120", "Ayilas", R.drawable.sd1));
        specials.add(new SpecialItem("Mandi Biryani", "Rs.150", "Shortie", R.drawable.sd2));
        return specials;
    }

    // Dummy data for popular dishes
    private List<PopularDish> getPopularDishes() {
        List<PopularDish> popular = new ArrayList<>();
        popular.add(new PopularDish("Pizza", "Rs.80", R.drawable.pd1));
        popular.add(new PopularDish("Burger", "Rs.90", R.drawable.pd2));
        popular.add(new PopularDish("Chicken 65", "Rs.70", R.drawable.pd3));
        return popular;
    }

    // Dummy data for food stalls
    private List<Stall> getStalls() {
        List<Stall> stalls = new ArrayList<>();
        stalls.add(new Stall("Ayilas", 4.5f, "10:00 AM - 8:00 PM", true, R.drawable.stall_sample1));
        stalls.add(new Stall("Shortie", 4.2f, "11:00 AM - 9:00 PM", false, R.drawable.stall_sample2));
        stalls.add(new Stall("Foodie Zone", 4.8f, "9:00 AM - 10:00 PM", true, R.drawable.stall_sample3));
        return stalls;
    }
}
