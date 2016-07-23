package com.forkboard.forkboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Cookbook_Selecter extends AppCompatActivity {
    // instance variables
    private String curMeal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Standard app startup //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook__selecter);
        Toolbar tb =  (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        // Standard app startup //

        // bind views
        ListView list            = (ListView) findViewById(R.id.listView3);
        RecipeLogHandler handler = new RecipeLogHandler(this);
        handler.load();
        final RecipeLog cookbook = handler.cookbook;
        cookbook.add(new Recipe("(No Meal Selected)", new FoodInventory(),"")); // for empty save
        curMeal = getIntent().getStringExtra("Current Meal");

        // warnings if object is null or cookbook is empty
        if (cookbook.recipeList().length == 1) // we put in one blank item
            Log.i(Warnings.EMPTY_OBJECT, "There is nothing in the cookbook!");

        else if (cookbook == null || cookbook.recipeList() == null || cookbook.recipeList().length == 0)
            Log.w("NULL", "You have a null object");

        else
            list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cookbook.recipeList()));
            // fill listview

        if (list != null) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // retrieve selected item name
                    String recName = (String)parent.getItemAtPosition(position);
                    System.out.print(cookbook.get(recName).toString());
                    Intent intent  = new Intent(getApplicationContext(),Day_View.class);

                    if (recName == null)
                        recName = "(No Meal Selected)";

                    // return selected item to parent activity (Day_View)
                    intent.putExtra("Recipe Name", recName);
                    setResult(001, intent);
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        new ActivityChanger().changeActivity(item, this);
        return true;
    }

    @Override
    public void onBackPressed() {
        // if back is pressed return original recipe
        Intent intent = new Intent(getApplicationContext(), Day_View.class);
        intent.putExtra("Recipe Name", curMeal);
        setResult(001, intent);
        finish();
    }
}
