package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/16/2016.
 */
import java.util.List;
import java.util.ArrayList;

public class RecipeLog {
    private List<Recipe> cookbook;

    public RecipeLog() {
        cookbook = new ArrayList<Recipe>();
    }

    public void add(Recipe recip) {
        if (recip == null)
            System.out.println("NULL ITEM! CANNOT BE ADDED TO COOKBOOK");
        else
            cookbook.add(recip);
    }

    public void remove(Recipe r) {
        for (Recipe rec : cookbook) {
            if (rec.name().equals(r.name())) {
                cookbook.remove(rec);
            }
        }
    }

    public Recipe get(String name) {
        for (Recipe rec : cookbook) {
            if (rec.name().equals(name)) {
                return rec;
            }
        }
        return null;
    }

    public Recipe get(int ID) {
        for (Recipe rec : cookbook) {
            if (rec.ID() == ID) {
                return rec;
            }
        }
        return null;
    }

    public Recipe get(Recipe r) {
        for (Recipe rec : cookbook) {
            if (rec == r) {
                return rec;
            }
        }
        return null;
    }

    public String[] recipeList() {
        List<String> ret = new ArrayList<>();

        for (Recipe rec : cookbook) {
            ret.add(rec.name());
        }

        return ret.toArray(new String[ret.size()]);
    }
}
