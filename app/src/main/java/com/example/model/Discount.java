package com.example.model;

public class Discount {
    private int id;
    private String code;
    private float percentage;

    public Discount(int id, String code, float percentage) {
        this.id = id;
        this.code = code;
        this.percentage = percentage;
    }

    public Discount(String code, float percentage) {
        this.code = code;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
