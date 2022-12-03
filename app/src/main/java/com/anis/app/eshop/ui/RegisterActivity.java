package com.anis.app.eshop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.anis.app.eshop.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getSupportActionBar().hide();

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.registerName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.registerName.setError("Name tidak boleh kosong");
                } else {
                    binding.registerName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.registerEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.registerEmail.setError("Phone Number tidak boleh kosong");
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        binding.registerEmail.setError("Format salah");
                    } else {
                        binding.registerEmail.setError(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.registerPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.registerPassword.setError("Password tidak boleh kosong");
                } else {
                    binding.registerPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        binding.registerButton.setOnClickListener(v -> {
            String name = binding.registerName.getEditText().getText().toString().trim();
            String email = binding.registerEmail.getEditText().getText().toString().trim();
            String password = binding.registerPassword.getEditText().getText().toString().trim();

            if (name.isEmpty()) {
                binding.registerName.setError("Name tidak boleh kosong");
            } else if (email.isEmpty()) {
                binding.registerEmail.setError("Phone Number tidak boleh kosong");
            } else if (password.isEmpty()) {
                binding.registerPassword.setError("Password tidak boleh kosong");
            } else if (binding.registerName.getError() != null || binding.registerEmail.getError() != null || binding.registerPassword.getError() != null) {

            } else {
                registerUser(name, email, password);
            }
        });

    }

    private void registerUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("email", email);
                data.put("password", password);
                data.put("userType", "user");
                data.put("uid", mAuth.getCurrentUser().getUid());

                addDataUser(data);

                Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addDataUser(Map<String, Object> data) {
        db.collection("user")
                .document(mAuth.getCurrentUser().getUid())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Data user added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}