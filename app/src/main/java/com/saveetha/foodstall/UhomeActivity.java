package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saveetha.foodstall.adapter.PopularDishesAdapter;
import com.saveetha.foodstall.adapter.SpecialsAdapter;
import com.saveetha.foodstall.adapter.StallsAdapter;
import com.saveetha.foodstall.model.PopularDish;
import com.saveetha.foodstall.model.SpecialDish;
import com.saveetha.foodstall.model.Stall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UhomeActivity extends AppCompatActivity {

    private RecyclerView specialsRecyclerView, popularDishesRecyclerView, stallsRecyclerView;
    private StallsAdapter stallsAdapter;
    private List<Stall> allStalls;
    private Button openNowBtn, topRatedBtn;
    private ImageView settingsIcon;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uhome);

        // Find views
        searchBar = findViewById(R.id.search_bar);

        // --- Setup Specials and Popular Dishes RecyclerViews ---
        specialsRecyclerView = findViewById(R.id.specials_recycler_view);
        specialsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SpecialsAdapter specialsAdapter = new SpecialsAdapter(getSpecialDishes());
        specialsRecyclerView.setAdapter(specialsAdapter);

        // Add item click listener for Today's Specials
        specialsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, specialsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SpecialDish clickedDish = getSpecialDishes().get(position);
                Toast.makeText(UhomeActivity.this, "Opening menu for " + clickedDish.stallName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UhomeActivity.this, UviewmenuActivity.class);
                intent.putExtra("stallName", clickedDish.stallName);
                startActivity(intent);
            }
        }));

        popularDishesRecyclerView = findViewById(R.id.popular_dishes_recycler_view);
        popularDishesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PopularDishesAdapter popularDishesAdapter = new PopularDishesAdapter(getPopularDishes());
        popularDishesRecyclerView.setAdapter(popularDishesAdapter);

        // Add item click listener for Popular Dishes
        popularDishesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, popularDishesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PopularDish clickedDish = getPopularDishes().get(position);
                Toast.makeText(UhomeActivity.this, "Opening menu for " + clickedDish.dishName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UhomeActivity.this, UviewmenuActivity.class);
                intent.putExtra("dishName", clickedDish.dishName);
                startActivity(intent);
            }
        }));

        // --- Setup Food Stalls RecyclerView and Data ---
        stallsRecyclerView = findViewById(R.id.stalls_recycler_view);
        stallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allStalls = getFoodStalls();
        stallsAdapter = new StallsAdapter(allStalls);
        stallsRecyclerView.setAdapter(stallsAdapter);

        // Add item click listener for Food Stalls
        stallsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, stallsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Corrected line: We use the public getStalls() method to access the data
                Stall clickedStall = stallsAdapter.getStalls().get(position);
                Toast.makeText(UhomeActivity.this, "Opening menu for " + clickedStall.stallName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UhomeActivity.this, UviewmenuActivity.class);
                intent.putExtra("stallName", clickedStall.stallName);
                startActivity(intent);
            }
        }));

        // --- Navigation and Button Logic ---
        openNowBtn = findViewById(R.id.openNowBtn);
        topRatedBtn = findViewById(R.id.topRatedBtn);

        setFilterButtonSelected(openNowBtn, topRatedBtn);
        filterStalls("open_now");

        openNowBtn.setOnClickListener(v -> {
            setFilterButtonSelected(openNowBtn, topRatedBtn);
            filterStalls("open_now");
        });
        topRatedBtn.setOnClickListener(v -> {
            setFilterButtonSelected(topRatedBtn, openNowBtn);
            filterStalls("top_rated");
        });

        // Settings Icon Click Listener
        settingsIcon.setOnClickListener(v -> {
            startActivity(new Intent(UhomeActivity.this, UsettingsActivity.class));
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_orders) {
                startActivity(new Intent(UhomeActivity.this, UordersActivity.class));
            } else if (itemId == R.id.nav_wallet) {
                startActivity(new Intent(UhomeActivity.this, UwalletActivity.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(UhomeActivity.this, UeditprofileActivity.class));
            }
            return true;
        });
    }

    private void filterStalls(String filterType) {
        List<Stall> filteredList = new ArrayList<>();
        if (filterType.equals("open_now")) {
            for (Stall stall : allStalls) {
                if (stall.isOpen) {
                    filteredList.add(stall);
                }
            }
        } else if (filterType.equals("top_rated")) {
            filteredList.addAll(allStalls);
            Collections.sort(filteredList, (s1, s2) -> Double.compare(Double.parseDouble(s2.rating), Double.parseDouble(s1.rating)));
        } else {
            filteredList.addAll(allStalls);
        }
        stallsAdapter.updateList(filteredList);
    }

    private void setFilterButtonSelected(Button selectedButton, Button unselectedButton) {
        selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.pink));
        unselectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
        unselectedButton.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private List<SpecialDish> getSpecialDishes() {
        List<SpecialDish> specials = new ArrayList<>();
        specials.add(new SpecialDish("Margherita Pizza", "Pizza Hub", "$12.99", R.drawable.sd1));
        specials.add(new SpecialDish("Chicken Burger", "Burger House", "$8.99", R.drawable.sd2));
        specials.add(new SpecialDish("Biryani", "Biryani Spot", "$11.99", R.drawable.sd3));
        return specials;
    }

    private List<PopularDish> getPopularDishes() {
        List<PopularDish> populars = new ArrayList<>();
        populars.add(new PopularDish("Pasta", "$10.99", R.drawable.pd1));
        populars.add(new PopularDish("Burger", "$8.99", R.drawable.pd2));
        populars.add(new PopularDish("Pizza", "$12.99", R.drawable.pd3));
        populars.add(new PopularDish("Sushi", "$15.99", R.drawable.sd3));
        return populars;
    }

    private List<Stall> getFoodStalls() {
        List<Stall> stalls = new ArrayList<>();
        stalls.add(new Stall("Pizza Hub", "4.8", "10:00 AM - 10:00 PM", true, R.drawable.stall_sample1));
        stalls.add(new Stall("Burger House", "4.5", "11:00 AM - 9:00 PM", true, R.drawable.stall_sample2));
        stalls.add(new Stall("Sushi Bar", "4.9", "11:30 AM - 8:30 PM", false, R.drawable.stall_sample3));
        return stalls;
    }
}
