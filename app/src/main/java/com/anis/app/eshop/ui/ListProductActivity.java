package com.anis.app.eshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anis.app.eshop.adapter.ProductsAdapter;
import com.anis.app.eshop.databinding.ActivityListProductBinding;
import com.anis.app.eshop.model.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {

    private ActivityListProductBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private List<Products> listProducts = new ArrayList<>();
    private ProductsAdapter productsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        checkUser();
        getSupportActionBar().hide();

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        recyclerView = binding.rvProduct;
        String category = getIntent().getStringExtra("category");
        String gender = getIntent().getStringExtra("gender");
        String filter = getIntent().getStringExtra("filter");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(productsAdapter);

        if (filter!=null) {
            getData(category,gender,filter);
        }

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
    
    private void getData(String category,String gender, String filter) {{
            db.collection("product")
                    .whereEqualTo("category", category)
                    .whereEqualTo("gender",gender)
                    .whereEqualTo("filter", filter)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Products products = documentSnapshot.toObject(Products.class);
                                listProducts.add(products);
                                Log.d("TAG get Succeed", "getData:" + products);
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
}