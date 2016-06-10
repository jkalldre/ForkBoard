package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 */
public enum Units {
    teaspoon, tablespoon, fluid_ounce,
    cup, pint, quart, gallon,
    pound, ounce,
    milliliter, liter,
    milligram, gram, kilogram,
    item;

    public String toString() {
        switch (this) {
            case teaspoon:    return "tsp.";
            case tablespoon:  return "tbsp.";
            case fluid_ounce: return "fl. oz.";

            case cup:         return "c.";
            case pint:        return "pt.";
            case quart:       return "qt.";
            case gallon:      return "gal.";

            case pound:       return "lb.";
            case ounce:       return "oz.";

            case milliliter:  return "ml";
            case liter:       return "l";

            case milligram:   return "mg";
            case gram:        return "g";
            case kilogram:    return "kg";

            default: return "";
        }
    }
}
