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

import com.bumptech.glide.Glide;
import com.example.posify.R;
import com.example.posify.SupabaseClient;
import com.example.posify.modal.CategoryHeader;
import com.example.posify.modal.FoodItem;
import com.example.posify.modal.Order;

import java.util.ArrayList;
import java.util.List;

public class FoodSectionedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<ListItem> itemList;
    private final List<Order> selectedOrders = new ArrayList<>();

    public FoodSectionedAdapter(Context context, List<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public List<Order> getSelectedOrders() {
        return selectedOrders;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_category_haed, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
            return new FoodViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem listItem = itemList.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind((CategoryHeader) listItem);
        } else if (holder instanceof FoodViewHolder) {
            ((FoodViewHolder) holder).bind((FoodItem) listItem);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textCategory;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.textCategoryHeader);
        }

        void bind(CategoryHeader header) {
            textCategory.setText(header.getCategoryName());
        }
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price;
        ImageView image;
        Button btnAdd;

        public FoodViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textfoodname);
            description = itemView.findViewById(R.id.textfooddescription);
            price = itemView.findViewById(R.id.textfoodprice);
            image = itemView.findViewById(R.id.imageFood);
            btnAdd = itemView.findViewById(R.id.btnOrder); // make sure this ID matches your layout
        }

        void bind(FoodItem item) {
            name.setText(item.getName());
            description.setText(item.getDescription());
            price.setText("₹" + item.getPrice());
            String imageUrl = item.getImageUrl();

            String finalUrl = (imageUrl != null && imageUrl.startsWith("http")) ? imageUrl
                    : (imageUrl != null ? SupabaseClient.STORAGE_URL + imageUrl : "");

            Glide.with(itemView.getContext())
                    .load(finalUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(image);






//
//            Glide.with(itemView.getContext())
//                    .load(imageUrl.startsWith("http") ? imageUrl : SupabaseClient.STORAGE_URL + imageUrl)
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.placeholder_image)
//                    .into(image);


            Order order = new Order(item.getName(), item.getDescription(), item.getPrice());

            // Update button state
            btnAdd.setText(selectedOrders.contains(order) ? "Added" : "Add");

            btnAdd.setOnClickListener(v -> {
                Order newOrder = new Order(item.getName(), item.getDescription(), (float) item.getPrice(), item.getImageUrl());
                if (selectedOrders.contains(newOrder)) {
                    selectedOrders.remove(newOrder);
                    btnAdd.setText("Add");
                } else {
                    selectedOrders.add(newOrder);
                    btnAdd.setText("Added");
                }
            });


        }
    }
}
