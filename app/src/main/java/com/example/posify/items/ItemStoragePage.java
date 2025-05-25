    package com.example.posify.items;

    import android.app.Activity;
    import android.database.Cursor;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.posify.R;
    import com.example.posify.SupabaseClient;
    import com.example.posify.database.DBHelpers;
    import com.example.posify.modal.Item;
    import com.google.android.material.button.MaterialButton;

    import java.util.ArrayList;

    public class ItemStoragePage extends Activity {
        private EditText editTextId, editTextItem, editTextPrice, editTextQuantity;
        private MaterialButton btnAdd, btnUpdate, btnDelete, btnShow;
        private RecyclerView recyclerView;
        private ItemAdapter itemAdapter;
        private ArrayList<Item> itemList;
        private DBHelpers dbHelpers;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.item_storage_page); // replace with your actual XML filename

            editTextId = findViewById(R.id.editTextId);
            editTextItem = findViewById(R.id.editTextItem);
            editTextPrice = findViewById(R.id.editTextPrice);
            editTextQuantity = findViewById(R.id.editTextQuantity);
            btnAdd = findViewById(R.id.btnAdd);
            btnUpdate = findViewById(R.id.btnUpdate);
            btnDelete = findViewById(R.id.btnDelete);
            btnShow = findViewById(R.id.btnShow);
            recyclerView = findViewById(R.id.recyclerView);

            dbHelpers = new DBHelpers(this);
            itemList = new ArrayList<>();

            itemAdapter = new ItemAdapter(itemList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(itemAdapter);

            // Add
            btnAdd.setOnClickListener(view -> {
                String name = editTextItem.getText().toString();
                String priceStr = editTextPrice.getText().toString();
                String quantityStr = editTextQuantity.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(quantityStr)) {
                    Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                String timestamp = String.valueOf(System.currentTimeMillis()); // assign timestamp here

                // Assuming your addItem method signature is: addItem(String name, double price, int quantity, String timestamp)
                boolean inserted = dbHelpers.addItem(name, price, quantity, timestamp);

                if (inserted) {
                    SupabaseClient.insertItemToSupabase(name, price, quantity, timestamp);
                    Toast.makeText(this, "Item added!", Toast.LENGTH_SHORT).show();
                    clearFields();
                    loadItems();
                } else {
                    Toast.makeText(this, "Add failed!", Toast.LENGTH_SHORT).show();
                }
            });


            // Update
            btnUpdate.setOnClickListener(view -> {
                String idStr = editTextId.getText().toString();
                String name = editTextItem.getText().toString();
                String priceStr = editTextPrice.getText().toString();
                String quantityStr = editTextQuantity.getText().toString();

                if (TextUtils.isEmpty(idStr) || TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(quantityStr)) {
                    Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idStr);
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);

                boolean updated = dbHelpers.updateItem(id, name, price, quantity);
                if (updated) {
                    Toast.makeText(this, "Item updated!", Toast.LENGTH_SHORT).show();
                    clearFields();
                    loadItems();
                } else {
                    Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            });

            // Delete
            btnDelete.setOnClickListener(view -> {
                String idStr = editTextId.getText().toString();
                if (TextUtils.isEmpty(idStr)) {
                    Toast.makeText(this, "Enter ID to delete!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(idStr);
                boolean deleted = dbHelpers.deleteItem(id);
                if (deleted) {
                    Toast.makeText(this, "Item deleted!", Toast.LENGTH_SHORT).show();
                    clearFields();
                    loadItems();
                } else {
                    Toast.makeText(this, "Delete failed!", Toast.LENGTH_SHORT).show();
                }
            });

            // Show All
            btnShow.setOnClickListener(view -> loadItems());

            // Item click to populate fields
            itemAdapter.setOnItemClickListener(item -> {
                editTextId.setText(String.valueOf(item.getId()));
                editTextItem.setText(item.getName());
                editTextPrice.setText(String.valueOf(item.getPrice()));
                editTextQuantity.setText(String.valueOf(item.getQuantity()));
            });

            // Load items initially
            loadItems();
        }

        private void loadItems() {
            itemList.clear();
            Cursor cursor = dbHelpers.getAllItems();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                    itemList.add(new Item(id, name, price, quantity, timestamp));
                } while (cursor.moveToNext());
                cursor.close();
            }
            itemAdapter.notifyDataSetChanged();
        }

        private void clearFields() {
            editTextId.setText("");
            editTextItem.setText("");
            editTextPrice.setText("");
            editTextQuantity.setText("");
        }




    }
