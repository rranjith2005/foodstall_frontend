package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import com.saveetha.foodstall.adapter.FavoriteStallsAdapter;
import com.saveetha.foodstall.model.FavoriteStall;

import java.util.ArrayList;

public class Ufavorite_stallsActivity extends AppCompatActivity {

    private ArrayList<FavoriteStall> favoriteStalls;
    private FavoriteStallsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufavorite_stalls);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load your sample data
        favoriteStalls = getSampleFavorites();

        adapter = new FavoriteStallsAdapter(favoriteStalls, position -> {
            String removedStallName = favoriteStalls.get(position).getName();
            favoriteStalls.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(this, removedStallName + " removed from favorites", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }

    private ArrayList<FavoriteStall> getSampleFavorites() {
        ArrayList<FavoriteStall> list = new ArrayList<>();
        list.add(new FavoriteStall("Pizza Hub", R.drawable.stall_sample1));
        list.add(new FavoriteStall("Burger House", R.drawable.stall_sample2));
        list.add(new FavoriteStall("Sushi Bar", R.drawable.stall_sample3));
        return list;
    }
}