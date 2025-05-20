package com.example.posify.orders;

import android.content.Context;
import android.widget.Toast;

import com.example.posify.SupabaseClient;
import com.example.posify.modal.Order;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderRepository {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();


    public static void sendOrder(Context context, Order order) {
        String url = SupabaseClient.BASE_URL + "orders";
        List<Order> orderList = Collections.singletonList(order); // Send as array for PostgREST
        String bodyJson = gson.toJson(orderList);

        RequestBody body = RequestBody.create(bodyJson, MediaType.parse("application/json"));
        Request.Builder builder = new Request.Builder().url(url).post(body);
        SupabaseClient.addHeaders(builder);

        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (context != null) {
                    android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                    mainHandler.post(() -> Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (context != null) {
                    android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                    mainHandler.post(() -> {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Order placed!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
