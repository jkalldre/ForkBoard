package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
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
    String        _ID;

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

    public void name(String name)                      { _name         = name;         }
    public void instructions(String instructions)      { _instructions = instructions; }
    public void ingredients(FoodInventory ingredients) { _ingredients  = ingredients;  }
    public void cookTime(int cookTime)                 { _cookTime     = cookTime;     }
    public void serveCount(int serveCount)             { _serveCount   = serveCount;   }
    public void ID(String ID)                          { _ID = ID;                     }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    public String name()               { return _name;         }
    public String instructions()       { return _instructions; }
    public FoodInventory ingredients() { return _ingredients;  }
    public int cookTime()              { return _cookTime;     }
    public int serveCount()            { return _serveCount;   }
    public String ID()                 { return _ID;           }

    //---------------------------------------------------------------
    // Object Class overrides
    //---------------------------------------------------------------

    public String toString() {
        return getScaledOutputString(50);
    }

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