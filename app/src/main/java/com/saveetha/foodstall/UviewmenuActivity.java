package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.adapter.MenuItemAdapter;
import com.saveetha.foodstall.adapter.ReviewAdapter;
import com.saveetha.foodstall.model.MenuItem;
import com.saveetha.foodstall.model.Review;

import java.util.ArrayList;
import java.util.List;

public class UviewmenuActivity extends AppCompatActivity {

    private RecyclerView menuRecyclerView, reviewsRecyclerView;
    private TextView shopNameTitle, cartDetailsText;
    private ImageView searchButton, backButton, loadingIcon;
    private Button addToCartButton, viewCartButton, lovedByAddButton;
    private View bottomCartBar, loadingOverlay;
    private List<MenuItem> menuItems;

    // Cart state variables
    private int cartItemCount = 0;
    private double cartTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uviewmenu);

        shopNameTitle = findViewById(R.id.shop_name_title);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        lovedByAddButton = findViewById(R.id.lovedByAddButton);

        bottomCartBar = findViewById(R.id.bottomCartBar);
        cartDetailsText = findViewById(R.id.cartDetailsText);
        viewCartButton = findViewById(R.id.viewCartButton);

        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);

        bottomCartBar.setVisibility(View.GONE);

        shopNameTitle.setText("Aliyas");

        menuItems = getMenuItems();

        // This is the corrected line. It passes the new OnItemAddedListener to the adapter.
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(menuItems, (name, price) -> {
            cartItemCount++;
            cartTotal += price;
            updateCartBar();
            Toast.makeText(UviewmenuActivity.this, name + " added to cart!", Toast.LENGTH_SHORT).show();
        });

        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        menuRecyclerView.setAdapter(menuItemAdapter);

        reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter reviewAdapter = new ReviewAdapter(getReviews());
        reviewsRecyclerView.setAdapter(reviewAdapter);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(UviewmenuActivity.this, UhomeActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(UviewmenuActivity.this, SearchpageActivity.class);
            startActivity(intent);
        });

        addToCartButton.setOnClickListener(v -> {
            cartItemCount++;
            cartTotal += 12.99;
            updateCartBar();
            Toast.makeText(this, "Item added to cart!", Toast.LENGTH_SHORT).show();
        });

        lovedByAddButton.setOnClickListener(v -> {
            cartItemCount++;
            cartTotal += 10.99;
            updateCartBar();
            Toast.makeText(this, "Margherita Pizza added to cart!", Toast.LENGTH_SHORT).show();
        });

        viewCartButton.setOnClickListener(v -> {
            showLoadingOverlay(() -> {
                Intent intent = new Intent(UviewmenuActivity.this, Uconfirm_orderActivity.class);
                startActivity(intent);
            });
        });
    }

    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler().postDelayed(() -> {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
            onComplete.run();
        }, 1500);
    }

    private void updateCartBar() {
        if (cartItemCount > 0) {
            bottomCartBar.setVisibility(View.VISIBLE);
            cartDetailsText.setText(cartItemCount + " items • $" + String.format("%.2f", cartTotal));
        } else {
            bottomCartBar.setVisibility(View.GONE);
        }
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
        reviews.add(new Review("Sarah M.", "Best pizza on campus! The cheese burst is amazing.", "2 days ago", "4.8", R.drawable.sai));
        reviews.add(new Review("John D.", "Great food and quick service. Love the garlic bread!", "1 week ago", "4.5", R.drawable.nithi));
        return reviews;
    }
}