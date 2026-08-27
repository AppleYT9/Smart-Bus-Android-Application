package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.adapters.NotificationAdapter;
import com.example.smartbus.models.Notification;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Set up toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Set up Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        
        // Clear active highlighting initially since notifications is a detail/sub-page
        bottomNavigation.getMenu().setGroupCheckable(0, true, false);
        for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
            bottomNavigation.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigation.getMenu().setGroupCheckable(0, true, true);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(NotificationsActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_routes) {
                startActivity(new Intent(NotificationsActivity.this, RoutesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_tracking) {
                startActivity(new Intent(NotificationsActivity.this, TrackingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(NotificationsActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        // Initialize Notification Data from Singleton Repository
        notificationList = com.example.smartbus.models.NotificationRepository.getInstance().getNotifications();

        // Setup RecyclerView
        rvNotifications = findViewById(R.id.rv_notifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter(notificationList);
        rvNotifications.setAdapter(notificationAdapter);

        // Mark All as Read Button action
        findViewById(R.id.btn_mark_all_read).setOnClickListener(v -> {
            com.example.smartbus.models.NotificationRepository.getInstance().markAllAsRead();
            notificationAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onBackPressed() {
        // Go back to HomeActivity
        startActivity(new Intent(NotificationsActivity.this, HomeActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
