package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 */
public enum Units {
    teaspoon, tablespoon,
    cup, pint, quart, gallon,
    pound, ounce,
    milliliter, liter,
    milligram, gram, kilogram,
    item;

    public String toString() {
        if (this == item) return "";
        else return this.name();
    }
}
