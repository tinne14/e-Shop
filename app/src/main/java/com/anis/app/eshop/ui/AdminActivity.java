package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anis.app.eshop.databinding.ActivityAdminBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.logout.setOnClickListener(v -> new MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setCancelable(true)
                .setMessage("Are you sure?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Logout", (dialog, which) -> {
                    mAuth.signOut();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .show());


        binding.addStaffButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), StaffRegisterActivity.class);
            startActivity(intent);
        });

        binding.addStockButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddStockActivity.class);
            startActivity(intent);
        });

    }
}