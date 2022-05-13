package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "restaurant.db";
    private static int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreateDisk = "create table if not exists disk" +
                "(id integer primary key autoincrement not null," +
                "name text," +
                "price real, image integer)";

        String sqlCreateOrder = "create table if not exists diskOrder" +
                "(id integer primary key autoincrement not null," +
                "quantity integer, date text, status integer," +
                "phone text, tableNumber integer," +
                "disk_id integer," +
                "foreign key (disk_id) references disk(id))";

        db.execSQL(sqlCreateDisk);
        db.execSQL(sqlCreateOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists disk");
        db.execSQL("drop table if exists diskOrder");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAllItems() {
        return getItems(null, null);
    }

    public List<Item> searchItems(String sql, String[] args) {
        List<Item> items = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, args);
//        Log.i("CURSOR COUNT", "" + cursor.getCount());
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                int id = cursor.getInt(0);
//                String title = cursor.getString(1);
//                String author = cursor.getString(2);
//                String bShort = cursor.getString(3);
//                String publisher = cursor.getString(4);
//                float rate = cursor.getFloat(5);
//                items.add(new Item(id, title, author, bShort, publisher, rate));
//                cursor.moveToNext();
//            }
//        }
        return items;
    }

    public List<Item> getItems(String clause, String[] args) {
        List<Item> items = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.query("book", null, clause, args, null, null, "rate desc");
//        while (cursor != null && cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            String author = cursor.getString(2);
//            String bShort = cursor.getString(3);
//            String publisher = cursor.getString(4);
//            float rate = cursor.getFloat(5);
//            items.add(new Item(id, title, author, bShort, publisher, rate));
//        }
        return items;
    }

    public long createItem(Item item) {
//        ContentValues values = new ContentValues();
//        values.put("title", item.getTitle());
//        values.put("author", item.getAuthor());
//        values.put("bShort", item.getbShort());
//        values.put("publisher", item.getPublisher());
//        values.put("rate", item.getRate());
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        return sqLiteDatabase.insert("book", null, values);
        return -1;
    }

    public int updateItem(Item item) {
//        ContentValues values = new ContentValues();
//        values.put("title", item.getTitle());
//        values.put("author", item.getAuthor());
//        values.put("bShort", item.getbShort());
//        values.put("publisher", item.getPublisher());
//        values.put("rate", item.getRate());
//
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//
//        String clause = "id=?";
//        String[] args = {String.valueOf(item.getId())};
//
//        return sqLiteDatabase.update("book", values, clause, args);
        return -1;
    }

    public int deleteItem(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String clause = "id=?";
        String[] args = {String.valueOf(id)};
        return sqLiteDatabase.delete("book", clause, args);
    }
}
