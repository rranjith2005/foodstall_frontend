package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.PopularDishesAdapter;
import com.saveetha.foodstall.adapter.SpecialsAdapter;
import com.saveetha.foodstall.adapter.StallsAdapter;
import com.saveetha.foodstall.model.PopularDish;
import com.saveetha.foodstall.model.SpecialDish;
import com.saveetha.foodstall.model.Stall;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class UhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhome);

        // --- Setup Today's Specials RecyclerView ---
        RecyclerView specialsRecyclerView = findViewById(R.id.specials_recycler_view);
        specialsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SpecialsAdapter specialsAdapter = new SpecialsAdapter(getSpecialDishes());
        specialsRecyclerView.setAdapter(specialsAdapter);

        // --- Setup Popular Dishes RecyclerView ---
        RecyclerView popularDishesRecyclerView = findViewById(R.id.popular_dishes_recycler_view);
        popularDishesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PopularDishesAdapter popularDishesAdapter = new PopularDishesAdapter(getPopularDishes());
        popularDishesRecyclerView.setAdapter(popularDishesAdapter);

        // --- Setup Nearby Stalls RecyclerView ---
        RecyclerView stallsRecyclerView = findViewById(R.id.stalls_recycler_view);
        stallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StallsAdapter stallsAdapter = new StallsAdapter(getNearbyStalls());
        stallsRecyclerView.setAdapter(stallsAdapter);

        // --- Setup Bottom Navigation Bar ---
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_orders) {
                // Navigate to the orders page
                return true;
            } else if (itemId == R.id.nav_wallet) {
                // Navigate to the wallet page
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Navigate to the profile page
                return true;
            }
            return false;
        });
    }

    private List<SpecialDish> getSpecialDishes() {
        List<SpecialDish> specials = new ArrayList<>();
        specials.add(new SpecialDish("Briyani", "rs.90", R.drawable.sd1, "Stall A"));
        specials.add(new SpecialDish("Mandi Briyani", "rs.50", R.drawable.sd2, "Stall B"));
        specials.add(new SpecialDish("Chaya", "rs.100", R.drawable.sd3, "Stall C"));
        return specials;
    }

    private List<PopularDish> getPopularDishes() {
        List<PopularDish> populars = new ArrayList<>();
        populars.add(new PopularDish("pasta", "rs.120", R.drawable.pd1));
        populars.add(new PopularDish("burger", "rs.200", R.drawable.pd2));
        populars.add(new PopularDish("givigi", "rs.250", R.drawable.pd3));
        return populars;
    }

    private List<Stall> getNearbyStalls() {
        List<Stall> stalls = new ArrayList<>();
        stalls.add(new Stall("Aliyas", "4.8", "Open", "10:00 AM - 10:00 PM", R.drawable.stall_sample1));
        stalls.add(new Stall("Shortie", "4.5", "Close", "11:00 AM - 9:00 PM", R.drawable.stall_sample2));
        stalls.add(new Stall("Sivan Unavagam", "4.9", "Open", "8:30 AM - 8:30 PM", R.drawable.stall_sample3));
        return stalls;
    }
}