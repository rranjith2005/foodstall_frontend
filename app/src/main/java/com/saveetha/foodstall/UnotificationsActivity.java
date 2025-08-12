package com.saveetha.foodstall; // Use your package name

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.saveetha.foodstall.adapter.NotificationAdapter;
import com.saveetha.foodstall.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class UnotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unotifications);

        // Back button listener
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Setup RecyclerView
        RecyclerView notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create dummy data
        List<Notification> notificationsList = getDummyNotifications();

        // Setup Adapter
        NotificationAdapter adapter = new NotificationAdapter(notificationsList);
        notificationsRecyclerView.setAdapter(adapter);
    }

    private List<Notification> getDummyNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("New", "New Stall Opened Nearby!", "BBQ Express is now open near you with a 10% opening discount.", "500m away", "10 mins ago"));
        notifications.add(new Notification("Offer", "Limited-Time Offer at Tasty Truck", "Get 20% off on all menu items today! Valid until 8 PM.", "2.1 km away", "1 hour ago"));
        notifications.add(new Notification("Popular", "Street Food Festival This Weekend", "Join us for the biggest street food gathering with 30+ vendors.", "1.2 km away", "Today 4:30 PM"));
        return notifications;
    }
}