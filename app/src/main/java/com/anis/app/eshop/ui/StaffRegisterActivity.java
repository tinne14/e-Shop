package com.anis.app.eshop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.anis.app.eshop.databinding.ActivityStaffRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffRegisterActivity extends AppCompatActivity {

    private ActivityStaffRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<String> listData = new ArrayList<>();
    private List<String> listUserType = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffRegisterBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.registerStaffUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.registerStaffUsername.setError("Username tidak boleh kosong");
                } else {
                    binding.registerStaffUsername.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.registerStaffEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.registerStaffEmail.setError("Email tidak boleh kosong");
                } else {
                    binding.registerStaffEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.registerStaffPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.registerStaffPassword.setError("Password tidak boleh kosong");
                } else {
                    binding.registerStaffPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.registerStaffRepassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.registerStaffPassword.getEditText().getText().toString().trim();
                if (!s.toString().trim().equals(password)) {
                    binding.registerStaffRepassword.setError("Password tidak cocok");
                } else {
                    binding.registerStaffRepassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.registerStaffButton.setOnClickListener(v -> {
            String username = binding.registerStaffUsername.getEditText().getText().toString().trim();
            String email = binding.registerStaffEmail.getEditText().getText().toString().trim();
            String password = binding.registerStaffPassword.getEditText().getText().toString().trim();
            String repassword = binding.registerStaffRepassword.getEditText().getText().toString().trim();

            if (username.isEmpty()) {
                binding.registerStaffUsername.setError("Username tidak boleh kosong");
            } else if (email.isEmpty()) {
                binding.registerStaffPassword.setError("Password tidak boleh kosong");
            } else if (password.isEmpty()) {
                binding.registerStaffPassword.setError("Password tidak boleh kosong");
            } else if (repassword.isEmpty()) {
                binding.registerStaffRepassword.setError("Masukkan ulang password");
            } else if (binding.registerStaffUsername.getError() != null || binding.registerStaffPassword.getError() != null || binding.registerStaffRepassword.getError() != null) {

            } else {
                staffRegisterAccount(username,email,password);
            }
        });


    }
    private void staffRegisterAccount(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Add staff successful!", Toast.LENGTH_LONG).show();
                        Map<String, Object> data = new HashMap<>();
                        data.put("name", username);
                        data.put("email", email);
                        data.put("password", password);
                        data.put("userType", "staff");

                        addDataStaff(data);

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addDataStaff(Map<String, Object> data){
        db.collection("user")
                .document(mAuth.getUid())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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
