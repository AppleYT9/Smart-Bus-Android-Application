package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.adapters.RouteAdapter;
import com.example.smartbus.models.Route;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutesActivity extends AppCompatActivity {

    private RecyclerView rvRoutes;
    private RouteAdapter routeAdapter;
    private List<Route> routeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        // Set up toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(RoutesActivity.this, HomeActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Set up Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.navigation_routes);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(RoutesActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_routes) {
                return true;
            } else if (itemId == R.id.navigation_tracking) {
                startActivity(new Intent(RoutesActivity.this, TrackingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(RoutesActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        // Initialize mock routes
        routeList = new ArrayList<>();
        routeList.add(new Route(
            "SB-101",
            "College ➔ Central Station",
            new ArrayList<>(Arrays.asList("College", "Main Road", "City Mall", "Central Station")),
            "On Route",
            "Robert Taylor",
            "+1 (555) 019-2834",
            50,
            38,
            "College",
            "Central Station",
            "Main Road",
            "8 minutes"
        ));
        routeList.add(new Route(
            "SB-102",
            "College ➔ Railway Station",
            new ArrayList<>(Arrays.asList("College", "Market Road", "Bus Stand", "Railway Station")),
            "Active",
            "David Miller",
            "+1 (555) 014-9988",
            55,
            42,
            "College",
            "Railway Station",
            "Market Road",
            "12 minutes"
        ));
        routeList.add(new Route(
            "SB-103",
            "College ➔ Tech Park",
            new ArrayList<>(Arrays.asList("College", "North Road", "IT Junction", "Tech Park")),
            "Delayed",
            "Sarah Jenkins",
            "+1 (555) 017-5522",
            45,
            20,
            "College",
            "Tech Park",
            "North Road",
            "25 minutes"
        ));
        routeList.add(new Route(
            "SB-104",
            "College ➔ Airport Terminal",
            new ArrayList<>(Arrays.asList("College", "Highway Junction", "Flyover", "Airport Terminal")),
            "Active",
            "Marcus Brody",
            "+1 (555) 012-7711",
            40,
            15,
            "College",
            "Airport Terminal",
            "Highway Junction",
            "18 minutes"
        ));
        routeList.add(new Route(
            "SB-105",
            "College ➔ City Center",
            new ArrayList<>(Arrays.asList("College", "East Gate", "Sector 4", "City Center")),
            "Active",
            "James Wilson",
            "+1 (555) 013-8822",
            60,
            48,
            "College",
            "City Center",
            "East Gate",
            "10 minutes"
        ));

        // Setup RecyclerView
        rvRoutes = findViewById(R.id.rv_routes);
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
        routeAdapter = new RouteAdapter(routeList);
        rvRoutes.setAdapter(routeAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RoutesActivity.this, HomeActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
