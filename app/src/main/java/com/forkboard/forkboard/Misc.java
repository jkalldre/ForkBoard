package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/23/2016.
 */
public class Misc {
    public static String generateIDfromInt(int id){
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
