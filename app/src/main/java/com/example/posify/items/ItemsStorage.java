package com.example.posify.items;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.posify.R;
import com.example.posify.database.DBHelper;
import com.example.posify.modal.Item;

import java.util.ArrayList;

public class ItemsStorage extends AppCompatActivity {

    EditText editTextItem, editTextId , editTextPrice, editTextQuantity;
    DBHelper dbHelper;
    private ArrayList<Item> itemList;
    private ItemAdapter adapter;
    private RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.items_storage);

        dbHelper = new DBHelper(this);

        itemList = new ArrayList<>();
        adapter = new ItemAdapter(itemList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        editTextItem = findViewById(R.id.editTextItem);
        editTextId = findViewById(R.id.editTextId);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnShow = findViewById(R.id.btnShow);

        btnAdd.setOnClickListener(v -> {
            String name = editTextItem.getText().toString().trim();
            String priceStr = editTextPrice.getText().toString().trim();
            String quantityStr = editTextQuantity.getText().toString().trim();

            // Basic validation
            if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            int quantity;

            try {
                price = Double.parseDouble(priceStr);
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbHelper.addItem(name, price, quantity);

            Toast.makeText(this, result ? "Item Added" : "Failed to Add", Toast.LENGTH_SHORT).show();

            // Clear fields after adding
            if (result) {
                editTextItem.setText("");
                editTextPrice.setText("");
                editTextQuantity.setText("");
            }

            loadItems(); // Refresh the list
        });


        btnUpdate.setOnClickListener(v -> {
            String idStr = editTextId.getText().toString().trim();
            String name = editTextItem.getText().toString().trim();
            String priceStr = editTextPrice.getText().toString().trim();
            String quantityStr = editTextQuantity.getText().toString().trim();

            // Validate input
            if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int id = Integer.parseInt(idStr);
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);

                boolean result = dbHelper.updateItem(id, name, price, quantity);

                Toast.makeText(this, result ? "Item Updated" : "Failed to Update", Toast.LENGTH_SHORT).show();
                loadItems(); // Refresh the list

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            }
        });


        btnShow.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getAllItems();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No Items Found", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                builder.append("ID: ").append(cursor.getInt(0)).append("\n");
                builder.append("Name: ").append(cursor.getString(1)).append("\n\n");
            }



            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("All Items");
            dialog.setMessage(builder.toString());
            dialog.setPositiveButton("OK", null);
            dialog.show();
        });
    }

    private void loadItems() {
        itemList.clear(); // Clear old data

        Cursor cursor = dbHelper.getAllItems();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

                Item item = new Item(id, name, price, quantity, timestamp);
                itemList.add(item);

            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}