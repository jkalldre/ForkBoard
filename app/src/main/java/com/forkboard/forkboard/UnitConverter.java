package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/25/2016.
 */
public class UnitConverter {
    private static final int W_indexFinder(Units u) {
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

    /**
     * Method converts units of weight measurement
     *
     * @param quantity the current value we want to convert
     * @param from the Units the Food is currently in
     * @param to the Units we want to change to
     * @return The results are converted units. If not valid returns -1
     */
    public static double convertEnglish_weight(double quantity, Units from, Units to) {
        if (from == Units.ounce && to == Units.pound) return quantity / 16;
        else
        if (from == Units.pound && to == Units.ounce) return quantity * 16;
        else
            return -1;
    }

    /**
     * Method converts units of volume measurement
     *
     * @param quantity the current value we want to convert
     * @param from the Units the Food is currently in
     * @param to the Units we want to change to
     * @return The results are converted units. If not valid returns -1
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

        int f = W_indexFinder(from);
        int t = W_indexFinder(to);
        if (f < 0 || t < 0) return -1;
        return quantity*volume_index[f][t];
    }
}
