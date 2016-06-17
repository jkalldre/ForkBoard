package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/16/2016.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.io.*;   // for File, FileReader, BufferedReader, and exceptions
import java.util.*; // for List, and ArrayList

public class RecipeLogHandler implements DataHandler {
    public RecipeLog cookbook;

    public RecipeLogHandler() {
        cookbook = new RecipeLog();
    }

    public void load() {
        String dir = "RecipeBook/";
        File[] files = new File(dir).listFiles();
        for (File f : files) {
            if (f.getName().substring(8).equals(".recipe"))
                doFile(f.getName());
        }
    }

    public void save(Context context) {
        File file = new File("RecipeBook/");
        if (!file.exists()) { file.mkdir(); }

        for (Recipe recipe : cookbook.getRecipes()) {
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput("RecipeBook/" + recipe.ID() + ".recipe",
                        Context.MODE_PRIVATE);
                if (recipe.ID().equals("00000001")){
                    String prnt =
                        "@@@ID 00000001\n" +
                        "@NAME Ramen Noodle\n" +
                        "@INGS\n" +
                        "@INGR Top Ramen packet\n" +
                        "@ICNT 1\n" +
                        "@IUNT item\n" +
                        "@INGR water\n" +
                        "@ICNT 3\n" +
                        "@IUNT cup\n" +
                        "@INGR flavor packet\n" +
                        "@ICNT 1\n" +
                        "@IUNT item\n" +
                        "@INGE\n" +
                        "@INSS\n" +
                        "First, Boil the water in a pot.\n" +
                        "Next, throw that noodle crap in with the water for 3 min.\n" +
                        "Then, poor the salt paket in.\n" +
                        "Put in a bowl and try to enjoy being poor.\n" +
                        "@INSE\n" +
                        "@END! 00000001\n";
                    outputStream.write(prnt.getBytes());
                    outputStream.close();
                }
                if (recipe.ID().equals("00000002")){
                    String prnt =
                            "@@@ID 00000001\n" +
                                    "@NAME Ramen Noodle\n" +
                                    "@INGS\n" +
                                    "@INGR Top Ramen packet\n" +
                                    "@ICNT 1\n" +
                                    "@IUNT item\n" +
                                    "@INGR water\n" +
                                    "@ICNT 3\n" +
                                    "@IUNT cup\n" +
                                    "@INGR flavor packet\n" +
                                    "@ICNT 1\n" +
                                    "@IUNT item\n" +
                                    "@INGE\n" +
                                    "@INSS\n" +
                                    "First, Boil the water in a pot.\n" +
                                    "Next, throw that noodle crap in with the water for 3 min.\n" +
                                    "Then, poor the salt paket in.\n" +
                                    "Put in a bowl and try to enjoy being poor.\n" +
                                    "@INSE\n" +
                                    "@END! 00000001\n";
                    outputStream.write(prnt.getBytes());
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Object... params){

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void doFile(String filename) {
        if (okFile(filename)) {
            try(BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
                String ID = "";
                String NAME = "";
                FoodInventory INGREDIENTS = new FoodInventory();
                String INSTRUCTIONS = "";
                String line = "";
                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@@@ID")) {
                    ID = line.substring(6);
                }
                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@NAME")) {
                    NAME = line.substring(6);
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@INGS")) {
                    while (!line.substring(0,5).equals("@INGE")) {
                        if ( (line = buffer.readLine()) != null
                                && line.substring(0,5).equals("@INGR")) {
                            String foodName = line.substring(6);
                            int amount = 0;
                            // TODO: String units = "";
                            if ( (line = buffer.readLine()) != null
                                    && line.substring(0,5).equals("@ICNT")) {
                                amount = Integer.parseInt(line.substring(6));
                            }
                            INGREDIENTS.add(new Food(foodName, amount));
                        }
                    }
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@INSS")) {
                    while (!line.substring(0,5).equals("@INSE")) {
                        if ( (line = buffer.readLine()) != null
                                && !line.substring(0,5).equals("@INSE")) {
                            INSTRUCTIONS += line;
                        }
                    }
                }

                if ( (line = buffer.readLine()) != null
                        && line.substring(0,5).equals("@END!")) {
                    Recipe item = new Recipe(NAME, INGREDIENTS, INSTRUCTIONS);
                    item.ID(ID);
                    cookbook.add(item);
                }
            }
            catch(Exception ex) {
                System.out.println("Error with '" + filename + "'");
                System.exit(-98);
            }
        }
        else {
            System.out.println("Incorrect location for '" + filename + "'");
            System.exit(-99);
        }
    }

    private Boolean okFile(String fname) {
        File test = new File(fname);
        if(test.exists() && test.canRead())
            return true;
        else
            return false;
    }

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
