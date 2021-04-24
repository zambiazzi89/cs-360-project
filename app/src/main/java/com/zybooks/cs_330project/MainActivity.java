package com.zybooks.cs_330project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener, UpdateItemDialog.UpdateItemDialogListener {

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    List<Item> itemList;
    InventoryDB inventoryDB;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventoryDB = new InventoryDB(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        itemList = new ArrayList<>();
        // Gets user's items from DB and store them on a list that will be sent to the Adapter
        itemList = inventoryDB.getItems(sessionManager.getUsername());

        // Inform users when inventory is empty
        if (itemList.size() < 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "The inventory is currently empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
    }

    // Initializes the RecyclerView
    private void initRecyclerView() {
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
    }

    // Opens dialog to add new item information
    public void onAddItemClick(View v) {
        AddItemDialog addItemDialog = new AddItemDialog();
        addItemDialog.show(getSupportFragmentManager(), "Add Item Dialog");
    }

    // Opens dialog with selected item information to be updated
    public void onUpdateItemClick(View v, int itemIndex, long id, String title, String quantity, String units) {
        UpdateItemDialog updateItemDialog = new UpdateItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("itemIndex", itemIndex);
        bundle.putLong("id", id);
        bundle.putString("title", title);
        bundle.putString("quantity", quantity);
        bundle.putString("units", units);
        updateItemDialog.setArguments(bundle);
        updateItemDialog.show(getSupportFragmentManager(), "Update Item Dialog");
    }

    // Adds item to the DB and the Adapter with the dialog's data
    @Override
    public void getItemFromDialog(String addItemTitle, String addItemQuantity, String addItemUnits) {
        Item item = inventoryDB.addItem(sessionManager.getUsername(), addItemTitle, addItemQuantity, addItemUnits);
        itemAdapter.addItem(item);
    }

    // Updates item on the DB and the Adapter with the dialog's data
    @Override
    public void updateItemFromDialog(long id, int itemIndex, String updatedItemTitle, String updatedItemQuantity, String updatedItemUnits) {
        boolean success = inventoryDB.updateItem(id, updatedItemTitle, updatedItemQuantity, updatedItemUnits);
        if (success) {
            itemAdapter.updateItem(itemIndex, updatedItemTitle, updatedItemQuantity, updatedItemUnits);
        }
    }

    // Logs out and clears session manager data
    public void onLogOutClick(View v){
        sessionManager.setLogin(false);
        sessionManager.setUsername("");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    // Goes to SMS page
    public void onSMSClick(View v){
        Intent intent = new Intent(getApplicationContext(), SMSActivity.class);
        startActivity(intent);
    }
}