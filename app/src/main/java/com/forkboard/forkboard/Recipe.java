package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 * Recipe class is used to store all of the properties of a recipe. It holds
 * all of the ingredients, instructions, its name, cooktime, servecount, and
 * a unique ID that is used to create its .recipe filename.
 */
public class Recipe {
    //---------------------------------------------------------------
    // States
    //---------------------------------------------------------------

    FoodInventory _ingredients;    // our list of ingredients
    String        _instructions;   // the instructions for preparing
    String        _name;           // title of the recipe
    int           _cookTime;       // how long it takes to prepare (in minutes)
    int           _serveCount;     // how many it feeds
    String        _ID = null;

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------

    public Recipe() {
        _ingredients  = new FoodInventory();
        _instructions = "";
        _name         = "ForkBoard Recipe";
        _cookTime     = 15;
        _serveCount   = 1;
    }

    public Recipe(String name, FoodInventory ingr, String inst) {
        _ingredients  = ingr;
        _instructions = inst;
        _name         = name;
        _cookTime     = 15;
        _serveCount   = 1;
    }

    public Recipe(String name, FoodInventory ingr, String inst, int cook, int serv) {
        _ingredients  = ingr;
        _instructions = inst;
        _name         = name;
        _cookTime     = cook;
        _serveCount   = serv;
    }

    //---------------------------------------------------------------
    // Modifiers
    //---------------------------------------------------------------

    /**
     * SET the name
     * @param name sting we set the name too
     */
    public void name(String name)                      { _name         = name;         }

    /**
     * SET the instructions
     * @param instructions the string containing our instructions
     */
    public void instructions(String instructions)      { _instructions = instructions; }

    /**
     * SET the ingredients
     * @param ingredients FoodInventory holding the ingredients
     */
    public void ingredients(FoodInventory ingredients) { _ingredients  = ingredients;  }

    /**
     * SET the cook time (in minutes)
     * @param cookTime the new cooktime
     */
    public void cookTime(int cookTime)                 { _cookTime     = cookTime;     }

    /**
     * SET the count for number of people Recipe serves
     * @param serveCount how many people
     */
    public void serveCount(int serveCount)             { _serveCount   = serveCount;   }

    /**
     * SET the ID string (form: XXXXXXXX)
     * @param ID the new ID
     */
    public void ID(String ID)                          { _ID = ID;                     }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    /**
     * GET the name
     * @return the name string
     */
    public String name()               { return _name;         }

    /**
     * GET the instructions
     * @return instructions string
     */
    public String instructions()       { return _instructions; }

    /**
     * GET the Ingredients
     * @return ingredients FoodInventory
     */
    public FoodInventory ingredients() { return _ingredients;  }

    /**
     * GET the cook time (in minutes)
     * @return the time in minutes
     */
    public int cookTime()              { return _cookTime;     }

    /**
     * GET the count for how many people Recipe serves
     * @return the serve count
     */
    public int serveCount()            { return _serveCount;   }

    /**
     * GET the ID
     * @return the ID string
     */
    public String ID()                 { return _ID;           }

    //---------------------------------------------------------------
    // Object Class overrides
    //---------------------------------------------------------------

    /**
     * the classic toString() override
     * @return a nicely formatted string
     */
    public String toString() {
        return getScaledOutputString(50);
    }

    /**
     * Format the recipe to print out nicely
     * @param width how wide we want the string to print out (in chars)
     * @return the nicely formatted string
     */
    public String getScaledOutputString(int width) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < width; i++) output.append("=");
        output.append("\n");
        output.append(Format.centerOnLine(_name, width) + "\n");
        for (int i = 0; i < width; i++) output.append("-");
        output.append("\n");
        String prep = "Prep: " + _cookTime +" min.  ";
        String serv = "Serves: " + _serveCount;
        output.append(prep);
        if (serv.length() <= width - prep.length())
            output.append(Format.rightJustifyOnLine(serv, width - prep.length()));
        else {
            output.append("\n" + serv);
        }
        output.append("\n");
        for (int i = 0; i < width; i++) output.append("-");
        output.append("\n");
        output.append(_ingredients.toString());
        for (int i = 0; i < width; i++) output.append("-");
        output.append("\n");
        output.append(Format.marginize(_instructions, width) + "\n");
        for (int i = 0; i < width; i++) output.append("=");
        return output.toString();
    }

    /**
     * Format the recipe into a string to print to a file
     * @return the file formatted string
     */
    public String toFileString() {
        String print = "";
        print = "@@@ID " + _ID + "\n" +
                "@NAME " + _name + "\n" +
                "@PREP " + _cookTime + "\n" +
                "@SERV " + _serveCount + "\n" +
                "@INGS\n";
        for (Food ingr : _ingredients.toArray()) {
            print += "@INGR " + ingr.type() + "\n" +
                     "@ICNT " + ingr.quantity() + "\n" +
                     "@IUNT " + ingr.units() + "\n";
        }
        print += "@INGE\n" +
                 "@INSS\n" +
                 _instructions + "\n" +
                 "@INSE\n" +
                 "@END! "  + _ID + "\n";
        return print;
    }
}