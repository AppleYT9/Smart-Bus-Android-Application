package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Bind views for animations
        View logoCard = findViewById(R.id.splash_logo_card);
        View titleText = findViewById(R.id.splash_title);

        float density = getResources().getDisplayMetrics().density;

        if (logoCard != null) {
            // Initialize: scale down and fade out logo
            logoCard.setAlpha(0f);
            logoCard.setScaleX(0.4f);
            logoCard.setScaleY(0.4f);
            // Animate: zoom in and fade in
            logoCard.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(1200)
                    .start();
        }

        if (titleText != null) {
            // Initialize: translate down and fade out title
            titleText.setAlpha(0f);
            titleText.setTranslationY(40f * density);
            // Animate: slide up and fade in with short delay
            titleText.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(1200)
                    .setStartDelay(300)
                    .start();
        }

        // Automatic redirection to welcome onboarding screen after 2.2 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2200);
    }
}
