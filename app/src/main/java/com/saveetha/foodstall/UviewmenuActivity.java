package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.LovedByAdapter;
import com.saveetha.foodstall.adapter.MenuItemAdapter;
import com.saveetha.foodstall.adapter.ReviewAdapter;
import com.saveetha.foodstall.model.MenuItem;
import com.saveetha.foodstall.model.OrderItem;
import com.saveetha.foodstall.model.Review;
import com.saveetha.foodstall.model.StallLocation;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UviewmenuActivity extends AppCompatActivity {

    private static final int CONFIRM_ORDER_REQUEST_CODE = 1;
    private ArrayList<OrderItem> cartItems = new ArrayList<>();

    private FrameLayout loadingOverlay;
    private ImageView loadingIcon;

    private Button viewCartButton;
    private LinearLayout bottomCartBar;
    private TextView cartDetailsText;
    private Button addToCartButton;

    private RecyclerView menuRecyclerView;
    private RecyclerView lovedByRecyclerView;
    private RecyclerView reviewsRecyclerView;

    // --- NEW: Variables for distance calculation ---
    private TextView distanceTextView, walkTimeTextView;
    private GeoPoint stallLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uviewmenu);

        // Find existing views
        loadingOverlay = findViewById(R.id.loadingOverlay);
        loadingIcon = findViewById(R.id.loadingIcon);
        viewCartButton = findViewById(R.id.viewCartButton);
        bottomCartBar = findViewById(R.id.bottomCartBar);
        cartDetailsText = findViewById(R.id.cartDetailsText);
        addToCartButton = findViewById(R.id.addToCartButton);
        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        lovedByRecyclerView = findViewById(R.id.loved_by_recycler_view);
        reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);

        // --- NEW: Find views for distance ---
        distanceTextView = findViewById(R.id.distanceTextView);
        walkTimeTextView = findViewById(R.id.walkTimeTextView);

        // --- NEW: Get the stall's location ---
        String stallName = getIntent().getStringExtra("stallName");
        if (stallName != null) {
            ((TextView) findViewById(R.id.shop_name_title)).setText(stallName);
            // Find this stall's location from the StallLocationManager
            for (StallLocation loc : StallLocationManager.getStallLocations()) {
                if (loc.getStallName().equalsIgnoreCase(stallName)) {
                    stallLocation = loc.getGeoPoint();
                    break;
                }
            }
        }

        // Your existing navigation logic (untouched)
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(UviewmenuActivity.this, UhomeActivity.class);
            startActivity(intent);
        });

        ImageView searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(UviewmenuActivity.this, SearchpageActivity.class);
            startActivity(intent);
        });

        setupLovedByRecyclerView();
        setupMenuRecyclerView();
        setupReviewsRecyclerView();

        // --- NEW: Call the method to display the distance ---
        updateDistanceInfo();

        if (getIntent().hasExtra("CART_ITEMS")) {
            cartItems = getIntent().getParcelableArrayListExtra("CART_ITEMS");
        }

        addToCartButton.setOnClickListener(v -> addItemToCart(new MenuItem("Cheese Burst Pizza", 100.00, R.drawable.sd2)));

        viewCartButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            showLoadingOverlay(() -> {
                Intent intent = new Intent(UviewmenuActivity.this, Uconfirm_orderActivity.class);
                intent.putParcelableArrayListExtra("CART_ITEMS", cartItems);
                startActivityForResult(intent, CONFIRM_ORDER_REQUEST_CODE);
            });
        });
        updateCartBar();
    }

    // --- NEW: Method to calculate and display distance ---
    private void updateDistanceInfo() {
        // Get the user's last saved location
        GeoPoint userLocation = LocationManager.getUserLocation(this);

        if (userLocation != null && stallLocation != null) {
            // Calculate distance in meters
            double distance = userLocation.distanceToAsDouble(stallLocation);

            // Estimate walk time (average walking speed is ~80 meters per minute)
            int walkTimeMinutes = (int) Math.round(distance / 80.0);
            if (walkTimeMinutes < 1) {
                walkTimeMinutes = 1; // Show at least 1 min
            }

            // Update the UI
            if (distance < 1000) {
                distanceTextView.setText(String.format(Locale.getDefault(), "%.0f meters away", distance));
            } else {
                distanceTextView.setText(String.format(Locale.getDefault(), "%.2f km away", distance / 1000.0));
            }
            walkTimeTextView.setText(String.format(Locale.getDefault(), "%d min walk", walkTimeMinutes));
        } else {
            // If user or stall location isn't available
            distanceTextView.setText("Location not set");
            walkTimeTextView.setText("");
        }
    }

    // Your other methods are below (untouched)
    private void showLoadingOverlay(Runnable onComplete) {
        loadingOverlay.setVisibility(View.VISIBLE);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
        loadingIcon.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(onComplete, 800);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loadingOverlay != null) {
            loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
        updateCartBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONFIRM_ORDER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("UPDATED_CART_ITEMS")) {
                ArrayList<OrderItem> updatedCart = data.getParcelableArrayListExtra("UPDATED_CART_ITEMS");
                if (updatedCart != null) {
                    cartItems = updatedCart;
                    updateCartBar();
                }
            }
        }
    }

    private void setupLovedByRecyclerView() {
        lovedByRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<MenuItem> lovedByItems = new ArrayList<>();
        lovedByItems.add(new MenuItem("Mandi biriyani", 210.00, R.drawable.sd1));

        LovedByAdapter adapter = new LovedByAdapter(lovedByItems, this::addItemToCart);
        lovedByRecyclerView.setAdapter(adapter);
    }

    private void setupMenuRecyclerView() {
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Veg Biryani", 150.00, R.drawable.sd3));
        menuItems.add(new MenuItem("Paneer Butter Masala", 220.00, R.drawable.pd1));
        menuItems.add(new MenuItem("Gobi Manchurian", 180.00, R.drawable.pd2));
        MenuItemAdapter adapter = new MenuItemAdapter(menuItems, this::addItemToCart);
        menuRecyclerView.setAdapter(adapter);
    }

    private void setupReviewsRecyclerView() {
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review("Sai G.J.", "Best briyani on campus! The piece is amazing.", "2 days ago", "4.8", R.drawable.sai));
        reviewList.add(new Review("Nithi R.", "Quick service and delicious food. Will order again for sure.", "4 days ago", "4.5", R.drawable.nithi));
        ReviewAdapter adapter = new ReviewAdapter(reviewList);
        reviewsRecyclerView.setAdapter(adapter);
    }

    private void addItemToCart(MenuItem item) {
        for (OrderItem orderItem : cartItems) {
            if (orderItem.getName().equals(item.getName())) {
                orderItem.incrementQuantity();
                Toast.makeText(this, "Quantity of " + item.getName() + " increased.", Toast.LENGTH_SHORT).show();
                updateCartBar();
                return;
            }
        }
        cartItems.add(new OrderItem(item.getName(), 1, item.getPrice(), false, null));
        Toast.makeText(this, item.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
        updateCartBar();
    }

    private void updateCartBar() {
        if (cartItems.isEmpty()) {
            bottomCartBar.setVisibility(View.GONE);
        } else {
            bottomCartBar.setVisibility(View.VISIBLE);
            int totalItems = 0;
            double totalPrice = 0;
            for (OrderItem orderItem : cartItems) {
                totalItems += orderItem.getQuantity();
                totalPrice += orderItem.getQuantity() * orderItem.getPrice();
            }
            cartDetailsText.setText(String.format(Locale.getDefault(), "%d items • ₹%.2f", totalItems, totalPrice));
        }
    }
}