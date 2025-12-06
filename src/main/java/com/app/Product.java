package com.app;


import java.time.Instant;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private Instant creationDatetime;
    private List<Category> categories;

    public Product(int id, String name, double price, Instant creationDatetime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDatetime = creationDatetime;
    }


    @Override
    public String toString() {
        return "Product{id=" + id +
                ", name='" + name +
                "', price=" + price +
                ", creation_datetime=" + creationDatetime + "}";
    }
}
