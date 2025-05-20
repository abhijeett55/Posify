package com.example.posify.modal;

public class Item {
    private int id;
    private String name;
    private final double price;
    private final int quantity;
    private final String timestamp;

    public Item(int id, String name, double price, int quantity, String timestamp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
