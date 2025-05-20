package com.example.posify.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posify.R;
import com.example.posify.modal.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final ArrayList<Item> itemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ItemAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textPrice, textQuantity, textTimestamp;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textItemName);
            textPrice = itemView.findViewById(R.id.textItemPrice);
            textQuantity = itemView.findViewById(R.id.textItemQuantity);
            textTimestamp = itemView.findViewById(R.id.textItemTimestamp);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item current = itemList.get(position);
        holder.textName.setText("Name: " + current.getName());
        holder.textPrice.setText("Price: ₹" + current.getPrice());
        holder.textQuantity.setText("Qty: " + current.getQuantity());
        holder.textTimestamp.setText("Added: " + current.getTimestamp());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}