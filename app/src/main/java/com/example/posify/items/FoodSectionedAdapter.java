package com.example.posify.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.posify.R;
import com.example.posify.modal.CategoryHeader;
import com.example.posify.modal.FoodItem;

import java.util.List;

public class FoodSectionedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final List<ListItem> itemList;

    public FoodSectionedAdapter(Context context, List<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
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
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind((CategoryHeader) itemList.get(position));
        } else {
            ((FoodViewHolder) holder).bind((FoodItem) itemList.get(position));
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

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price;
        ImageView image;
        CheckBox checkbox;

        public FoodViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textfoodname);
            description = itemView.findViewById(R.id.textfooddescription);
            price = itemView.findViewById(R.id.textfoodprice);
            image = itemView.findViewById(R.id.imageFood);
        }

        void bind(FoodItem item) {
            name.setText(item.getName());
            description.setText(item.getDescription());
            price.setText("₹" + item.getPrice());

            Glide.with(itemView.getContext()).load(item.getImageUrl()).into(image);
        }
    }
}
