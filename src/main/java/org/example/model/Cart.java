package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, Integer> items = new HashMap<>();

    public void addItem(int id, int quantity) {
        items.put(id, quantity);
    }

    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    public void updateItemQuantity(int itemId, int quantity) {
        if (quantity <= 0) {
            removeItem(itemId);
        } else {
            items.put(itemId, quantity);
        }
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }
}
