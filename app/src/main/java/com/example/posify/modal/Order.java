package com.example.posify.modal;

import java.util.Objects;

public class Order {
    private String food_name;
    private String description;
    private Float price;
    private String image_url;
    private boolean isSelected = false;

    public Order(String food_name, String description, float price, String image_url) {
        this.food_name = food_name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }

    // Optional simplified constructor (if you don't use image_url somewhere)
    public Order(String food_name, String description, double price) {
        this.food_name = food_name;
        this.description = description;
        this.price = (float) price;
        this.image_url = "";
    }

    public String getName() {
        return food_name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return image_url;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return Objects.equals(food_name, order.food_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(food_name);
    }


}
