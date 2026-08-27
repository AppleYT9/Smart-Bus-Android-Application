package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartbus.adapters.StopAdapter;
import com.example.smartbus.models.Route;
import com.example.smartbus.models.Stop;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.ArrayList;
import java.util.List;

public class BusDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        // Retrieve Intent Data
        Route route = (Route) getIntent().getSerializableExtra("selected_route");
        if (route == null) {
            finish();
            return;
        }

        // Set up toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Bind data to views
        TextView tvBusNumber = findViewById(R.id.tv_detail_bus_number);
        TextView tvRouteName = findViewById(R.id.tv_detail_route_name);
        TextView tvStatus = findViewById(R.id.tv_detail_status);
        TextView tvEta = findViewById(R.id.tv_detail_eta);
        TextView tvStart = findViewById(R.id.tv_detail_start);
        TextView tvDestination = findViewById(R.id.tv_detail_destination);
        TextView tvNextStop = findViewById(R.id.tv_detail_next_stop);
        TextView tvDriverName = findViewById(R.id.tv_detail_driver_name);
        TextView tvDriverContact = findViewById(R.id.tv_detail_driver_contact);
        TextView tvOccupancy = findViewById(R.id.tv_detail_occupancy);
        LinearProgressIndicator progressOccupancy = findViewById(R.id.progress_occupancy);

        tvBusNumber.setText("Bus " + route.getBusNumber());
        tvRouteName.setText(route.getRouteName());
        tvStatus.setText(route.getStatus());
        tvEta.setText(route.getEta());
        tvStart.setText(route.getStartingPoint());
        tvDestination.setText(route.getDestination());
        tvNextStop.setText(route.getNextStop());
        tvDriverName.setText(route.getDriverName());
        tvDriverContact.setText(route.getDriverContact());
        
        tvOccupancy.setText(route.getOccupancy() + " / " + route.getCapacity() + " seats occupied");
        int occupancyPercent = (int) (((double) route.getOccupancy() / route.getCapacity()) * 100);
        progressOccupancy.setProgress(occupancyPercent);

        // Button: Track This Bus
        findViewById(R.id.btn_track_this_bus).setOnClickListener(v -> {
            Intent trackingIntent = new Intent(BusDetailsActivity.this, TrackingActivity.class);
            trackingIntent.putExtra("bus_number", route.getBusNumber());
            startActivity(trackingIntent);
            // Finish this activity so back navigates normally
            finish();
        });

        // Button: View Route (Scroll to Stops Timeline)
        findViewById(R.id.btn_view_route).setOnClickListener(v -> {
            NestedScrollView scrollView = findViewById(R.id.scroll_view);
            TextView timelineTitle = findViewById(R.id.tv_timeline_title);
            if (scrollView != null && timelineTitle != null) {
                scrollView.post(() -> scrollView.smoothScrollTo(0, timelineTitle.getTop()));
            }
        });

        // Setup RecyclerView for route stops
        RecyclerView rvStops = findViewById(R.id.rv_detail_stops);
        rvStops.setLayoutManager(new LinearLayoutManager(this));

        // Generate Stop objects from route raw stops
        List<Stop> stops = new ArrayList<>();
        ArrayList<String> rawStops = route.getStopsList();
        if (rawStops != null) {
            for (int i = 0; i < rawStops.size(); i++) {
                String mockTime;
                if (i == 0) mockTime = "08:00 AM";
                else if (i == 1) mockTime = "08:20 AM";
                else if (i == 2) mockTime = "08:40 AM";
                else if (i == 3) mockTime = "09:00 AM";
                else mockTime = "09:15 AM";

                // Mock first stop as passed for nice visualization
                boolean isPassed = i < 1;
                stops.add(new Stop(rawStops.get(i), mockTime, isPassed));
            }
        }

        StopAdapter stopAdapter = new StopAdapter(stops);
        rvStops.setAdapter(stopAdapter);
    }
}
