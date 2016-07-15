package com.forkboard.forkboard;

import android.content.Context;

/**
 * Created by Kyle on 6/30/2016.
 *
 *
 * This Object is not used in the project, but meant for future productions
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

    /**
     * Load Shopping List
     */
    public void load() {

    }

    /**
     * Save Shopping List
     */
    public void save() {

    }

    /**
     * Add to Shopping List
     * @param item item to be added
     */
    public void add(String item) {

    }
}
