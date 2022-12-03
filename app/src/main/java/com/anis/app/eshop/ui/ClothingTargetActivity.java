package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.anis.app.eshop.databinding.ActivityClothingTargetBinding;

public class ClothingTargetActivity extends AppCompatActivity {
    ActivityClothingTargetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClothingTargetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String category = getIntent().getStringExtra("category");
        Intent intent = new Intent(ClothingTargetActivity.this, ClothingCategoryActivity.class);
        intent.putExtra("category", category);

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.men.setOnClickListener(v -> {
            intent.putExtra("gender", "male");
            startActivity(intent);
        });

        binding.woman.setOnClickListener(v -> {
            intent.putExtra("gender", "female");
            startActivity(intent);
        });

    }
}