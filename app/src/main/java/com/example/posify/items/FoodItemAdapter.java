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
import com.example.posify.SupabaseClient;
import com.example.posify.modal.FoodItem;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {
    Context context;
    List<FoodItem> items;

    public interface OnOrderClickListener {
        void onOrderClick(FoodItem foodItem);
    }

    public FoodItemAdapter(Context context, List<FoodItem> items) {
        this(context, items, null);
    }


    private OnOrderClickListener orderClickListener;
    public FoodItemAdapter(Context context, List<FoodItem> items, OnOrderClickListener listener) {
        this.context = context;
        this.items = items;
        this.orderClickListener = listener;
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
        holder.name.setText(item.getName());
        holder.price.setText("$" + item.getPrice());

        String imageUrl = item.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            holder.imageFood.setImageResource(R.drawable.placeholder_image);
        } else {
            Glide.with(context)
                    .load(imageUrl.startsWith("http") ? imageUrl : SupabaseClient.BASE_URL + imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.imageFood);
        }

        holder.orderButton.setOnClickListener(v -> {
            if (orderClickListener != null) {
                orderClickListener.onOrderClick(item);
            }
        });


    }



    @Override
    public int getItemCount() {
        return items.size();
    }

}
