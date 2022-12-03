package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.anis.app.eshop.databinding.ActivityElectronicCategoryBinding;

public class ElectronicCategoryActivity extends AppCompatActivity {
    ActivityElectronicCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityElectronicCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        String category = getIntent().getStringExtra("category");
        Intent intent = new Intent(getApplicationContext(), ListProductActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("gender","other");

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.computer.setOnClickListener(v -> {
            intent.putExtra("filter", "computer");
            startActivity(intent);
        });
        binding.smartphone.setOnClickListener(v -> {
            intent.putExtra("filter", "smartphone");
            startActivity(intent);
        });
    }
}