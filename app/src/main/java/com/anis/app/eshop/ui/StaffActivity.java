package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.anis.app.eshop.databinding.ActivityStaffBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class StaffActivity extends AppCompatActivity {

    private ActivityStaffBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffBinding.inflate(getLayoutInflater());
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
                    startActivity(intent);
                    finish();
                })
                .show());

    }
}