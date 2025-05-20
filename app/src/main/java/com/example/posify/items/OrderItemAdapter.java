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
import com.example.posify.modal.Order;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder> {

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    private final Context context;
    private final List<Order> orders;
    private final OnOrderClickListener orderClickListener;

    public OrderItemAdapter(Context context, List<Order> orders, OnOrderClickListener orderClickListener) {
        this.context = context;
        this.orders = orders;
        this.orderClickListener = orderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.textfoodname.setText(order.getName());
        holder.textfooddescription.setText(order.getDescription());
        holder.textfoodprice.setText(String.format("₹%.2f", order.getPrice()));


        holder.btnOrder.setOnClickListener(v -> {
            if (orderClickListener != null) {
                orderClickListener.onOrderClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
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