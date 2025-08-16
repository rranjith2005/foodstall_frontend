package com.saveetha.foodstall; // Use your app's package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.adapter.AdminReviewAdapter;
import com.saveetha.foodstall.model.AdminReview;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AoreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aoreview);

        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        RecyclerView reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AdminReview> reviewList = getDummyReviewData();
        AdminReviewAdapter adapter = new AdminReviewAdapter(reviewList);
        reviewsRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_reviews);
    }

    private List<AdminReview> getDummyReviewData() {
        List<AdminReview> reviews = new ArrayList<>();
        reviews.add(new AdminReview("Golden Wok", "John Smith", "Excellent service and cleanliness maintained throughout the semester. Students consistently provide positive feedback about our food quality and service.", "2024-01-15", 5));
        reviews.add(new AdminReview("Coffee Haven", "Sarah Johnson", "Great improvement in customer service this semester. We've implemented new queue management system which has helped reduce waiting times significantly.", "2024-01-14", 4));
        reviews.add(new AdminReview("Fresh Bites", "Mike Wilson", "Proud to maintain high standards of food safety and quality. Our new menu items have been well received by students.", "2024-01-13", 4));
        return reviews;
    }
}
