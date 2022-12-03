package com.anis.app.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anis.app.eshop.R;
import com.anis.app.eshop.databinding.ItemProductBinding;
import com.anis.app.eshop.model.Products;
import com.anis.app.eshop.ui.DetailProductActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private Context context;
    private List<Products> list;

    public ProductsAdapter(Context context, List<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products products = list.get(position);
        if (products != null) {
            holder.bind(products);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        public MyViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            binding.itemTitle.setText(products.getName());
            binding.itemPrice.setText("Rp " + products.getPrice());
            binding.itemQty.setText(products.getQuantity()+" Pcs");

            Glide.with(binding.getRoot())
                    .load(products.getImage())
                    .placeholder(R.drawable.placeholder_img)
                    .error(R.drawable.ic_error)
                    .centerCrop()
                    .into(binding.itemImage);

//            Picasso.get().load(products.getImages())
//                    .placeholder((R.drawable.ic_launcher_foreground))
//                    .error(R.drawable.ic_launcher_foreground)
//                    .resize(1460, 1460)
//                    .centerCrop()
//                    .into(binding.rowImage);

            int position = getBindingAdapterPosition();
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("image", list.get(position).getImage());
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("price", list.get(position).getPrice());
                intent.putExtra("quantity", list.get(position).getQuantity());
                intent.putExtra("detail", list.get(position).getDetail());

                itemView.getContext().startActivity(intent);
            });

        }

    }
}
