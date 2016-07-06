package com.forkboard.forkboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Activity for creating a shopping list
 *
 * This activity is used to display the shopping list and add items to the shopping list.
 *
 * @author Janine
 */

public class ShoppingListWeek extends AppCompatActivity {

    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;
    private ArrayAdapter<String> mAdapter;
    private RecipeLogHandler handler = new RecipeLogHandler(this);
    private FoodInventory    allFood = new FoodInventory();
    private ArrayAdapter<String> adapter;
    private List<String> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_week);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        mShoppingList = (ListView) findViewById(R.id.shopping_listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        generateList(new GregorianCalendar(), new GregorianCalendar());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodList);


        handler.load();
        /*for (Recipe recipe : handler.cookbook.getRecipes()) {
            for (Food food : recipe.ingredients().toArray()){
                allFood.add(food);
            }
        }*/
       // foodList = allFood.ingredientList();
        mShoppingList.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        new ActivityChanger().changeActivity(item, this);
        return true;
    }

    public void generateList(Calendar start, Calendar end){
        FoodInventory newIngrList = new FoodInventory();
        Day dayObject = null;
        Calendar tstart = new GregorianCalendar(2016, 6, 2);
        Calendar tend   = new GregorianCalendar(2016, 6, 3);
        List<String> listDates = Misc.generateDateList(tstart, tend);
        for (String date : listDates) {
            dayObject = new Day(this);
            dayObject.load(date);
            if(!dayObject.breakfast.name().equals("(No Meal Selected)"))
                loadFood(dayObject.breakfast, newIngrList);
            if(!dayObject.lunch.name().equals("(No Meal Selected)"))
                loadFood(dayObject.lunch, newIngrList);
            if(!dayObject.dinner.name().equals("(No Meal Selected)"))
                loadFood(dayObject.dinner, newIngrList);

            System.out.println("++++" + "B:" + dayObject.breakfast.name() + " L:"
            + dayObject.lunch.name() + " D:" + dayObject.dinner.name() + "++++");
        }

        foodList = newIngrList.ingredientList();
        adapter.notifyDataSetChanged();
        mShoppingList.setAdapter(adapter);
       // System.out.println("***" + allFood.toString());
        for (String food : foodList)
        System.out.println(food);
    }

    private void loadFood(Recipe r, FoodInventory f) {
        for(Food foodItem : r.ingredients().toArray()) {
            f.add(foodItem);
        }
        System.out.println("---- added " + r.name() + "-----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        generateList(new GregorianCalendar(), new GregorianCalendar());
       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodList);
        adapter.notifyDataSetChanged();
    }
}
