package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 *
 * @Deprecated Was not needed.
 */
import java.util.List;
import java.util.ArrayList;


public class Meal {
    //---------------------------------------------------------------
    // States
    //---------------------------------------------------------------

    private List<Recipe> _courses;
    private int          _cookTime;
    private MealTime     _mealtime;

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------

    public Meal() {
        _courses  = new ArrayList<Recipe>();
        _cookTime = 0;
        _mealtime = MealTime.Breakfast;
    }

    public Meal(List<Recipe> courses) {
        _courses  = courses;
        _cookTime = 0;
        _mealtime = MealTime.Breakfast;
    }

    public Meal(MealTime mealtime, List<Recipe> courses) {
        _courses  = courses;
        _cookTime = 0;
        _mealtime = mealtime;
    }

    public Meal(MealTime mealtime) {
        _courses  = new ArrayList<Recipe>();
        _cookTime = 0;
        _mealtime = mealtime;
    }

    //---------------------------------------------------------------
    // Modifiers
    //---------------------------------------------------------------

    /**
     * Add recipe to meal
     * @param recp item to be added
     */
    public void add(Recipe recp) {
        _cookTime += recp.cookTime();
        _courses.add(recp);
    }

    /**
     * Remove recipe from meal
     * @param recp Item to be removed
     */
    public void remove(Recipe recp) {
        for (Recipe r : _courses) {
            if (r.name().equals(recp.name())) {
                _cookTime -= r.cookTime();
                _courses.remove(r);
            }
        }
    }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    /**
     * Retrieves Courses in form of a FoodInventory
     * @return FoodInventory
     */
    public FoodInventory totalIngredients() {
        FoodInventory total = new FoodInventory();
        Food[] ingredients;
        for (Recipe course : _courses) {
            ingredients = course.ingredients().toArray();
            for (Food item : ingredients){
                total.add(item);
            }
        }
        return total;
    }

    /**
     * Returns Instructions as String array
     * @return String Array
     */
    public String[] instructions() {
        String[] inst = new String[_courses.size()];
        int i = 0;
        for (Recipe course : _courses) {
            inst[i] = course.instructions();
            i++;
        }
        return inst;
    }

    /**
     * Returns all the recipe names as a String array
     * @return String array
     */
    public String[] recipeNames() {
        String[] names = new String[_courses.size()];
        int i = 0;
        for (Recipe course : _courses) {
            names[i] = course.name();
            i++;
        }
        return names;
    }

    public MealTime mealTime() { return _mealtime; }
    public int      cookTime() { return _cookTime; }

    //---------------------------------------------------------------
    // Object Class overrides
    //---------------------------------------------------------------
    public String toString() {
        String str = "**********************************************\n";

        str += mealTime() + " Meal\n\n";
        str += "----------------------------------------------\n";
        str += "Total Cook Time : " + cookTime() + " min.\n";
        str += "----------------------------------------------\n";
        str += "Ingredients Need:\n" + totalIngredients() + "\n";
        str += "----------------------------------------------\n";

        for (Recipe course : _courses) {
            str += course.name() + " Instructions:\n";
            str += course.instructions() + "\n\n";
        }

        str += "**********************************************";
        return str;
    }
}