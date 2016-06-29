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

    public void clear() { store.clear(); }

    public void add(Food more) {
        Food located = find(more.type());
        if (located != null) {
            located.add(more);
        }
        else store.add(more);
    }

    public void subtract (Food less) {
        Food located = find(less.type());
        if (located != null) {
            located.subtract(less);
        }
        else
            throw new NullPointerException("Requested item: " + less.type() + " DNE!");
    }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    public boolean empty() { return store.isEmpty(); }

    public Food find(String type) {
        for (Food spot : store) {
            if (spot.isSameFoodType(type)) return spot;
        }
        return null;
    }

    public boolean existsInCollection(Food item) {
        for (Food spot : store) {
            if (spot.isSameFoodType(item)) return true;
        }
        return false;
    }

    public Food[] toArray() {
        return store.toArray(new Food[store.size()]);
    }

    public Food[] findSimilar(FoodInventory other) {
        List<Food> similar = new ArrayList<Food>();
        for (Food spot : store) {
            if (other.existsInCollection(spot)) similar.add(spot);
        }
        return similar.toArray(new Food[similar.size()]);
    }

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

    //---------------------------------------------------------------
    // Object class overrides
    //---------------------------------------------------------------

    public String toString() {
        String printout = "";
        for (Food spot : store) {
            printout += spot.toString() + "\n";
        }
        return printout;
    }

    public List<String> ingredientList() {
        List<String> ret = new ArrayList<>();

        for (Food food : store) {
            ret.add(food.toString());
        }

        return ret;//.toArray(new String[ret.size()]);
    }
}
