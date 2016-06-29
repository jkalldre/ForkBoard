package com.forkboard.forkboard;

import android.util.Log;

/**
 * Created by Kyle on 6/23/2016.
 */
public class Misc {
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
