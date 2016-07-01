package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 */
import java.util.List;
import java.util.ArrayList;
import java.lang.NullPointerException;

public class FoodInventory {
    //---------------------------------------------------------------
    // State
    //---------------------------------------------------------------

    protected List<Food> store;

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------

    public FoodInventory () {
        store = new ArrayList<Food>();
    }

    public FoodInventory (FoodInventory other) {
        store = new ArrayList<Food>();
        for (Food item : other.toArray()) {
            store.add(item.copy());
        }
    }

    //---------------------------------------------------------------
    // Modifiers
    //---------------------------------------------------------------

    /**
     * Clear the Inventory
     */
    public void clear() { store.clear(); }

    /**
     * replace a food item stored
     * @param repl the item to replace
     */
    public void replace(Food repl) {
        Food located = find(repl.type());
        if (located != null) {
            store.remove(located);
            store.add(repl);
        }
        else store.add(repl);
    }

    /**
     * Add an item to the inventory, if already exits, add foods together
     * @param more the food we are adding
     */
    public void add(Food more){
        Food located = find(more.type());
        if(located != null)
            located.add(more);
        else
            store.add(more);
    }

    /**
     * Subtract an item to the inventory
     * @param less food to take away
     */
    public void subtract(Food less) {
        Food located = find(less.type());
        if (located != null) {
            located.subtract(less);
        }
        else
            throw new NullPointerException("Requested item: " + less.type() + " DNE!");
    }

    /**
     * remove an item all together from the inventory
     * @param toDelete the Food marked for removal
     */
    public void remove(Food toDelete) {
        Food located = find(toDelete.type());
        if (located != null)
           store.remove(located);
    }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    /**
     * CHECK if the inventory is empty
     * @return true if empty
     */
    public boolean empty() { return store.isEmpty(); }

    /**
     * Find a foo item
     * @param type the type if the food we are looking up
     * @return the found food, if DNE - return null
     */
    public Food find(String type) {
        for (Food spot : store) {
            if (spot.isSameFoodType(type)) return spot;
        }
        return null;
    }

    /**
     * Returns true if the food object exists in the array
     * @param item item to search for
     * @return Boolean
     */
    public boolean existsInCollection(Food item) {
        for (Food spot : store) {
            if (spot.isSameFoodType(item)) return true;
        }
        return false;
    }

    /**
     * Returns the list in the form as a food array
     * @return Food Array
     */
    public Food[] toArray() {
        return store.toArray(new Food[store.size()]);
    }

    /**
     * Returns an array of food objects that two FoodInventories
     * have in common
     * @param other name of second foodInventory
     * @return Food Array
     */
    public Food[] findSimilar(FoodInventory other) {
        List<Food> similar = new ArrayList<Food>();
        for (Food spot : store) {
            if (other.existsInCollection(spot)) similar.add(spot);
        }
        return similar.toArray(new Food[similar.size()]);
    }

    /**
     * Retuns an array of food objects that two FoodInventories
     * do not have in common
     * @param other name of second foodInventory
     * @return Food Array
     */
    public Food[] findDifferent(FoodInventory other) {
        List<Food> different = new ArrayList<Food>();
        for (Food spot : store) {
            if (!other.existsInCollection(spot)) different.add(spot);
        }
        for (Food spot : other.store) {
            if (!existsInCollection(spot)) different.add(spot);
        }
        return different.toArray(new Food[different.size()]);
    }

    /**
     * Return Food item with that matches food name
     * @param name name of desired food object
     * @return Food
     */
    public Food get(String name) {
        for (Food food : store) {
            if (food.toString().equals(name))
                return food;
        }
        return null;
    }

    //---------------------------------------------------------------
    // Object class overrides
    //---------------------------------------------------------------

    /**
     * Print out recipe book
     * @return String
     */
    public String toString() {
        String printout = "";
        for (Food spot : store) {
            printout += spot.toString() + "\n";
        }
        return printout;
    }

    /**
     * Return FoodInventory in form of a List
     * @return List
     */
    public List<String> ingredientList() {
        List<String> ret = new ArrayList<>();
        for (Food food : store) {
            ret.add(food.toString());
        }
        return ret;
    }
}
