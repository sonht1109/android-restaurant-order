package com.example.model;

import java.io.Serializable;

public class Disk implements Serializable {

    private int id, image;
    private float price;
    private String name;

    public Disk(int image, float price, String name) {
        this.image = image;
        this.price = price;
        this.name = name;
    }

    public Disk(int id, int image, float price, String name) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.name = name;
    }

    public Disk(float price, String name) {
        this.price = price;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "id=" + id +
                ", image=" + image +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
