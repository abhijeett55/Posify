package com.example.posify.modal;

import com.example.posify.items.ListItem;

public class CategoryHeader implements ListItem {
    private final String categoryName;

    public CategoryHeader(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public int getType() {
        return ListItem.TYPE_HEADER;
    }
}