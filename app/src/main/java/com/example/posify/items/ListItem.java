package com.example.posify.items;

public interface ListItem {
    int TYPE_HEADER = 0;
    int TYPE_FOOD = 1;

    int getType();
}
