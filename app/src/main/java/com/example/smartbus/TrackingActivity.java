package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrackingActivity extends AppCompatActivity {

    private ImageView ivTrackedBus;
    private TextView tvTopBusNumber, tvTopRoute, tvTopStatus, tvTopEta;
    private TextView tvBottomLocation, tvBottomNext, tvBottomEta;
    private Button btnSimulate;
    private AppCompatSpinner spinnerRoutes;
    
    private View dotStop0, dotStop1, dotStop2, dotStop3;

    private int currentStep = 0;
    private boolean isSimulating = false;
    private Handler simulationHandler;
    private Runnable simulationRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Retrieve Intent Extras
        String busNumberExtra = getIntent().getStringExtra("bus_number");
        if (busNumberExtra == null || busNumberExtra.isEmpty()) {
            busNumberExtra = "SB-101"; // Default mockup
        }

        // Set up toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(TrackingActivity.this, HomeActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Set up Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.navigation_tracking);
        bottomNavigation.setOnItemSelectedListener(item -> {
            stopSimulation();
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(TrackingActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_routes) {
                startActivity(new Intent(TrackingActivity.this, RoutesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_tracking) {
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(TrackingActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        // Bind Views
        ivTrackedBus = findViewById(R.id.iv_tracked_bus);
        tvTopBusNumber = findViewById(R.id.tv_top_bus_number);
        tvTopRoute = findViewById(R.id.tv_top_route);
        tvTopStatus = findViewById(R.id.tv_top_status);
        tvTopEta = findViewById(R.id.tv_top_eta);
        tvBottomLocation = findViewById(R.id.tv_bottom_location);
        tvBottomNext = findViewById(R.id.tv_bottom_next);
        tvBottomEta = findViewById(R.id.tv_bottom_eta);
        btnSimulate = findViewById(R.id.btn_simulate);
        spinnerRoutes = findViewById(R.id.spinner_routes);

        dotStop0 = findViewById(R.id.dot_stop0);
        dotStop1 = findViewById(R.id.dot_stop1);
        dotStop2 = findViewById(R.id.dot_stop2);
        dotStop3 = findViewById(R.id.dot_stop3);

        // Populate Route Switcher Spinner
        String[] routeOptions = {
            "SB-101 (Central Station)",
            "SB-102 (Railway Station)",
            "SB-103 (Tech Park)"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, routeOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoutes.setAdapter(spinnerAdapter);

        // Pre-select route based on passed Intent extras
        int preSelectedIndex = 0;
        if ("SB-102".equalsIgnoreCase(busNumberExtra)) {
            preSelectedIndex = 1;
        } else if ("SB-103".equalsIgnoreCase(busNumberExtra)) {
            preSelectedIndex = 2;
        }
        spinnerRoutes.setSelection(preSelectedIndex);

        // Switcher Selection Listener
        spinnerRoutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopSimulation();
                currentStep = 0;
                
                String selectedBus = "SB-101";
                String selectedRouteText = "College ➔ Central Station";
                
                if (position == 1) {
                    selectedBus = "SB-102";
                    selectedRouteText = "College ➔ Railway Station";
                } else if (position == 2) {
                    selectedBus = "SB-103";
                    selectedRouteText = "College ➔ Tech Park";
                }
                
                tvTopBusNumber.setText("Bus " + selectedBus);
                tvTopRoute.setText(selectedRouteText);
                
                // Reset timeline positions
                updateSimulationUI(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Initialize simulator Handler
        simulationHandler = new Handler(Looper.getMainLooper());
        simulationRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentStep < 3) {
                    currentStep++;
                    updateSimulationUI(true);
                    simulationHandler.postDelayed(this, 3500);
                } else {
                    stopSimulation();
                }
            }
        };

        // Simulate Button Listener
        btnSimulate.setOnClickListener(v -> {
            if (isSimulating) {
                stopSimulation();
            } else {
                startSimulation();
            }
        });
    }

    private void startSimulation() {
        isSimulating = true;
        btnSimulate.setText("Stop Simulation");
        btnSimulate.setBackgroundTintList(getResources().getColorStateList(R.color.error));
        
        if (currentStep >= 3) {
            currentStep = 0;
            updateSimulationUI(false);
        }
        
        // Trigger step progression
        simulationHandler.postDelayed(simulationRunnable, 3500);
    }

    private void stopSimulation() {
        isSimulating = false;
        btnSimulate.setText("Simulate Bus Movement");
        btnSimulate.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
        if (simulationHandler != null) {
            simulationHandler.removeCallbacks(simulationRunnable);
        }
    }

    private void updateSimulationUI(boolean animate) {
        float density = getResources().getDisplayMetrics().density;
        float translationY = 0f;

        // Custom stop label strings depending on active bus
        String busNumber = tvTopBusNumber.getText().toString();
        String stop0 = "College";
        String stop1 = "Main Road";
        String stop2 = "City Mall";
        String stop3 = "Central Station";

        if (busNumber.contains("SB-102")) {
            stop1 = "Market Road";
            stop2 = "Bus Stand";
            stop3 = "Railway Station";
        } else if (busNumber.contains("SB-103")) {
            stop1 = "North Road";
            stop2 = "IT Junction";
            stop3 = "Tech Park";
        }

        switch (currentStep) {
            case 0:
                translationY = 0f;
                tvTopStatus.setText("On Route");
                tvTopStatus.setTextColor(getResources().getColor(R.color.success));
                tvTopEta.setText("ETA: 8 mins");
                
                tvBottomLocation.setText(stop0);
                tvBottomNext.setText(stop1);
                tvBottomEta.setText("8 mins");

                dotStop0.setBackgroundResource(R.drawable.dot_passed);
                dotStop1.setBackgroundResource(R.drawable.dot_upcoming);
                dotStop2.setBackgroundResource(R.drawable.dot_upcoming);
                dotStop3.setBackgroundResource(R.drawable.dot_upcoming);
                break;

            case 1:
                translationY = 80f * density;
                tvTopStatus.setText("On Route");
                tvTopStatus.setTextColor(getResources().getColor(R.color.success));
                tvTopEta.setText("ETA: 5 mins");

                tvBottomLocation.setText(stop1);
                tvBottomNext.setText(stop2);
                tvBottomEta.setText("5 mins");

                dotStop0.setBackgroundResource(R.drawable.dot_passed);
                dotStop1.setBackgroundResource(R.drawable.dot_passed);
                dotStop2.setBackgroundResource(R.drawable.dot_upcoming);
                dotStop3.setBackgroundResource(R.drawable.dot_upcoming);
                break;

            case 2:
                translationY = 160f * density;
                tvTopStatus.setText("On Route");
                tvTopStatus.setTextColor(getResources().getColor(R.color.success));
                tvTopEta.setText("ETA: 3 mins");

                tvBottomLocation.setText(stop2);
                tvBottomNext.setText(stop3);
                tvBottomEta.setText("3 mins");

                dotStop0.setBackgroundResource(R.drawable.dot_passed);
                dotStop1.setBackgroundResource(R.drawable.dot_passed);
                dotStop2.setBackgroundResource(R.drawable.dot_passed);
                dotStop3.setBackgroundResource(R.drawable.dot_upcoming);
                break;

            case 3:
                translationY = 240f * density;
                tvTopStatus.setText("Completed");
                tvTopStatus.setTextColor(getResources().getColor(R.color.primary_light));
                tvTopEta.setText("Arrived");

                tvBottomLocation.setText(stop3);
                tvBottomNext.setText("Destination Reached");
                tvBottomEta.setText("--");

                dotStop0.setBackgroundResource(R.drawable.dot_passed);
                dotStop1.setBackgroundResource(R.drawable.dot_passed);
                dotStop2.setBackgroundResource(R.drawable.dot_passed);
                dotStop3.setBackgroundResource(R.drawable.dot_passed);

                // Add dynamic notification alert when trip is completed
                if (animate) {
                    String cleanBus = busNumber.replace("Bus ", "").trim();
                    com.example.smartbus.models.Notification newAlert = new com.example.smartbus.models.Notification(
                        new java.util.Random().nextInt(1000) + 10,
                        busNumber,
                        busNumber + " has reached the end station " + stop3 + ".",
                        "Just now",
                        false,
                        "info"
                    );
                    com.example.smartbus.models.NotificationRepository.getInstance().addNotification(newAlert);
                    
                    android.widget.Toast.makeText(TrackingActivity.this, 
                        busNumber + " has arrived at " + stop3 + "! Notification created.", 
                        android.widget.Toast.LENGTH_LONG).show();

                    showSystemNotification(cleanBus, stop3);
                }
                break;
        }

        // Animate/Slide the bus icon to the new position
        if (animate) {
            ivTrackedBus.animate()
                    .translationY(translationY)
                    .setDuration(1200)
                    .start();
        } else {
            ivTrackedBus.setTranslationY(translationY);
        }
    }

    private void showSystemNotification(String busNumber, String stopName) {
        String channelId = "bus_tracking_channel";
        String channelName = "Bus Tracking Alerts";
        android.app.NotificationManager notificationManager = 
                (android.app.NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(
                    channelId, channelName, android.app.NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        androidx.core.app.NotificationCompat.Builder builder = 
                new androidx.core.app.NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bus " + busNumber + " Arrived")
                .setContentText("Destination stop " + stopName + " has been reached.")
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(new java.util.Random().nextInt(1000), builder.build());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSimulation();
    }
}
