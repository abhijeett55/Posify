package com.example.posify.modal;

    public class FoodItem {
        private final int id;
        private final String name;
        private final String description;
        private final double price;
        private final String imageUrl;

        public FoodItem(int id, String name, String description, double price, String image_url) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageUrl = image_url;
        }

        public int getId() {
            return id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public double getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

