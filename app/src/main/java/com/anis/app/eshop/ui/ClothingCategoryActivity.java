package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.anis.app.eshop.adapter.ProductsAdapter;
import com.anis.app.eshop.databinding.ActivityClothingCategoryBinding;
import com.anis.app.eshop.model.Products;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ClothingCategoryActivity extends AppCompatActivity {

    ActivityClothingCategoryBinding binding;
    FirebaseFirestore db;
    private RecyclerView rvProduct;
    private List<Products> listCloth = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClothingCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        String category = getIntent().getStringExtra("category");
        String gender = getIntent().getStringExtra("gender");
        Intent intent = new Intent(ClothingCategoryActivity.this, ListProductActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("gender", gender);

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.tShirt.setOnClickListener(v -> {
            intent.putExtra("filter", "tshirt");
            startActivity(intent);
        });

        binding.formal.setOnClickListener(v -> {
            intent.putExtra("filter", "formal");
            startActivity(intent);
        });

        binding.outer.setOnClickListener(v -> {
            intent.putExtra("filter", "outer");
            startActivity(intent);
        });


    }


}