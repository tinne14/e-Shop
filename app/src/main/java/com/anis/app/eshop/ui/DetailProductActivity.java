package com.anis.app.eshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anis.app.eshop.R;
import com.anis.app.eshop.databinding.ActivityDetailProductBinding;
import com.bumptech.glide.Glide;

public class DetailProductActivity extends AppCompatActivity {

    ActivityDetailProductBinding binding;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.productTitle.setText(getIntent().getStringExtra("name"));
        binding.productQty.setText(getIntent().getStringExtra("quantity")+" Pcs");
        binding.productDetail.setText(getIntent().getStringExtra("detail"));
        binding.productPrice.setText("Rp "+getIntent().getStringExtra("price"));
        img = getIntent().getStringExtra("image");

        Glide.with(this)
                .load(img)
                .fitCenter()
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.ic_error)
                .into(binding.productImage);




    }
}