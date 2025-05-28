package com.example.posify;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupabaseClient {

//    public static final String BASE_URL = "https://sfwkwfhdcxbtwqbulmzv.supabase.co";

    public static final String BASE_URL = "https://sfwkwfhdcxbtwqbulmzv.supabase.co/rest/v1/";

    public static final String TABLE_NAME = "items";
    public static final String STORAGE_URL = "https://sfwkwfhdcxbtwqbulmzv.supabase.co/storage/v1/object/public/images/";
    public static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNmd2t3ZmhkY3hidHdxYnVsbXp2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDc1NDc3MjcsImV4cCI6MjA2MzEyMzcyN30.DaorlyXkgMhHx1qihtpCAXoWs5dntxWRDL7vCoQ8U0s";

    private static final OkHttpClient client = new OkHttpClient();

    public static void insertItemToSupabase(String name, double price, int quantity, String timestamp) {
        String url = BASE_URL + "/rest/v1/" + TABLE_NAME;

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("price", price);
            json.put("quantity", quantity);
            json.put("timestamp", timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=minimal") // Optional: skip returning row
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Network error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.out.println("Insert failed: " + response.body().string());
                }
                response.close();
            }
        });
    }

    public static void fetchDashboardData(Callback callback) {
        String url = BASE_URL + "orders?select=amount,customer_id";

        Request request = addHeaders(new Request.Builder()
                .url(url)
                .get())
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static Request.Builder addHeaders(Request.Builder builder) {
        return builder
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json");
    }
}
