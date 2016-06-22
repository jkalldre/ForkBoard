package com.forkboard.forkboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Recipe_Input extends AppCompatActivity {
    // string array for spinner
    private String[] _units = {
            "(no units)",
            Units.teaspoon.toString(), Units.tablespoon.toString(), Units.fluid_ounce.toString(),
            Units.cup.toString(), Units.pint.toString(), Units.quart.toString(), Units.gallon.toString(),
            Units.pound.toString(), Units.ounce.toString(),
            Units.milliliter.toString(), Units.liter.toString(),
            Units.milligram.toString(), Units.gram.toString(), Units.kilogram.toString()};

    //
    private FoodInventory ingredients = new FoodInventory();
    ArrayList<String>     foodList    = new ArrayList<String>();
    ArrayAdapter<String>  adapter1;
    ListView              lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__input);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        // set up adapter to save ingredients
        lv       = (ListView)findViewById(R.id.ingredients);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, foodList);
        lv.setAdapter(adapter1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        new ActivityChanger().changeActivity(item,this);
        return true;
    }

    public void onAddIngredient(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the Quantity, Units, and Name");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, _units);

        // Set up the inputs
        final EditText quantity     = new EditText(this);
        final Spinner unitsDropdown = new Spinner(this);
        final EditText name         = new EditText(this);

        // add tooltips
        quantity.setHint("quantity");
        name    .setHint("name");

        // add inputs to new layout
        layout       .addView(quantity);
        layout       .addView(unitsDropdown);
        layout       .addView(name);
        unitsDropdown.setAdapter(adapter);

        // Specify the type of input expected
        quantity.setInputType(InputType.TYPE_CLASS_TEXT);
        name    .setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(layout);

        // run if ok is clicked
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                Food newFood = new Food();
                newFood.quantity(Integer.parseInt(quantity.getText().toString()));
                newFood.units(Units.fromString(unitsDropdown.getSelectedItem().toString()));
                newFood.type(name.getText().toString());
                ingredients.add(newFood);

                foodList.add(newFood.quantity() + " " + newFood.units() + " " + newFood.type());
                adapter1.notifyDataSetChanged();
                lv.setAdapter(adapter1);
            }
        });
        // run if cancel is clicked
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adapter.notifyDataSetChanged();
        builder.show();
    }

    public void onSubmit(View v){
        EditText rName = (EditText)findViewById(R.id.recipeName);
        EditText rInstruc = (EditText)findViewById(R.id.directions);
        EditText rCookTime = (EditText)findViewById(R.id.cookTime);
        EditText rServings = (EditText)findViewById(R.id.servingSize);

        Recipe recipe = new Recipe(rName.getText().toString(), ingredients,
                rInstruc.getText().toString(), Integer.parseInt(rCookTime.getText().toString()),
                Integer.parseInt(rServings.getText().toString()));

        String id = "00000001";

        recipe.ID(id);
        System.out.print(recipe.toString());
        RecipeLogHandler handler = new RecipeLogHandler(this);
        handler.cookbook.add(recipe);
        handler.save();
        Intent intent = new Intent(this, Cookbook.class);
        startActivity(intent);
    }
}
