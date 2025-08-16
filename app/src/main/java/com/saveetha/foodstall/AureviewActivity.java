package com.saveetha.foodstall; // Use your app's package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AdminUserReviewAdapter;
import com.saveetha.foodstall.model.AdminUserReview;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AureviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aureview);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView userReviewsRecyclerView = findViewById(R.id.userReviewsRecyclerView);
        userReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AdminUserReview> reviewList = getDummyUserReviewData();
        AdminUserReviewAdapter adapter = new AdminUserReviewAdapter(reviewList);
        userReviewsRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_reviews);
    }

    private List<AdminUserReview> getDummyUserReviewData() {
        List<AdminUserReview> reviews = new ArrayList<>();
        reviews.add(new AdminUserReview("192210687", "Ranjithkumar.R", "Ayilas", "Good taste but have to reduce the cost of the item and increase the quantity fo the product", "2024-01-15", 3));
        reviews.add(new AdminUserReview("192210688", "Nithi.R", "Saravanan mess", "Good service and reasonable price", "2024-01-14", 4));
        reviews.add(new AdminUserReview("192210689", "Deepika.M", "Fresh Bites", "Great food and quick service.", "2024-01-13", 5));
        return reviews;
    }
}
