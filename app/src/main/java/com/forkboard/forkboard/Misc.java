package com.forkboard.forkboard;

import android.util.Log;

/**
 * Created by Kyle on 6/23/2016.
 */
public class Misc {

    public static String[] _units = {
            "(no units)",
            Units.teaspoon.toString(), Units.tablespoon.toString(), Units.fluid_ounce.toString(),
            Units.cup.toString(), Units.pint.toString(), Units.quart.toString(), Units.gallon.toString(),
            Units.pound.toString(), Units.ounce.toString(),
            Units.milliliter.toString(), Units.liter.toString(),
            Units.milligram.toString(), Units.gram.toString(), Units.kilogram.toString()};

    public static String[] _weights = {
            Units.pound.toString(), Units.ounce.toString(), Units.gram.toString(),
            Units.milligram.toString(), Units.kilogram.toString()
    };

    public static String[] _volumes = {
            Units.teaspoon.toString(), Units.tablespoon.toString(), Units.fluid_ounce.toString(),
            Units.cup.toString(), Units.pint.toString(), Units.quart.toString(), Units.gallon.toString(),
            Units.milliliter.toString(), Units.liter.toString()
    };



    public static String generateIDfromInt(int id){
        return Format.eight_place_ID(id);
    }

    public static double processUserQuantityInput(String value) {
        double ret;
        if (value.contains("/")) {
            ret = Format.defractionize(value);
        } else {
            try {
                ret = Double.parseDouble(value);
            } catch (Exception ex) {
                Log.e("USER ERROR", "processUserQuantityInput type error");
                ret = -1;
            }
        }
        return ret;
    }
}
