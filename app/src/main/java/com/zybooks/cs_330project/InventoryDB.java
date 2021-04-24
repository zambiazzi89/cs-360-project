package com.zybooks.cs_330project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class InventoryDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int VERSION = 2;

    private static InventoryDB mInventoryDB;

    public static InventoryDB getInstance(Context context) {
        if (mInventoryDB == null) {
            mInventoryDB = new InventoryDB(context);
        }
        return mInventoryDB;
    }

    public InventoryDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class ItemsTable {
        private static final String TABLE = "items";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_TITLE = "title";
        private static final String COL_QUANTITY = "quantity";
        private static final String COL_QUANTITY_UNITS = "quantityUnits";
    }

    private static final class UsersTable {
        private static final String TABLE = "users";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemsTable.TABLE + " (" +
                ItemsTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemsTable.COL_USERNAME + " TEXT, " +
                ItemsTable.COL_TITLE + " TEXT, " +
                ItemsTable.COL_QUANTITY + " TEXT, " +
                ItemsTable.COL_QUANTITY_UNITS + " TEXT, " +
                "FOREIGN KEY ("+ItemsTable.COL_USERNAME+") " +
                "REFERENCES "+UsersTable.TABLE+" ("+UsersTable.COL_USERNAME+"))");

        db.execSQL("CREATE TABLE " + UsersTable.TABLE + " (" +
                UsersTable.COL_USERNAME + " TEXT PRIMARY KEY, " +
                UsersTable.COL_PASSWORD + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemsTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE);
        onCreate(db);
    }

    public Item addItem(String username, String title, String quantity, String quantityUnits) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemsTable.COL_USERNAME, username);
        values.put(ItemsTable.COL_TITLE, title);
        values.put(ItemsTable.COL_QUANTITY, quantity);
        values.put(ItemsTable.COL_QUANTITY_UNITS, quantityUnits);

        long itemId = db.insert(ItemsTable.TABLE, null, values);
        Item item = new Item(itemId, username, title, quantity, quantityUnits);
        return item;
    }

    public boolean updateItem(long id, String title, String quantity, String quantityUnits) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemsTable.COL_TITLE, title);
        values.put(ItemsTable.COL_QUANTITY, quantity);
        values.put(ItemsTable.COL_QUANTITY_UNITS, quantityUnits);

        int rowsUpdated = db.update(ItemsTable.TABLE, values, "_id = ?",
                new String[] { Float.toString(id) });
        return rowsUpdated > 0;
    }

    public boolean deleteItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(ItemsTable.TABLE, ItemsTable.COL_ID + " = ?",
                new String[] { Long.toString(id) });
        return rowsDeleted > 0;
    }

    public List<Item> getItems(String username) {
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + ItemsTable.TABLE +
                " WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {username});
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String title = cursor.getString(2);
                String quantity = cursor.getString(3);
                String quantityUnits = cursor.getString(4);
                itemList.add(new Item(id, username, title, quantity, quantityUnits));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+UsersTable.TABLE+" WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+UsersTable.TABLE+" WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert(UsersTable.TABLE, null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}