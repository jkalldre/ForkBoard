package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/16/2016.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.*;   // for File, FileReader, BufferedReader, and exceptions
import java.util.*; // for List, and ArrayList

/**
 * RecipeLogHandler is our datahandler for our recipes. It has
 * functions that allow us to save to custom .recipe files and read
 * and load files into Recipe objects and add them to our RecipeLog(Cookbook)
 */
public class RecipeLogHandler implements DataHandler {
    public  RecipeLog cookbook;
    private Context   context; // new

    public static final String TAG = "~> RECIPE_LOG_HANDLER";

    public RecipeLogHandler(Context con) {
        context  = con;
        cookbook = new RecipeLog();
    }

    /**
     * Load reads all files saved in the android directory and loads them
     * into the cookbook to be used by the different activities. Providing
     * a universal cookbook.
     */
    public void load() {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput("INITIALIZE_DIR.txt", Context.MODE_PRIVATE);
            String prnt  = "DIRECTORY NOW CREATED";
            outputStream.write(prnt.getBytes());
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String dir = context.getFilesDir().getPath() + "/";
        Log.i(TAG, "Working in: " + dir);
        System.out.println("Working in: " + dir);
        File[] files = new File(dir).listFiles();
        for (File f : files) {
            if (f.getName().length() > 8 && f.getName().substring(f.getName().length() - 7).equals(".recipe")) {
                System.out.println("Reading: " + f.getName());
                doFile(dir + f.getName());
            }

        }
    }

    /**
     * Save saves all the files to a directory in internal memory in the format of our .recipe
     * files. This allows them to be read back in later with Load
     */
    public void save() {
        for (Recipe recipe : cookbook.getRecipes()) {
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(recipe.ID() + ".recipe",
                        Context.MODE_PRIVATE);

                Log.i(TAG,"Writing: " + recipe.ID() + ".recipe");
                String prnt = recipe.toFileString();
                outputStream.write(prnt.getBytes());
                outputStream.close();
                File file = new File(context.getFilesDir(), recipe.ID() + ".recipe");
                Log.i(TAG, "Wrote: " + file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove any number of items from the filesystem
     * @param params the Recipes to remove
     */
    public void remove(Recipe... params){
        for (Recipe recipe : params) {
            context.deleteFile(recipe.ID() + ".recipe");
        }
    }

    /**
     * Re-save any number of items to the filesystem
     * @param params the Recipes to update
     */
    public void update(Recipe... params){
        for (Recipe recipe : params) {

            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(recipe.ID() + ".recipe",
                        Context.MODE_PRIVATE);

                Log.i(TAG,"Writing: " + recipe.ID() + ".recipe");
                String prnt = recipe.toFileString();
                outputStream.write(prnt.getBytes());
                outputStream.close();
                File file = new File(context.getFilesDir(), recipe.ID() + ".recipe");
                Log.i(TAG, "Wrote: " + file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void doFile(String filename) {
        if (okFile(filename)) {
            try(BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
                String ID           = "";
                String NAME         = "";
                FoodInventory INGREDIENTS  = new FoodInventory();
                String        INSTRUCTIONS = "";
                int PREP     = 0;
                int SERV     = 0;
                String line  = "";
                String units = "";

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@@@ID")) {
                    ID = line.substring(6);
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@NAME")) {
                    NAME = line.substring(6);
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@PREP")) {
                    PREP = Integer.parseInt(line.substring(6));
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@SERV")) {
                    SERV = Integer.parseInt(line.substring(6));
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@INGS")) {
                    while (!line.substring(0,5).equals("@INGE")) {
                        if ( (line = buffer.readLine()) != null
                                && line.substring(0,5).equals("@INGR")) {
                            String foodName = line.substring(6);
                            double amount = 0;
                            if ( (line = buffer.readLine()) != null
                                    && line.substring(0,5).equals("@ICNT")) {
                                amount = Double.parseDouble(line.substring(6));
                            }
                            if ( (line = buffer.readLine()) != null
                                    && line.substring(0,5).equals("@IUNT")) {
                                if (line.length() < 6)
                                    units = "";
                                else
                                    units = line.substring(6);
                            }
                            INGREDIENTS.add(new Food(foodName, amount, Units.fromString(units)));
                        }
                    }
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@INSS")) {
                    while (true) {
                        if ( (line = buffer.readLine()) != null
                                && !line.substring(0,1).equals("@")) {
                            INSTRUCTIONS += line;
                        } else break;
                    }
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@END!")) {
                    Recipe item = new Recipe(NAME, INGREDIENTS, INSTRUCTIONS, PREP, SERV);
                    item.ID(ID);
                    Log.i("SAVED" + item.ID(),item.toFileString());
                    cookbook.add(item);
                }
            }
            catch(Exception ex) {
                Log.e(TAG,"Error with '" + filename + "'");
                Log.e(TAG,ex.getMessage());
                //System.exit(-98);
            }
        }
        else {
            Log.e(TAG,"Incorrect location for '" + filename + "'");
            //System.exit(-99);
        }
    }

    private Boolean okFile(String fname) {
        File test = new File(fname);
        if(test.exists() && test.canRead())
            return true;
        else
            return false;
    }

    /**
     * Insert arbitrary items into the cookbook (for testing)
     */
    public void mock() {
        FoodInventory ingr = new FoodInventory();
        ingr.add(new Food("Honey Nut O's", 3, Units.cup));
        ingr.add(new Food("milk", 2, Units.cup));
        ingr.add(new Food("sugar", 1, Units.tablespoon));
        String inst =
                "First, take the cereal box out of the cupboard.\n" +
                        "Next, open the box and hold sides with both hands for safety.\n" +
                        "Then, take an empty bowl and hold the open box over it, tilting " +
                        "until that sweet goodness falls out.\n"+
                        "Lastly, once the bowl is full of cereal, fill in the remaining " +
                        "space with milk.\nStir with spoon and enjoy!";
        Recipe C = new Recipe("World's Famous Cereal!", ingr, inst, 3, 1);
        C.ID("00000001");

        FoodInventory ingr1 = new FoodInventory();
        ingr1.add(new Food("Top Ramen packet", 1, Units.item));
        ingr1.add(new Food("water", 3, Units.cup));
        ingr1.add(new Food("flavor packet", 1, Units.item));
        String inst1 =
                "First, Boil the water in a pot\n" +
                        "Next, throw that noodle crap in with the water for 3 min\n" +
                        "Then, poor the salt paket in.\n" +
                        "Put in a bowl and try to enjoy being poor.";
        Recipe R = new Recipe("Ramen Noodle", ingr1, inst1, 7, 1);
        R.ID("00000002");
        cookbook.add(C);
        cookbook.add(R);
    }
}
