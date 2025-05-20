package com.example.posify.orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.posify.R;
import com.example.posify.SupabaseClient;
import com.example.posify.items.FoodItemAdapter;
import com.example.posify.items.OrderItemAdapter;
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

    Button checkoutButton;
    Button btnOrder;
    RecyclerView recyclerView;
    FoodItemAdapter adapter;
    List<FoodItem> foodItems = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.order_page);



        recyclerView = findViewById(R.id.recyclerViewFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodItemAdapter(this, foodItems);
        recyclerView.setAdapter(adapter);
        checkoutButton  = findViewById(R.id.buttonCheckout);

        adapter = new OrderItemAdapter(this, foodItems, foodItem -> {
            Order order = new Order(
                    foodItem.getName(),
                    foodItem.getDescription(),
                    foodItem.getPrice(),
                    foodItem.getImageUrl() // update getImageUrl() if needed
            );
            OrderRepository.sendOrder(this, order);
        });
        recyclerView.setAdapter(adapter);

        checkoutButton = findViewById(R.id.buttonCheckout);
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderPage.this, PaymentPage.class);
            startActivity(intent);
        });
        fetchFoodItems();

    }






    private void fetchFoodItems() {
        String url = SupabaseClient.BASE_URL + "food_items";
        Request request = SupabaseClient.addHeaders(new Request.Builder().url(url)).get().build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Supabase", "Failed to fetch food items", e);
                runOnUiThread(() -> Toast.makeText(OrderPage.this, "Failed to fetch", Toast.LENGTH_SHORT).show());
            }

            @SuppressLint("NotifyDataSetChanged")
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    Log.d("Supabase", "Raw JSON: " + jsonData); // ADD THIS LINE

                    FoodItem[] items = new Gson().fromJson(jsonData, FoodItem[].class);
                    runOnUiThread(() -> {
                        foodItems.clear();
                        foodItems.addAll(Arrays.asList(items));
                        adapter.notifyDataSetChanged();

                        Toast.makeText(OrderPage.this, "Items Loaded: " + foodItems.size(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Log.e("Supabase", "Error Response: " + response.code() + " " + response.message());
                }
            }
        });

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderPage.this, PaymentPage.class);
            startActivity(intent);
        });





    }

}