package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvId, tvEmail, tvBus, tvStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bind Views
        tvName = findViewById(R.id.tv_profile_name);
        tvId = findViewById(R.id.tv_profile_id);
        tvEmail = findViewById(R.id.tv_profile_email);
        tvBus = findViewById(R.id.tv_profile_bus);
        tvStop = findViewById(R.id.tv_profile_stop);

        // Set up Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.navigation_profile);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_routes) {
                startActivity(new Intent(ProfileActivity.this, RoutesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_tracking) {
                startActivity(new Intent(ProfileActivity.this, TrackingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                return true;
            }
            return false;
        });

        // Edit Profile Dialog implementation
        Button btnEditProfile = findViewById(R.id.btn_edit_profile);
        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> showEditProfileDialog());
        }

        // Logout Button - Clear stack navigation
        Button btnLogout = findViewById(R.id.btn_logout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        // Settings Toggles - Push notifications
        MaterialSwitch switchNotif = findViewById(R.id.switch_notifications);
        if (switchNotif != null) {
            switchNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String message = isChecked ? "Push notifications enabled" : "Push notifications muted";
                Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            });
        }

        // Real Night-Mode Switch
        MaterialSwitch switchDarkMode = findViewById(R.id.switch_dark_mode);
        if (switchDarkMode != null) {
            // Set checked state before listener mapping to prevent recreation loops
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            switchDarkMode.setChecked(nightMode == AppCompatDelegate.MODE_NIGHT_YES);

            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                AppCompatDelegate.setDefaultNightMode(isChecked ? 
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            });
        }

        // About SmartBus dialog
        findViewById(R.id.row_about).setOnClickListener(v -> {
            new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("About SmartBus")
                .setMessage("SmartBus – Bus Tracking and Route Management System v1.0.0\n\nDesigned for students to monitor real-time bus locations and check scheduled routes.\n\nAcademic Project.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
        });
    }

    private void showEditProfileDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);

        EditText etName = dialogView.findViewById(R.id.et_edit_name);
        EditText etId = dialogView.findViewById(R.id.et_edit_id);
        EditText etEmail = dialogView.findViewById(R.id.et_edit_email);
        EditText etBus = dialogView.findViewById(R.id.et_edit_bus);
        EditText etStop = dialogView.findViewById(R.id.et_edit_stop);

        // Pre-fill dialog with current layout text
        if (tvName != null) etName.setText(tvName.getText().toString());
        if (tvId != null) {
            String rawId = tvId.getText().toString().replace("Student ID: ", "");
            etId.setText(rawId);
        }
        if (tvEmail != null) etEmail.setText(tvEmail.getText().toString());
        if (tvBus != null) etBus.setText(tvBus.getText().toString());
        if (tvStop != null) etStop.setText(tvStop.getText().toString());

        new AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Save", (dialog, which) -> {
                String inputName = etName.getText().toString().trim();
                String inputId = etId.getText().toString().trim();
                String inputEmail = etEmail.getText().toString().trim();
                String inputBus = etBus.getText().toString().trim();
                String inputStop = etStop.getText().toString().trim();

                if (inputName.isEmpty() || inputId.isEmpty() || inputEmail.isEmpty() || inputBus.isEmpty() || inputStop.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "All fields are required. Changes not saved.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update text views dynamically
                tvName.setText(inputName);
                tvId.setText("Student ID: " + inputId);
                tvEmail.setText(inputEmail);
                tvBus.setText(inputBus);
                tvStop.setText(inputStop);

                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
