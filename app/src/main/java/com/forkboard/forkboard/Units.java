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

    public static Units fromString(String s){
        switch (s) {
            case "tsp.":      return teaspoon;
            case "tbsp.":     return tablespoon;
            case "fl. ox.":   return fluid_ounce;

            case "c.":        return cup;
            case "pt.":       return pint;
            case "qt.":       return quart;
            case "gal.":      return gallon;

            case "lb.":       return pound;
            case "oz.":       return ounce;

            case "ml.":       return milliliter;
            case "l":         return liter;

            case "mg.":       return milligram;
            case "g":         return gram;
            case "kg":        return kilogram;
            
            case "":          return item;
        }
        return item;
    }
}
