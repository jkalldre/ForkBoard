package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/25/2016.
 */
public class Format {

    /*******************************************************
     * Takes a string of text and right justifies it.
     *******************************************************/
    public static String rightJustifyOnLine(String text, int width) {
        if (text.length() > width) return text;
        StringBuilder centered = new StringBuilder("");
        int num_spaces = (width - text.length());
        for (int i = 0; i < num_spaces; i++) centered.append(" ");
        //if (text.length() % 2 != 0 && width % 2 == 0) centered.append(" ");
        centered.append(text);
        return centered.toString();
    }

    /*******************************************************
     * Takes a string of text and centers it.
     *******************************************************/
    public static String centerOnLine(String text, int width) {
        if (text.length() > width) return text;
        StringBuilder centered = new StringBuilder("");
        int num_spaces = (width - text.length()) / 2;
        for (int i = 0; i < num_spaces; i++) centered.append(" ");
        if (text.length() % 2 != 0 && width % 2 == 0) centered.append(" ");
        centered.append(text);
        return centered.toString();
    }

    /*******************************************************
     * Wraps text within a margin
     *******************************************************/
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

    /*******************************************************
     * Take a double and converts it to the nearest fraction.
     * supports: halves, thirds, fourths, eigths, sixteenths
     *******************************************************/
    public static String fractionize(double number) {
        if (number == 0) return "0";

        double num = number % 1;
        String fract = "";
        double nume = 0;
        int deno = 0;

        if (num % 0.5 > 0.0 && num % 0.25 > 0.0) deno = 3;
        if (num % 0.5 == 0.0) deno = 2;
        else if (num % 0.25 == 0.0) deno = 4;
        else if (num % 0.125 == 0.0) deno = 8;
        else if (num % 0.0625 == 0.0) deno = 16;

        nume = deno * num;

        if (deno == 3) {
            if (nume <= 1) nume = 1;
            if (nume > 1 && nume < 2) nume = 2;
        }

        int whole = (int)(number - num);
        if (whole != 0) fract = "" + whole;

        if (whole != 0 && num >= 0.0625) fract += " ";

        if (whole == 0 && num < 0.0625) return "" + number;
        if (num < 0.0625) return fract;
        fract += "" + (int) nume + "/" + deno;

        return fract;
    }

    /*******************************************************
     * Takes a fraction (mixed or otherwise) and converts it
     * into its double form.
     *******************************************************/
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

    /*******************************************************
     * Generates an ID string in the form: "iiiiiiii" (i for int)
     *******************************************************/
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
}
