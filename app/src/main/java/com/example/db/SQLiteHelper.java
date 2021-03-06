package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.model.Discount;
import com.example.model.Disk;
import com.example.model.Item;
import com.example.model.Order;
import com.example.model.Values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "restaurant4.db";
    private static int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreateDisk = "create table if not exists disk" +
                "(id integer primary key autoincrement not null," +
                "name text," +
                "price real, image integer, type text default 'FOOD')";

        String sqlCreateOrder = "create table if not exists diskOrder" +
                "(id integer primary key autoincrement not null," +
                "quantity integer, date text, status integer," +
                "phone text, tableNumber integer," +
                "disk_id integer, discount_id integer, " +
                "foreign key (disk_id) references disk(id), " +
                "foreign key (discount_id) references discount(id))";

        String sqlCreateDiscount = "create table if not exists discount" +
                "(id integer primary key autoincrement not null," +
                "code text not null unique," +
                "percentage real)";

        db.execSQL(sqlCreateDisk);
        db.execSQL(sqlCreateOrder);
        db.execSQL(sqlCreateDiscount);
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

    public List<Disk> getAllDisks(Values.EnumDiskType byType) {
        List<Disk> disks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {byType.name()};
        Cursor c = db.rawQuery("select * from disk where type = ?", args);
        while (c != null && c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            float price = c.getFloat(2);
            int image = c.getInt(3);
            Values.EnumDiskType type = Values.EnumDiskType.valueOf(c.getString(4));
            disks.add(new Disk(id, image, price, name, type));
        }
        return disks;
    }

    public List<Order> getCurrentOrders(String phone, int tableNumber) {
        SQLiteDatabase db = getReadableDatabase();
        List<Order> orders = new ArrayList<>();
        String[] args = {phone, String.valueOf(tableNumber)};
        Cursor c = db.rawQuery("select o.id, o.quantity, d.name, d.price, o.status " +
                "from disk as d, diskOrder as o " +
                "where d.id = o.disk_id and o.phone = ? and o.tableNumber = ? and o.status in (0, 1)", args);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                int quantity = c.getInt(1);
                String diskName = c.getString(2);
                float diskPrice = c.getFloat(3);
                int status = c.getInt(4);
                Disk disk = new Disk(diskPrice, diskName);
                Order order = new Order(quantity, disk, id, status);
                orders.add(order);
                c.moveToNext();
            }
        }
        return orders;
    }

    public int updateOrderByQuantity(Order order) {
        ContentValues values = new ContentValues();
        values.put("quantity", order.getQuantity());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] args = {String.valueOf(order.getId())};
        return sqLiteDatabase.update("diskOrder", values, "id=?", args);
    }

    public List<Disk> getMostFavoriteDisks() {
        List<Disk> disks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {};
        Cursor c = db.rawQuery("select d.id, d.name, d.price, d.image, d.type, count(d.id) " +
                "from disk as d, diskOrder as o " +
                "where o.disk_id = d.id and o.status = 2 " +
                "group by d.id having count(d.id) > 0 order by count(d.id) desc", args);
        while (c != null && c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            float price = c.getFloat(2);
            int image = c.getInt(3);
            Values.EnumDiskType type = Values.EnumDiskType.valueOf(c.getString(4));
            disks.add(new Disk(id, image, price, name, type));
        }
        return disks;
    }

    public List<Discount> getAllDiscount() {
        List<Discount> discounts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {};
        Cursor c = db.rawQuery("select * from discount", args);
        while (c != null && c.moveToNext()) {
            int id = c.getInt(0);
            String code = c.getString(1);
            float percentage = c.getFloat(2);
            discounts.add(new Discount(id, code, percentage));
        }
        return discounts;
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

    public long createDiscount(Discount discount) {
        ContentValues values = new ContentValues();
        values.put("code", discount.getCode());
        values.put("percentage", discount.getPercentage());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("discount", null, values);
    }

    public long createDisk(Disk disk) {
        ContentValues values = new ContentValues();
        values.put("name", disk.getName());
        values.put("price", disk.getPrice());
        values.put("image", disk.getImage());
        values.put("type", disk.getType().name());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("disk", null, values);
    }

    public int updateOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put("status", order.getStatus());
        if(order.getDiscount() != null) {
            values.put("discount_id", order.getDiscount().getId());
        }
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
        Cursor c = db.rawQuery("select o.id, o.quantity, d.name, d.price, o.status, d.type " +
                "from disk as d, diskOrder as o " +
                "where d.id = o.disk_id and o.phone = ? and o.tableNumber = ? and o.status = 1", args);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                int quantity = c.getInt(1);
                String diskName = c.getString(2);
                float diskPrice = c.getFloat(3);
                int status = c.getInt(4);
                String type = c.getString(5);
                Disk disk = new Disk(diskPrice, diskName);
                disk.setType(Values.EnumDiskType.valueOf(type));
                Order order = new Order(quantity, disk, id, status);
                orders.add(order);
                c.moveToNext();
            }
        }
        return orders;
    }

    public boolean pay(String phone, int tableNumber, Discount discount) {
        List<Order> orders = getBill(phone, tableNumber);
        try {
            for (Order o : orders) {
                o.setStatus(Values.ORDER_STATUS_PAID);
                o.setDiscount(discount);
                updateOrder(o);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean confirmOrders(String phone, int tableNumber) {
        List<Order> orders = getCurrentOrders(phone, tableNumber);
        try {
            for (Order o : orders) {
                o.setStatus(Values.ORDER_STATUS_ACCEPTED);
                updateOrder(o);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Discount getDiscount(String byCode) {
        try {
            String[] args = {byCode};
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("select * from discount where code = ?", args);
            Discount discount = null;
            if (c != null && c.moveToNext()) {
                int id = c.getInt(0);
                String code = c.getString(1);
                float percent = c.getFloat(2);
                discount = new Discount(id, code, percent);
            }
            return discount;
        }
        catch(Exception e) {
            return null;
        }
    }

}
