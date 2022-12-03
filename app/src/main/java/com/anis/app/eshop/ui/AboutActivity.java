package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anis.app.eshop.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}