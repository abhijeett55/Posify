package com.example.posify.modal;

import com.example.posify.items.ListItem;

public class FoodItem implements ListItem {
        private final int id;
        private final String name;
        private final String description;
        private final double price;
        private final String imageUrl;

        private final String category;

        public FoodItem(int id, String name, String description, double price, String image_url , String category) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageUrl = image_url;
            this.category = category;
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

        public String getCategory() {
            return category;
        }



    @Override
    public int getType() {
        return ListItem.TYPE_FOOD;
    }
}

