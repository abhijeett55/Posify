package com.example.posify.orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posify.MainActivity;
import com.example.posify.R;
import com.example.posify.SupabaseClient;
import com.example.posify.items.FoodSectionedAdapter;
import com.example.posify.items.ListItem;
import com.example.posify.modal.CategoryHeader;
import com.example.posify.modal.FoodItem;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderPage extends AppCompatActivity {


    private static final String TAG = "OrderPage";

    private RecyclerView recyclerView;
    private Button checkoutButton;

    private Button burgerButton, pizzaButton, showAllButton;
    private FoodItem[] allItems = new FoodItem[0];


    private final List<ListItem> sectionedItems = new ArrayList<>();
    private FoodSectionedAdapter adapter;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        initViews();
        setupRecyclerView();
        setupCheckoutButton();
        fetchFoodItems();

        burgerButton.setOnClickListener(v -> filterByCategory("Burger"));
        pizzaButton.setOnClickListener(v -> filterByCategory("Pizza"));
        showAllButton.setOnClickListener(v -> showAllItems());

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewFood);
        checkoutButton = findViewById(R.id.buttonCheckout);
        burgerButton = findViewById(R.id.buttonBurger);
        pizzaButton = findViewById(R.id.buttonPizza);
        showAllButton = findViewById(R.id.buttonShowAll);
        setFilterButtonsEnabled(true);

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodSectionedAdapter(this, sectionedItems);
        recyclerView.setAdapter(adapter);
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterByCategory(String category) {
        sectionedItems.clear();

        Map<String, List<FoodItem>> grouped = new LinkedHashMap<>();
        for (FoodItem item : allItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                grouped.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
            }
        }

        for (Map.Entry<String, List<FoodItem>> entry : grouped.entrySet()) {
            sectionedItems.add(new CategoryHeader(entry.getKey()));
            sectionedItems.addAll(entry.getValue());
        }

        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showAllItems() {
        sectionedItems.clear();

        Map<String, List<FoodItem>> grouped = new LinkedHashMap<>();
        for (FoodItem item : allItems) {
            grouped.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
        }

        for (Map.Entry<String, List<FoodItem>> entry : grouped.entrySet()) {
            sectionedItems.add(new CategoryHeader(entry.getKey()));
            sectionedItems.addAll(entry.getValue());
        }

        adapter.notifyDataSetChanged();
    }

    private void setFilterButtonsEnabled(boolean enabled) {
        burgerButton.setEnabled(enabled);
        pizzaButton.setEnabled(enabled);
        showAllButton.setEnabled(enabled);
    }




    private void fetchFoodItems() {
        String url = SupabaseClient.BASE_URL + "food_items";
        Log.d(TAG, "Fetching food items from: " + url);

        Request request = SupabaseClient.addHeaders(new Request.Builder().url(url)).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Failed to fetch food items", e);
                runOnUiThread(() ->
                        Toast.makeText(OrderPage.this, "Failed to fetch items: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    Log.d(TAG, "Response JSON: " + jsonData);

                    FoodItem[] items;
                    try {
                        items = new Gson().fromJson(jsonData, FoodItem[].class);
                    } catch (Exception e) {
                        Log.e(TAG, "JSON parsing error", e);
                        runOnUiThread(() ->
                                Toast.makeText(OrderPage.this, "Error parsing data", Toast.LENGTH_LONG).show()
                        );
                        return;
                    }
                    runOnUiThread(() -> {
                        sectionedItems.clear();
                        if (items != null) {
                            allItems = items; // ✅ Store for filtering

                            Map<String, List<FoodItem>> grouped = new LinkedHashMap<>();
                            for (FoodItem item : items) {
                                grouped.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
                            }

                            for (Map.Entry<String, List<FoodItem>> entry : grouped.entrySet()) {
                                sectionedItems.add(new CategoryHeader(entry.getKey()));
                                sectionedItems.addAll(entry.getValue());
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(OrderPage.this, "Loaded " + sectionedItems.size() + " items", Toast.LENGTH_SHORT).show();
                    });


                } else {
                    String errorBody = response.body().string();
                    Log.e(TAG, "Error response: " + response.code() + " " + response.message() + " - " + errorBody);
                    runOnUiThread(() ->
                            Toast.makeText(OrderPage.this, "Error fetching items: " + response.code(), Toast.LENGTH_LONG).show()
                    );
                }
            }
        });
    }
}
