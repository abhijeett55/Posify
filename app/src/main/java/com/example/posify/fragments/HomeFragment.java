package com.example.posify.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.posify.R;
import com.example.posify.SupabaseClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;


public class HomeFragment extends Fragment {


        private TextView totalBilledTextView, totalOrdersTextView, totalCustomersTextView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            totalBilledTextView = view.findViewById(R.id.totalBilledTextView);
            totalOrdersTextView = view.findViewById(R.id.totalOrdersTextView);
            totalCustomersTextView = view.findViewById(R.id.totalCustomersTextView);

            loadDashboardData();

            return view;
        }

        private void loadDashboardData() {
            SupabaseClient.fetchDashboardData(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Failed to load dashboard", Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        try {
                            JSONArray jsonArray = new JSONArray(responseBody);

                            double totalBilled = 0;
                            Set<String> customerSet = new HashSet<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject order = jsonArray.getJSONObject(i);

                                if (order.has("amount")) {
                                    totalBilled += order.getDouble("amount");
                                }

                                if (order.has("customer_id")) {
                                    customerSet.add(order.getString("customer_id"));
                                }
                            }

                            int totalOrders = jsonArray.length();
                            int totalCustomers = customerSet.size();    

                            double finalTotalBilled = totalBilled;
                            requireActivity().runOnUiThread(() -> {
                                totalBilledTextView.setText("₹" + finalTotalBilled);
                                totalOrdersTextView.setText(String.valueOf(totalOrders));
                                totalCustomersTextView.setText(String.valueOf(totalCustomers));
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("Supabase", "Failed: " + response.code());
                    }
                    response.close();
                }
            });
        }

}
