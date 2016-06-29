package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/16/2016.
 * RecipeLog is our cookbook that holds all of the recipes entered
 * by the user. It can be used to remove and retrieve recipes from
 * the cookbook and can be used to display all of the recipe names.
 */
import java.util.List;
import java.util.ArrayList;

public class RecipeLog {
    private List<Recipe> cookbook;

    /**
     * Constructor: Initializes cookbook.
     */
    public RecipeLog() {
        cookbook = new ArrayList<Recipe>();
    }

    /**
     * Add a recipe to the cookbook if not null
     *
     * @param recip recipe desired to add to cookbook
     */
    public void add(Recipe recip) {
        if (recip == null)
            System.out.println("NULL ITEM! CANNOT BE ADDED TO COOKBOOK");
        else
            cookbook.add(recip);
    }

    /**
     * Remove a recipe from the cookbook
     *
     * @param r recipe desired to be removed from cookbook
     */
    public void remove(Recipe r) {
        for (Recipe rec : cookbook) {
            if (rec.name().equals(r.name())) {
                cookbook.remove(rec);
            }
        }
    }

    /**
     * Retrieve a reference to the recipe object
     *
     * @param name string name of desired recipe
     * @return null if no recipe exists by that name
     */
    public Recipe get(String name) {
        for (Recipe rec : cookbook) {
            if (rec.name().equals(name)) {
                return rec;
            }
        }
        return null;
    }

//    public Recipe get(int ID) {
//       for (Recipe rec : cookbook) {
//          if (rec.ID() == ID) {
//             return rec;
//          }
//       }
//       return null;
//    }

    /**
     * Return the names of all the recipes listed in the cookbook
     *
     * @return String array of recipe names.
     */
    public String[] recipeList() {
        List<String> ret = new ArrayList<>();

        for (Recipe rec : cookbook) {
            ret.add(rec.name());
        }

        return ret.toArray(new String[ret.size()]);
    }

    /**
     * Returns a Recipe array of all recipes in cookbook
     *
     * @return Recipe array
     */
    public Recipe[] getRecipes() {
        return cookbook.toArray(new Recipe[cookbook.size()]);
    }
}
