package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.anis.app.eshop.adapter.ProductsAdapter;
import com.anis.app.eshop.databinding.ActivityUserBinding;
import com.anis.app.eshop.model.Products;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private List<Products> listProducts = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        checkUser();

        recyclerView = binding.rvProduct;
        String category = getIntent().getStringExtra("category");
        String gender = getIntent().getStringExtra("gender");
        String filter = getIntent().getStringExtra("filter");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(productsAdapter);

        getData();


        binding.logout.setOnClickListener(v -> new MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setCancelable(true)
                .setMessage("Are you sure?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Logout", (dialog, which) -> {
                    mAuth.signOut();
                    Toast.makeText(UserActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .show());

        binding.clothingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ClothingTargetActivity.class);
            intent.putExtra("category", "clothing");
            startActivity(intent);
        });

        binding.electronicButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ElectronicCategoryActivity.class);
            intent.putExtra("category", "electronic");
            startActivity(intent);
        });

        binding.bookButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListProductActivity.class);
            intent.putExtra("category", "book");
            intent.putExtra("gender", "other");
            intent.putExtra("filter", "other");
            startActivity(intent);
        });

        binding.otherButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListProductActivity.class);
            intent.putExtra("category", "other");
            intent.putExtra("gender", "other");
            intent.putExtra("filter", "other");
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUser();
    }

    private void checkUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void getData() {
        db.collection("product")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            listProducts.add(products);
                        }
                        productsAdapter = new ProductsAdapter(this, listProducts);
                        productsAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(productsAdapter);

                    } else {
                        Log.w("TAG get Data", "Error getting documents.", task.getException());
                    }
                });
    }
}