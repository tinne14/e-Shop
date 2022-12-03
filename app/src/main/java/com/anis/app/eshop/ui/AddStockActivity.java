package com.anis.app.eshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.anis.app.eshop.databinding.ActivityAddStockBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddStockActivity extends AppCompatActivity {
    ActivityAddStockBinding binding;
    FirebaseFirestore db;
    CollectionReference subjectsRef;
    List<String> productsList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String docId, quantity, name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        subjectsRef = db.collection("product");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productsList);
        binding.spinner.setAdapter(arrayAdapter);

        binding.back.setOnClickListener(v -> onBackPressed());

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        db.collection("product")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String product = document.getString("name");
                        productsList.add(product);
                    }
                    arrayAdapter.notifyDataSetChanged();
                    binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String nama = parent.getSelectedItem().toString();
                            subjectsRef
                                    .whereEqualTo("name", nama).get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                name = documentSnapshot.getData().get("name").toString();
                                                docId = documentSnapshot.getId();
                                                quantity = documentSnapshot.getData().get("quantity").toString();
                                                binding.addStockQty.getEditText().setText(quantity);
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                });


        binding.addStockQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.addStockQty.setError("Quantity tidak boleh kosong");
                } else {
                    binding.addStockQty.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addStockButton.setOnClickListener(v -> {
            String cQuantity = binding.addStockQty.getEditText().getText().toString().trim();
            db.collection("product")
                    .document(docId)
                    .update("quantity", cQuantity)
                    .addOnSuccessListener(unused -> Log.d("TAG", "Quantity successfully updated!\n" + docId + "\n" + cQuantity))
                    .addOnFailureListener(e -> Log.w("TAG", "Error updating quantity", e));
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

}