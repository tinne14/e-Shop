package com.anis.app.eshop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.anis.app.eshop.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        checkUser();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUser();
    }

    private void checkUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        SharedPreferences mpref = getSharedPreferences("lastIntent", 0);
        String lastIntent = mpref.getString("intent", "");
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (currentUser == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                switch (lastIntent) {
                    case "admin":
                        startActivity(new Intent(this, AdminActivity.class));
                        finish();
                        break;
                    case "staff":
                        startActivity(new Intent(this, StaffActivity.class));
                        finish();
                        break;
                    case "user":
                        startActivity(new Intent(this, UserActivity.class));
                        finish();
                        break;
                    default:
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                        break;
                }
            }
        }, 2000);
    }

}