package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.model.Disk;
import com.example.model.Item;
import com.example.model.Order;

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

    public List<Disk> getAllDisks() {
        List<Disk> disks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("disk", null, null, null, null, null, null);
        while (c != null && c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            float price = c.getFloat(2);
            int image = c.getInt(3);
            disks.add(new Disk(id, image, price, name));
        }
        return disks;
    }

    public List<Disk> getMostFavoriteDisks() {
        List<Disk> disks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("disk", null, null, null, null, null, null);
        while (c != null && c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            float price = c.getFloat(2);
            int image = c.getInt(3);
            disks.add(new Disk(id, image, price, name));
        }
        return disks;
    }

    public long createOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put("quantity", order.getQuantity());
        values.put("date", order.getDate());
        values.put("status", order.getStatus());
        values.put("phone", order.getPhone());
        values.put("tableNumber", order.getTableNumber());
        values.put("disk_id", order.getDisk().getId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("diskOrder", null, values);
    }

    public long createDisk(Disk disk) {
        ContentValues values = new ContentValues();
        values.put("name", disk.getName());
        values.put("price", disk.getPrice());
        values.put("image", disk.getImage());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("disk", null, values);
    }

    public int updateOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put("quantity", order.getQuantity());
        values.put("date", order.getDate());
        values.put("status", order.getStatus());
        values.put("phone", order.getPhone());
        values.put("tableNumber", order.getTableNumber());
        values.put("disk_id", order.getDisk().getId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] args = {String.valueOf(order.getId())};
        return sqLiteDatabase.update("diskOrder", values, "id=?", args);
    }

    public int deleteOrder(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String clause = "id=?";
        String[] args = {String.valueOf(id)};
        return sqLiteDatabase.delete("diskOrder", clause, args);
    }

    public List<Order> getBill(String phone, int tableNumber) {
        SQLiteDatabase db = getReadableDatabase();
        List<Order> orders = new ArrayList<>();
        String[] args = {phone, String.valueOf(tableNumber)};
        Cursor c = db.rawQuery("select o.id, o.quantity, d.name, d.price " +
                "from disk as d, diskOrder as o " +
                "where d.id = o.diskId and o.phone = ? and o.tableNumber = ?", args);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                int quantity = c.getInt(1);
                String diskName = c.getString(2);
                float diskPrice = c.getFloat(3);
                Disk disk = new Disk(diskPrice, diskName);
                Order order = new Order(quantity, disk, id);
                orders.add(order);
                c.moveToNext();
            }
        }
        return orders;
    }

    public boolean checkout(String phone, int tableNumber) {
        List<Order> orders = getBill(phone, tableNumber);
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Order o : orders) {
                ContentValues values = new ContentValues();
                values.put("status", 2);
                String[] args = {String.valueOf(o.getId())};
                db.update("diskOrder", values, "id=?", args);
            }
            db.setTransactionSuccessful();
        } finally {
            db.close();
        }
        return true;
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
