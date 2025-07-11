package com.example.posify.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posify.R;
import com.example.posify.items.FoodItemAdapter;
import com.example.posify.modal.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment  {
    private final List<FoodItem> cartItems = new ArrayList<>();
    private FoodItemAdapter adapter;

    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCart);
        Button checkoutButton = view.findViewById(R.id.buttonCheckoutCart);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FoodItemAdapter(getContext(), cartItems);
        recyclerView.setAdapter(adapter);


        loadCartItems();

        checkoutButton.setOnClickListener(v -> {

        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCartItems() {

        cartItems.clear();

        cartItems.add(new FoodItem(1, "Cheese Burger", "Burger", 5.99, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Pizza-3007395.jpg/960px-Pizza-3007395.jpg" , "Burger" ));
        cartItems.add(new FoodItem(2, "Pepperoni Pizza", "Pizza", 8.99, "Tasty pepperoni pizza" ,"Pizza"));

        adapter.notifyDataSetChanged();
    }
}
