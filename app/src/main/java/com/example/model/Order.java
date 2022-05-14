package com.example.model;

import java.io.Serializable;

public class Order implements Serializable {
    private int id, quantity, tableNumber, status;
    private Disk disk;
    private String date, phone;

    public Order(int quantity, int tableNumber, int status, Disk disk, String date, String phone) {
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.status = status;
        this.disk = disk;
        this.date = date;
        this.phone = phone;
    }

    public Order(int quantity, Disk disk, int id) {
        this.quantity = quantity;
        this.disk = disk;
        this.id = id;
    }

    public Order(int id, int quantity, int tableNumber, int status, Disk disk, String date, String phone) {
        this.id = id;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.status = status;
        this.disk = disk;
        this.date = date;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", tableNumber=" + tableNumber +
                ", status=" + status +
                ", disk=" + disk +
                ", date='" + date + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
