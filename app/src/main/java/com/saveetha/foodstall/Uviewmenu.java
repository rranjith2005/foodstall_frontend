package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import com.saveetha.foodstall.adapter.MenuItemAdapter;
import com.saveetha.foodstall.adapter.ReviewAdapter;
import com.saveetha.foodstall.model.MenuItem;
import com.saveetha.foodstall.model.Review;

import java.util.ArrayList;
import java.util.List;

public class Uviewmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uviewmenu);

        TextView shopNameTitle = findViewById(R.id.shop_name_title);
        shopNameTitle.setText("Aliyas");

        // Setup Menu Grid RecyclerView
        RecyclerView menuRecyclerView = findViewById(R.id.menu_recycler_view);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(getMenuItems());
        menuRecyclerView.setAdapter(menuItemAdapter);

        // Setup Reviews RecyclerView
        RecyclerView reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter reviewAdapter = new ReviewAdapter(getReviews());
        reviewsRecyclerView.setAdapter(reviewAdapter);
    }

    private List<MenuItem> getMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Garlic Bread", "$4.99", R.drawable.sd1));
        items.add(new MenuItem("Chicken Rice", "$6.99", R.drawable.sd2));
        items.add(new MenuItem("Biryani", "$11.99", R.drawable.sd3));
        items.add(new MenuItem("Chocolate", "$3.99", R.drawable.pd1));
        return items;
    }

    private List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Sai.G.J", "Best pizza on campus! The cheese burst is amazing.", "2 days ago", "4.8", R.drawable.sai));
        reviews.add(new Review("Nithi", "Great food and quick service. Love the garlic bread!", "1 week ago", "4.5", R.drawable.nithi));
        return reviews;
    }
}