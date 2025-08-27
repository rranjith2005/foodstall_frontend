package com.saveetha.foodstall;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.saveetha.foodstall.fragment.OwnerReviewsFragment;
import com.saveetha.foodstall.fragment.UserReviewsFragment;

public class AreviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areviews);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Owner Reviews");
            } else {
                tab.setText("User Reviews");
            }
        }).attach();

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.nav_admin_reviews); // Highlight the reviews icon

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            // Using the correct IDs from your XML file
            if (itemId == R.id.nav_admin_home) {
                startActivity(new Intent(getApplicationContext(), AhomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_admin_new_stall) {
                startActivity(new Intent(getApplicationContext(), Anew_stallActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_admin_reviews) {
                return true;
            } else if (itemId == R.id.nav_admin_profile) {
                startActivity(new Intent(getApplicationContext(), AprofileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new OwnerReviewsFragment();
            } else {
                return new UserReviewsFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}