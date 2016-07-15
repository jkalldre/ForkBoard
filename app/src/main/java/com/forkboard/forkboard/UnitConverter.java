package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/25/2016.
 */
public class UnitConverter {
    private static final int V_indexFinder(Units u) {
        int idx = -1;
        switch (u) {
            case teaspoon:    idx = 0; break;
            case tablespoon:  idx = 1; break;
            case fluid_ounce: idx = 2; break;
            case cup:         idx = 3; break;
            case pint:        idx = 4; break;
            case quart:       idx = 5; break;
            case gallon:      idx = 6; break;
            case milliliter:  idx = 7; break;
            case liter:       idx = 8; break;
        }
        return idx;
    }

    private static final int W_indexFinder(Units u) {
        int idx = -1;
        switch (u) {
            case pound:     idx = 0; break;
            case ounce:     idx = 1; break;
            case milligram: idx = 2; break;
            case gram:      idx = 3; break;
            case kilogram:  idx = 4; break;
        }
        return idx;
    }

    /**
     * Convert a weight unit to another weight unit
     * @param quantity the current amount
     * @param from the units it is currently in
     * @param to the units we want it to be
     * @return the converted amount
     */
    public static double convert_weight(double quantity, Units from, Units to) {
        double [][] weight_index = {
                {1,16,45359.24,453.5924,0.4535924}, // pound
                {0.0625,1,2834.952,28.34952,0.02834952}, // ounce
                {0.000022046226,0.00035273962,1,0.01,0.000001}, // milligram
                {0.0022046226,0.035273962,1000,1,0.001}, // gram
                {2.2046226,35.273962,1000000,1000,1}  // kilogram
        };

        int f = W_indexFinder(from);
        int t = W_indexFinder(to);
        if (f < 0 || t < 0) return -1;
        return quantity*weight_index[f][t];
    }

    /**
     * Convert a volume unit to another weight unit
     * @param quantity the current amount
     * @param from the units it is currently in
     * @param to the units we want it to be
     * @return the converted amount
     */
    public static double convert_volume(double quantity, Units from, Units to) {
        double [][] volume_index = {
                {1,0.33333333333333333333333333333333,
                        0.16666666666666666666666666666667,
                        0.02083333333333333333333333333333,
                        0.01041666666666666666666666666667,
                        0.00520833333333333333333333333333,
                        0.00130208333333333333333333333333,
                        4.92892,0.00492892}, // teaspoon
                {3,1,0.5,0.0625,0.03125,0.015625,0.00390625,  14.7868, 0.0147868}, // tablespoon
                {6,2,1,0.125,0.0625,0.03125,0.0078125,   29.5735, 0.0295735}, // fluid_ounce
                {48,16,8,1,0.5,0.25,0.0625,   240, 0.24}, // cup
                {96,32,16,2,1,0.5,0.125,     473.176, 0.473176}, // pint
                {192,64,32,4,2,1,0.25,     946.353, 0.946353}, // quart
                {768,256,128,16,8,4,1,     3785.41, 3.78541}, // gallon

                {0.202884,0.067628,0.033814,0.00416667,0.00211338,0.00105669,0.000264172,    1, 0.001}, // milliliter
                {202.884,67.628,33.814,4.16667,2.11338,1.05669,0.264172,  1000, 1} // liter
        };

        int f = V_indexFinder(from);
        int t = V_indexFinder(to);
        if (f < 0 || t < 0) return -1;
        return quantity*volume_index[f][t];
    }
}
