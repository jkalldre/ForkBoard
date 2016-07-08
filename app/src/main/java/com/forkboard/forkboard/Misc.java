package com.forkboard.forkboard;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static ArrayList<String> generateDateList(Calendar startDate, Calendar endDate) {
        ArrayList<String> dateList = new ArrayList<>();

        Calendar sDate = startDate;//new GregorianCalendar(2016,0,1);//startDate;
        Calendar eDate = endDate;//new GregorianCalendar(2016,0,11);//endDate;
        int totalDays = daysBetween(sDate.getTime(), eDate.getTime()) + 1;

        for (int i = 0; i < totalDays; i++) {
            String date = Format.generateDateString(sDate.get(Calendar.YEAR), sDate.get(Calendar.MONTH), sDate.get(Calendar.DAY_OF_MONTH));
            dateList.add(date);
            sDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        for (String date : dateList)
            System.out.println(date);
        return dateList;
    }

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }




    /**
     * Generates the ID string for printing to a file
     * @param id concatenate 0's onto an int for an 8 digit number
     * @return id in the form of "XXXXXXXX"
     */
    public static String generateIDfromInt(int id){
        return Format.eight_place_ID(id);
    }

    /**
     * Take a users input for a value and interpret it
     * @param value the string to be interpreted
     * @return the double converted from the string
     */
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


    //TODO allow for different string format MM-DD-YYYY
    public static boolean compareDates(String LHS, String RHS) {
        int[] l = Format.parseDate(LHS);
        int[] r = Format.parseDate(RHS);

        if (l[0] == r[0]) {
            if (l[1] == r[1]) {
                if (l[2] == r[2]) {
                    return true;
                } else return (l[2] < r[2]);
            } else return (l[1] < r[1]);
        } else return (l[0] < r[0]);
    }
}
