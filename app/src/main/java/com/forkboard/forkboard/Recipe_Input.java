package com.forkboard.forkboard;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.Spinner;

public class Recipe_Input extends AppCompatActivity {
    private String _quantity = "";
    //private String _units = "";
    private String _type = "";
    private FoodInventory ingredients = new FoodInventory();
    private Food   foodItem    = new Food();

    private String[] _units = {
            Units.teaspoon.toString(), Units.tablespoon.toString(), Units.fluid_ounce.toString(),
            Units.cup.toString(), Units.pint.toString(), Units.quart.toString(), Units.gallon.toString(),
            Units.pound.toString(), Units.ounce.toString(),
            Units.milliliter.toString(), Units.liter.toString(),
            Units.milligram.toString(), Units.gram.toString(), Units.kilogram.toString()};

   // unitsDropdown.setAdapter(adapter);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__input);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        String recip = getIntent().getStringExtra("selected");

        RecipeLogHandler handler = new RecipeLogHandler();
        handler.load();
        RecipeLog cookbook = handler.cookbook;
        Recipe it = cookbook.get(recip);

        if (recip != null) {
            EditText box = (EditText) findViewById(R.id.editText2);
            box.setText(it.toString());
        }
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, _units);
        // Set up the inputs
        final EditText quantity = new EditText(this);
        //final EditText units    = new EditText(this);
        final Spinner unitsDropdown = new Spinner(this);
        final EditText name     = new EditText(this);
        // add tooltips
        quantity.setHint("quantity");
        //units   .setHint("units");
        name    .setHint("name");
        // add inputs to new layout
        layout.addView(quantity);
        layout.addView(unitsDropdown);
        layout.addView(name);
        unitsDropdown.setAdapter(adapter);
        // Specify the type of input expected
        quantity.setInputType(InputType.TYPE_CLASS_TEXT);
        //units   .setInputType(InputType.TYPE_CLASS_TEXT);
        name    .setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(layout);
        // run if ok is clicked
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                foodItem.quantity(Integer.parseInt(quantity.getText().toString()));
                foodItem.units(Units.cup);//units.getText().toString();
                foodItem.type(name.getText().toString());
                ingredients.add(foodItem);
                //ingredients.
                EditText tx = (EditText)findViewById(R.id.editText3);
                tx.setText(foodItem.quantity() + " " + foodItem.units() + " " + foodItem.type());
            }
        });
        // run if cancel is clicked
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
