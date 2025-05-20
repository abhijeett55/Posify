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
import com.example.posify.items.FoodItemAdapter;
import com.example.posify.modal.FoodItem;
import com.example.posify.modal.Order;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderPage extends AppCompatActivity {

    private static final String TAG = "OrderPage";

    private RecyclerView recyclerView;
    private FoodItemAdapter adapter;
    private final List<FoodItem> foodItems = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();



    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        initViews();
        setupRecyclerView();
        setupCheckoutButton();

        fetchFoodItems();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewFood);
        checkoutButton = findViewById(R.id.buttonCheckout);
    }



    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FoodItemAdapter(this, foodItems, foodItem -> {
            // Create Order object and send
            Order order = new Order(
                    foodItem.getName(),
                    foodItem.getDescription(),
                    (float) foodItem.getPrice(),
                    foodItem.getImageUrl()
            );
            OrderRepository.sendOrder(this, order);
            Toast.makeText(this, "Ordered: " + foodItem.getName(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            // Navigate back to MainActivity or checkout screen
            Intent intent = new Intent(OrderPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
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
                        foodItems.clear();
                        if (items != null) {
                            foodItems.addAll(Arrays.asList(items));
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(OrderPage.this, "Loaded " + foodItems.size() + " items", Toast.LENGTH_SHORT).show();
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
