package com.example.posify.items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.posify.R;
import com.example.posify.modal.FoodItem;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {
    Context context;
    List<FoodItem> items;

    public FoodItemAdapter(Context context, List<FoodItem> items) {
        this.context = context;
        this.items = items;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView imageFood;
        Button orderButton;

        public ViewHolder(View view) {
            super(view);
            imageFood = view.findViewById(R.id.imageFood);
            name = view.findViewById(R.id.textfoodname);
            price = view.findViewById(R.id.textfoodprice);
            orderButton = view.findViewById(R.id.btnOrder);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodItem item = items.get(position);
        holder.name.setText(item.name);
        holder.price.setText("$" + item.price);

        Log.d("ImageURL", item.imageUrl);


        Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageFood);

        holder.orderButton.setOnClickListener(v -> Toast.makeText(context, "Ordered: " + item.name, Toast.LENGTH_SHORT).show());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}
