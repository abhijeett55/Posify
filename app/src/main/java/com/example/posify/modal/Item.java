package com.example.posify.modal;

public class Item {
    public int id;
    public String name;
    public double price;
    public int quantity;
    public String timestamp;

    public Item(int id, String name, double price, int quantity, String timestamp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }
}