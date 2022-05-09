package com.example.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private float rate;
    private String title, author;
    private String bShort, publisher;

    public Item() {
    }

    public Item(int id, String title, String author, String bShort, String publisher, float rate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bShort = bShort;
        this.publisher = publisher;
        this.rate = rate;
    }

    public Item(String title, String author, String bShort, String publisher, float rate) {
        this.title = title;
        this.author = author;
        this.bShort = bShort;
        this.publisher = publisher;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getbShort() {
        return bShort;
    }

    public void setbShort(String bShort) {
        this.bShort = bShort;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", rate=" + rate +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", bShort='" + bShort + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
