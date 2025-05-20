package com.example.posify.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posify.R;
import com.example.posify.modal.FoodItem;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder> {

    public interface OnOrderClickListener {
        void onOrderClick(FoodItem foodItem);
    }

    private final Context context;
    private final List<FoodItem> foodItems;
    private final OnOrderClickListener orderClickListener;

    public OrderItemAdapter(Context context, List<FoodItem> foodItems, OnOrderClickListener orderClickListener) {
        this.context = context;
        this.foodItems = foodItems;
        this.orderClickListener = orderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // FIX: Use the correct item layout!
        View view = LayoutInflater.from(context).inflate(R.layout., parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);

        holder.textfoodname.setText(foodItem.getName());
        holder.textfooddescription.setText(foodItem.getDescription());
        holder.textfoodprice.setText(String.valueOf(foodItem.getPrice()));

        // Optionally set image if you have a URL or resource

        holder.btnOrder.setOnClickListener(v -> {
            if (orderClickListener != null) {
                orderClickListener.onOrderClick(foodItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFood;
        TextView textfoodname, textfooddescription, textfoodprice;
        Button btnOrder;

        OrderViewHolder(View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.imageFood);
            textfoodname = itemView.findViewById(R.id.textfoodname);
            textfooddescription = itemView.findViewById(R.id.textfooddescription);
            textfoodprice = itemView.findViewById(R.id.textfoodprice);
            btnOrder = itemView.findViewById(R.id.btnOrder);
        }
    }
}