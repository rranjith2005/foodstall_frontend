package com.saveetha.foodstall; // Replace with your package name

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

        RecyclerView specialsRecyclerView = findViewById(R.id.specials_recycler_view);
        specialsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SpecialsAdapter specialsAdapter = new SpecialsAdapter(getSpecialDishes());
        specialsRecyclerView.setAdapter(specialsAdapter);

        RecyclerView popularDishesRecyclerView = findViewById(R.id.popular_dishes_recycler_view);
        popularDishesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PopularDishesAdapter popularDishesAdapter = new PopularDishesAdapter(getPopularDishes());
        popularDishesRecyclerView.setAdapter(popularDishesAdapter);

        RecyclerView stallsRecyclerView = findViewById(R.id.stalls_recycler_view);
        stallsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StallsAdapter stallsAdapter = new StallsAdapter(getFoodStalls());
        stallsRecyclerView.setAdapter(stallsAdapter);
    }

    // Corrected getSpecialDishes() method
    private List<SpecialDish> getSpecialDishes() {
        List<SpecialDish> specials = new ArrayList<>();
        // Make sure each item has a different image and text
        specials.add(new SpecialDish("Margherita Pizza", "Pizza Hub", "$12.99", R.drawable.sd1));
        specials.add(new SpecialDish("Chicken Burger", "Burger House", "$8.99", R.drawable.sd2));
        // Add other special dishes here
        return specials;
    }

    // Corrected getPopularDishes() method
    private List<PopularDish> getPopularDishes() {
        List<PopularDish> populars = new ArrayList<>();
        // Make sure each item has a different image and text
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