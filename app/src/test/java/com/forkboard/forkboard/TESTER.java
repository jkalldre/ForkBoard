package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 */
class TEST_Meal {
    public static void main(String[] args) {
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
                        "Then, poor the salt paket in.\n " +
                        "Put in a bowl and try to enjoy being poor.";
        Recipe R = new Recipe("Ramen Noodle", ingr1, inst1, 7, 1);

        Meal meal = new Meal(MealTime.Breakfast);
        meal.add(C);
        meal.add(R);
        System.out.println(meal);
    }
}


class TEST_Recipe {
    public static void main(String[] args) {
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
        Recipe R = new Recipe("World's Famous Cereal!", ingr, inst, 3, 1);
        System.out.println(R);

    }
}


class TEST_FoodInventory {
    public static void main(String[] args) {
        FoodInventory inv = new FoodInventory();

        System.out.println(inv);
        inv.add(new Food("pear", 10));
        System.out.println(inv);
        inv.add(new Food("apple", 10));
        System.out.println(inv);
        inv.add(new Food("pear", 13));
        System.out.println(inv);
        inv.add(new Food("orange", 10));
        System.out.println(inv);
        inv.subtract(new Food("apple", 5));
        System.out.println(inv);
        //inv.subtract(new Food("dog", 5)); // causes NullPointerException
        System.out.println(inv);
        System.out.println("Found: " + inv.find("apple") + "\n");
        System.out.println("inventory empty?: " + inv.empty() + "\n");
        inv.clear();
        System.out.println(inv);
        System.out.println("inventory empty?: " + inv.empty() + "\n");


        FoodInventory inv2 = new FoodInventory();
        inv.add(new Food("pear", 10));
        inv.add(new Food("apple", 10));
        inv.add(new Food("orange", 10));
        inv.add(new Food("mango", 10));

        inv2.add(new Food("banana", 10));
        inv2.add(new Food("orange", 10));
        inv2.add(new Food("pear", 10));
        inv2.add(new Food("grape", 10));
        System.out.println("Find similar between: [pear, apple, orange, mango] and [banana, orange, pear, grape]");
        for (Food item : inv.findSimilar(inv2)){
            System.out.println(item);
        }
        System.out.println("Find different between: [pear, apple, orange, mango] and [banana, orange, pear, grape]");
        for (Food item : inv.findDifferent(inv2)){
            System.out.println(item);
        }
    }
}


class TEST_Food {
    public static void main(String[] args) {
        Food apple1 = new Food("apple", 25, Units.item);
        Food apple2 = new Food("apple", 15, Units.item);
        Food pear1 = new Food("pear", 15, Units.item);

        System.out.println(apple1);
        System.out.println(apple2);
        System.out.println(pear1);
        System.out.println("------------------------------------------");

        apple1.add(12);
        System.out.println(apple1);
        apple1.add(apple2);
        System.out.println(apple1);
        apple2.subtract(3);
        System.out.println(apple2);
        System.out.println("ran out?: " + apple2.ranOut());
        apple2.subtract(apple1);
        System.out.println(apple2);
        System.out.println("ran out?: " + apple2.ranOut());
        apple2.allowDeficit();
        System.out.println(apple2);
        apple2.subtract(apple1);
        System.out.println(apple2);
        apple2.allowDeficit(false);
        System.out.println(apple2);
        System.out.println("------------------------------------------");

        System.out.println(apple1);
        System.out.println(pear1);
        System.out.println("Pear + Apple");
        System.out.println(apple1);
        System.out.println(pear1);
        System.out.println("Pear - Apple");
        System.out.println(apple1);
        System.out.println(pear1);
        System.out.println("Same Food type: " + pear1.isSameFoodType(apple1));
        System.out.println("------------------------------------------");

    }
}
