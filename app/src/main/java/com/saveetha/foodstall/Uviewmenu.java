package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;

import com.saveetha.foodstall.model.Review;

import java.util.ArrayList;
import java.util.List;

public class Uviewmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uviewmenu);

        // This is how you would dynamically set the shop name title
        TextView shopNameTitle = findViewById(R.id.shop_name_title);
        // You would get the name from an Intent or a data source
        shopNameTitle.setText("Aliyas");

        // Setup Category Tabs RecyclerView
        RecyclerView categoriesRecyclerView = findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(getCategories());
        categoriesRecyclerView.setAdapter(categoryAdapter);

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

    // Dummy data generation methods
    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("All");
        categories.add("Pizza");
        categories.add("Sides");
        categories.add("Drinks");
        categories.add("Desserts");
        return categories;
    }

    private List<MenuItem> getMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Garlic Bread", "$4.99", R.drawable.garlic_bread));
        items.add(new MenuItem("Chicken Rice", "$6.99", R.drawable.chicken_rice));
        items.add(new MenuItem("Biryani", "$11.99", R.drawable.biryani));
        items.add(new MenuItem("Chocolate", "$3.99", R.drawable.chocolate));
        return items;
    }

    private List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Sarah M.", "Best pizza on campus! The cheese burst is amazing.", "2 days ago", "4.8", R.drawable.profile_sarah));
        reviews.add(new Review("John D.", "Great food and quick service. Love the garlic bread!", "1 week ago", "4.5", R.drawable.profile_john));
        return reviews;
    }
}