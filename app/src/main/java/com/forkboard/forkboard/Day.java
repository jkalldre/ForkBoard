package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/30/2016.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.*;   // for File, FileReader, BufferedReader, and exceptions

public class Day {
    // instance variables
    private final String ext;
    public Recipe breakfast;
    public Recipe lunch;
    public Recipe dinner;
    private Context context;

    /**
     * CONSTRUCTOR
     * @param con the context we are in
     */
    public Day(Context con) {
        // handle cases where no recipes are selected
        context = con;
        ext = ".day";
        String n = "(No Meal Selected)";
        breakfast = new Recipe();
        breakfast.name(n);
        breakfast.ID("EMPTY_ID");
        lunch = new Recipe();
        lunch.name(n);
        lunch.ID("EMPTY_ID");
        dinner = new Recipe();
        dinner.name(n);
        dinner.ID("EMPTY_ID");
    }

    /**
     * Load in a single day's worth of recipes
     * @param date the date to read in. In the form: YYYY_M+_D+
     */
    public void load(String date) {
        // load meals into approprate days
        System.out.println("READING: " + date + ext);
        String dir = context.getFilesDir().getPath() + "/";
        File day = new File(dir + date + ext);
        if(day.exists() && day.canRead()) {
            readDay(dir + date + ext);
        }
    }

    /**
     * Save a day's meal selections
     * @param date the date to write. In the form: YYYY_M+_D+
     */
    public void save(String date) {
        // save loaded days into .day files
        String print = generatePrintString();
        if (print.length() > 7) {
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(date + ext, Context.MODE_PRIVATE);
                outputStream.write(print.getBytes());
                outputStream.close();
                System.out.println("WROTE: " + date + ext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses the *.day file and loads in associated recipes
     * @param file the name of the *.day file to read.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void readDay(String file) {
        // handles the actual reading of the file
        String b_ID = "";
        String l_ID = "";
        String d_ID = "";
        try(BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = buffer.readLine()) != null) {
                if (line.substring(0,6).equals("@BREAK")) {
                    b_ID = line.substring(7);
                }
                if (line.substring(0,6).equals("@LUNCH")) {
                    l_ID = line.substring(7);
                }
                if (line.substring(0,6).equals("@DINNR")) {
                    d_ID = line.substring(7);
                }
            }
        }
        catch(Exception ex) {
            System.out.println("ERROR WITH READING: file");
        }

        RecipeLogHandler handler = new RecipeLogHandler(context);
        handler.load();
        for (Recipe recipe : handler.cookbook.getRecipes()) {
            if (recipe.ID().equals(b_ID)){
                breakfast = recipe;
            }
            if (recipe.ID().equals(l_ID)){
                lunch = recipe;
            }
            if (recipe.ID().equals(d_ID)){
                dinner = recipe;
            }
        }
    }

    /**
     * Create the string to print to the *.day file.
     * @return the formatted string.
     */
    public String generatePrintString() {
        // print out the meals held by day
        String print = "";
        if (breakfast != null)
            print += "@BREAK " + breakfast.ID() + "\n";
        if (lunch != null)
            print += "@LUNCH " + lunch.ID() + "\n";
        if (dinner != null)
            print += "@DINNR " + dinner.ID();
        Log.i("GEN_PRINT_STR ",print);
        return print;
    }
}