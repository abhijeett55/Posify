package com.example.posify.modal;

public class Order {
    public String food_name;
    public String description;
    public Float price;
    public String image_url;

    public Order(String food_name, String description, float price, String image_url) {
        this.food_name = food_name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }

}
