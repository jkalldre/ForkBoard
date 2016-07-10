package com.forkboard.forkboard;

import java.util.GregorianCalendar;

/**
 * Created by Kyle on 6/25/2016.
 */
public class Format {
    /**
     * Take a double and converts it to the nearest fraction.
     * <p> supports: halves, thirds, fourths, eigths, sixteenths </p>
     * @param number the thing to turn into a number
     * @return the fractionized string
     */
    public static String fractionize(double number) {
        if (number == 0) return "0";

        double num = number % 1;
        if (num == 0) return "" + (int) number;
        String fract = "";
        double nume = 0;
        int deno = 0;

        if ((num >= 0.29 && num <= 0.35) || // test if 1/3 ish
                (num >= 0.59 && num <= 0.68))   // test if 2/3 ish
        {
            deno = 3;
        }

        if ((num >= 0.155 && num <= 0.175) || // test if 1/6 ish
                (num >= 0.825 && num <= 0.845))   // test if 5/6 ish
        {
            deno = 6;
        }

        if (num % 0.5 == 0.0) deno = 2;
        else if (num % 0.25 == 0.0) deno = 4;
        else if (num % 0.125 == 0.0) deno = 8;
        else if (num % 0.0625 == 0.0) deno = 16;

        nume = deno * num;

        if (deno == 3) {
            if (nume <= 1) nume = 1;
            if (nume > 1 && nume < 2) nume = 2;
        }

        if (deno == 6) {
            if (nume <= 3) nume = 1;
            else nume = 5;
        }

        int whole = (int)(number - num);
        if (whole != 0) fract = "" + whole;

        if (whole != 0 && num >= 0.0625) fract += " ";

        if ((whole == 0 && num < 0.0625) ||
                (((int) nume) == 0 || deno == 0)) return "" + number;
        if (num < 0.0625) return fract;
        fract += "" + (int) nume + "/" + deno;

        return fract;
    }


    /**
     * Takes a fraction (mixed or otherwise) and convert into double
     * @param num the fraction string
     * @return a double the fraction represneted
     */
    public static double defractionize(String num) {
        String[] whole_from_fract = num.split("\\s+");
        String[] parts;
        int whole;

        if (whole_from_fract.length == 1) {
            parts = whole_from_fract[0].split("/");
            whole = 0;
        }
        else {
            parts = whole_from_fract[1].split("/");
            whole = Integer.parseInt(whole_from_fract[0]);
        }

        double nume = Integer.parseInt(parts[0]);
        double deno = Integer.parseInt(parts[1]);

        return (nume / deno) + whole;
    }

    /**
     * Generates an ID string in the form: "iiiiiiii" (i for int)
     * @param id the int we will append zeros to
     * @return the string in the form "iiiiiiii"
     */
    public static String eight_place_ID(int id){
        String sid = "";
        if (id > 99999999) {
            sid += "X";
            sid += "" + id % 10000000;
        } else {
            if (id < 10000000) sid += "0";
            if (id < 1000000) sid += "0";
            if (id < 100000) sid += "0";
            if (id < 10000) sid += "0";
            if (id < 1000) sid += "0";
            if (id < 100) sid += "0";
            if (id < 10) sid += "0";
            sid += "" + id;
        }
        return sid;
    }

    /**
     * Right justify output text
     * @param text the text we want to align
     * @param width how much space (in chars) we are in
     * @return an aligned string
     */
    public static String rightJustifyOnLine(String text, int width) {
        if (text.length() > width) return text;
        StringBuilder centered = new StringBuilder("");
        int num_spaces = (width - text.length());
        for (int i = 0; i < num_spaces; i++) centered.append(" ");
        //if (text.length() % 2 != 0 && width % 2 == 0) centered.append(" ");
        centered.append(text);
        return centered.toString();
    }

    /**
     * Center justify output text
     * @param text the text we want to align
     * @param width how much space (in chars) we are in
     * @return an aligned string
     */
    public static String centerOnLine(String text, int width) {
        if (text.length() > width) return text;
        StringBuilder centered = new StringBuilder("");
        int num_spaces = (width - text.length()) / 2;
        for (int i = 0; i < num_spaces; i++) centered.append(" ");
        if (text.length() % 2 != 0 && width % 2 == 0) centered.append(" ");
        centered.append(text);
        return centered.toString();
    }

    /**
     * Wraps text block within a margin
     * @param text the text block to wrap around
     * @param width how much space (in chars) in each line
     * @return an string of wrapped text
     */
    public static String marginize(String text, int width) {
        StringBuilder marginized = new StringBuilder("");
        String[] words = text.split("\\s+");
        int lineCount = 0;
        for (String word : words) {
            if (lineCount + word.length() >= width) {
                marginized.append("\n");
                lineCount = 0;
            }
            marginized.append(word + " ");
            lineCount += word.length() + 1;
        }
        return marginized.toString();
    }

    /**
     * Capatize every word in a string
     * @param string string to capitalize
     * @return capitalize string
     */
    public static String capitalizeFully(String string) {
        String[] words = string.split(" ");
        String output = "";
        for (String word : words) {
            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            output += cap + " ";
        }
        output = output.trim();
        return output;
    }

    /**
     * Parse a date String of the form YYYY_M+_D+
     * @param date the date YYYY_M+_D+
     * @return int array with the respective {year,month,day}
     */
    public static int[] parseDate(String date) {
        int[] iDate = new int[3];
        String[] sDate = date.split("_");

        if (sDate.length != 3) return null;

        iDate[0] = Integer.parseInt(sDate[0]);
        iDate[1] = Integer.parseInt(sDate[1]);
        iDate[2] = Integer.parseInt(sDate[2]);

        return iDate;
    }

    /**
     * Generates a date string in the form : YYYY_M+_D+
     * @param year the year int
     * @param month the month int
     * @param day the day int
     * @return the date as a string YYYY_M+_D+
     */
    public static String generateDateString(int year, int month, int day) {
        return "" + year + "_" + month + "_" + day;
    }

    public static GregorianCalendar MMDDYYYY_to_Gregorian(String date){
        int[] iDate = new int[3];
        String[] sDate = date.split("-");
        iDate[0] = Integer.parseInt(sDate[0]);
        iDate[1] = Integer.parseInt(sDate[1]);
        iDate[2] = Integer.parseInt(sDate[2]);
        return new GregorianCalendar(iDate[2], iDate[0] - 1, iDate[1]);
    }

    public static String changeFormat(String date){
        String[] dates = new String[3];
        dates = date.split("-");
        String toReturn = "" + dates[1] + "_" + dates[2] + "_" + dates[0];
        return toReturn;
    }
}
