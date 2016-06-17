package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/16/2016.
 */
public class RecipeLogHandler implements DataHandler {
    public RecipeLog cookbook;

    public RecipeLogHandler() {
        cookbook = new RecipeLog();
    }

    public void load() {
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
        cookbook.add(C);
        cookbook.add(R);
    }

    public void save() {
        // update SQL
    }

    public void update(Object... params){

    }
}
