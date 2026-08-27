package com.example.smartbus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.et_login_email);
        EditText etPassword = findViewById(R.id.et_login_password);
        Button btnLogin = findViewById(R.id.btn_login);

        // Pre-fill mock data for quick examiner testing
        if (etEmail != null) etEmail.setText("student@example.com");
        if (etPassword != null) etPassword.setText("student123");

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    etEmail.setError("Email address is required");
                    etEmail.requestFocus();
                    return;
                }
                if (!email.contains("@")) {
                    etEmail.setError("Please enter a valid email address");
                    etEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                    return;
                }

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            });
        }
    }
}
