package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.adapters.StopAdapter;
import com.example.smartbus.models.Stop;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvStops;
    private StopAdapter stopAdapter;
    private List<Stop> stopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.navigation_home);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                return true;
            } else if (itemId == R.id.navigation_routes) {
                startActivity(new Intent(HomeActivity.this, RoutesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_tracking) {
                startActivity(new Intent(HomeActivity.this, TrackingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        // Set up Greeting icons action
        findViewById(R.id.btn_notifications).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
        });
        findViewById(R.id.btn_profile).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        // "Track Bus" Button
        findViewById(R.id.btn_track_bus).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, TrackingActivity.class));
        });

        // Quick Actions clicks
        findViewById(R.id.qa_tracking).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, TrackingActivity.class));
        });
        findViewById(R.id.qa_routes).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, RoutesActivity.class));
        });
        findViewById(R.id.qa_notifications).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
        });
        findViewById(R.id.qa_profile).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        // Set up RecyclerView with mock stops data
        rvStops = findViewById(R.id.rv_stops);
        rvStops.setLayoutManager(new LinearLayoutManager(this));
        
        stopList = new ArrayList<>();
        stopList.add(new Stop("College Main Gate (Start)", "08:00 AM", true));
        stopList.add(new Stop("Hostel Block A", "08:10 AM", true));
        stopList.add(new Stop("Library Junction", "08:20 AM", true));
        stopList.add(new Stop("Main Road Terminal", "08:35 AM", false)); // Current / next stop
        stopList.add(new Stop("Tech Park Circle", "08:50 AM", false));
        stopList.add(new Stop("Central Station (End)", "09:10 AM", false));

        stopAdapter = new StopAdapter(stopList);
        rvStops.setAdapter(stopAdapter);
    }
}
