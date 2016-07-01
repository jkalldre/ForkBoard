package com.forkboard.forkboard;

import android.content.Context;

/**
 * Created by Kyle on 6/30/2016.
 */
public class ShoppingListHandler implements DataHandler {
    private Context context;

    public FoodInventory shoppingList;

    public ShoppingListHandler(Context con) {
        context = con;
        shoppingList = new FoodInventory();
    }

    public ShoppingListHandler(Context con, FoodInventory list) {
        context = con;
        shoppingList = new FoodInventory(list);
    }

    public void load() {

    }

    public void save() {

    }

    public void add(String item) {

    }
}
