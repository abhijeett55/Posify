package com.example.posify.orders;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.posify.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PaymentPage extends AppCompatActivity {

    TextView orderDetails;
//    String orderId = getIntent().getStringExtra("orders_id");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);

        orderDetails = findViewById(R.id.order_Details);

        Intent intent = new Intent(PaymentPage.this, OrderPage.class);
        intent.putExtra("orders_id", "orders");
        startActivity(intent);

        String orderId = getIntent().getStringExtra("orders_id");
        if (orderId != null) {
            fetchOrderData(orderId);
        } else {
            orderDetails.setText("No order ID provided.");
        }
    }

    private void fetchOrderData(String orderId) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://sfwkwfhdcxbtwqbulmzv.supabase.co/rest/v1/orders?id=eq." + orderId;

        Request request = new Request.Builder()
                .url("https://sfwkwfhdcxbtwqbulmzv.supabase.co/rest/v1/orders")
                .get()
                .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> orderDetails.setText("Network error."));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        if (jsonArray.length() > 0) {
                            JSONObject order = jsonArray.getJSONObject(0);
                            int itemId = order.getInt("item_id");
                            String customerName = order.getString("customer_name");
                            int quantity = order.getInt("quantity");

                            runOnUiThread(() -> orderDetails.setText(
                                    "Customer: " + customerName + "\n" +
                                            "Item ID: " + itemId + "\n" +
                                            "Quantity: " + quantity
                            ));
                        } else {
                            runOnUiThread(() -> orderDetails.setText("No order found."));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> orderDetails.setText("Error parsing data."));
                    }
                } else {
                    runOnUiThread(() -> orderDetails.setText("Error fetching order."));
                }
            }
        });

    }

}