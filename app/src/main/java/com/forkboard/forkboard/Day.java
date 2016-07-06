package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/30/2016.
 *
 *
 * TODO: tie into calendar
 *      In the calendar day viewer during the onCreate, create an instance of Day.
 *      Then do the following:
 *
 *          Day day = new Day(this);
 *          day.load("YYYYMMDD"); // where YYYY is the year, MM the month, and DD the day
 *
 *      Now the Day object is populated with a Recipe for each meal. If the day has never been
 *      set recipes before, then a blank recipe by the name of "(No Meal Selected)" is inserted
 *      into each meal.
 *
 *      for the Day viewer, simply type:
 *
 *          day.breakfast.name() to place in the text box.
 *
 *      Day has three Recipes:
 *          day.breakfast
 *          day.lunch
 *          day.dinner
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.*;   // for File, FileReader, BufferedReader, and exceptions

public class Day {
    private final String ext;
    public Recipe breakfast;
    public Recipe lunch;
    public Recipe dinner;


    private Context context;

    public Day(Context con) {
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

    public void load(String date) {
        System.out.println("READING: " + date + ext);
        String dir = context.getFilesDir().getPath() + "/";
        File day = new File(dir + date + ext);
        if(day.exists() && day.canRead()) {
            readDay(dir + date + ext);
        }
    }

    public void save(String date) {
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void readDay(String file) {
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

    public String generatePrintString() {
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