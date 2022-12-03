package com.anis.app.eshop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anis.app.eshop.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private List<String> listData = new ArrayList<>();
    private List<String> listUserType = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.registerButton.setOnClickListener(v -> {
            Intent intentRegister = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intentRegister);
        });

        binding.about.setOnClickListener(v -> {
            Intent intentAbout = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intentAbout);
        });

        binding.loginEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.loginEmail.setError("Email tidak boleh kosong");
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        binding.loginEmail.setError("Format salah");
                    } else {
                        binding.loginEmail.setError(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.loginPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.loginPassword.setError("Password tidak boleh kosong");
                } else {
                    binding.loginPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.loginEmail.getEditText().getText().toString().trim();
            String password = binding.loginPassword.getEditText().getText().toString().trim();
            if (email.isEmpty()) {
                binding.loginEmail.setError("Email tidak boleh kosong");
                Toast.makeText(this, "Username Kosong", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                binding.loginPassword.setError("Password tidak boleh kosong");
                Toast.makeText(this, "Password Kosong", Toast.LENGTH_SHORT).show();
            } else if (binding.loginEmail.getError() != null || binding.loginPassword.getError() != null) {

            } else {
                loginUserAccount(email, password);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void loginUserAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            SharedPreferences mPref = getSharedPreferences("lastIntent", 0);
                            SharedPreferences.Editor editor = mPref.edit();

                            if (task.isSuccessful()) {
                                for (int x = 0; x < listData.size(); x++) {
                                    if (listData.get(x).equals(email) && listUserType.get(x).equals("admin")) {
                                        editor.putString("intent", "admin");
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (listData.get(x).equals(email) && listUserType.get(x).equals("staff")) {
                                        editor.putString("intent", "staff");
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this, StaffActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (listData.get(x).equals(email) && listUserType.get(x).equals("user")) {
                                        editor.putString("intent", "user");
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                            }

                        }
                );
    }

    private void getData() {
        db.collection("user")
                .get()
                .addOnCompleteListener(
                        task -> {
                            listData.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String email = document.getString("email");
                                    String userType = document.getString("userType");
                                    listData.add(email);
                                    listUserType.add(userType);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

}