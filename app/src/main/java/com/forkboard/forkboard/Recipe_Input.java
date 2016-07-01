package com.forkboard.forkboard;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * This class is used to add a new recipe to the RecipeLog(Cookbook)
 * Collecting user input for a new recipe or collecting the info from
 * an old recipe to provide the means to edit it.
 */
public class Recipe_Input extends AppCompatActivity {
    @Bind(R.id.recipeName)  EditText recipeName;
    @Bind(R.id.cookTime)    EditText cookTime;
    @Bind(R.id.servingSize) EditText servingSize;
    @Bind(R.id.directions)  EditText directions;
    @Bind(R.id.ingredients) ListView lv;

    private FoodInventory        ingredients = new FoodInventory();
    private ArrayList<String>    foodList    = new ArrayList<String>();
    private ArrayAdapter<String> adapter1;
    private Recipe               recipe      = null;
    private RecipeLogHandler     handler;


    @Override
    /**
     * onCreate here will fill out all the fields if an existing recipe
     * is given otherwise it will only setup the layout for the activity.
     *
     * @param Bundle savedInstanceState to recover old activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__input);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        TextView recipeName  = (TextView)findViewById(R.id.recipeName );
        TextView cookTime    = (TextView)findViewById(R.id.cookTime   );
        TextView servingSize = (TextView)findViewById(R.id.servingSize);
        TextView directions  = (TextView)findViewById(R.id.directions );
        ListView lv          = (ListView)findViewById(R.id.ingredients);
        handler = new RecipeLogHandler(this);
        handler.load();

        if (!getIntent().getStringExtra("selected").equals("")) {

            recipe = handler.cookbook.get(getIntent().getStringExtra("selected"));
            foodList = (ArrayList<String>)recipe.ingredients().ingredientList();
            recipeName .setText(recipe.name());
            cookTime   .setText("" + recipe.cookTime());
            servingSize.setText("" + recipe.serveCount());
            directions .setText(recipe.instructions());
            ingredients = recipe.ingredients();
        }

        foodList = (ArrayList<String>)ingredients.ingredientList();
        // set up adapter to save ingredients
        lv       = (ListView)findViewById(R.id.ingredients);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, foodList);
        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //this will be executed when user selects an item from listview to be edited/delted
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println((String)parent.getItemAtPosition(position));
                onAddIngredient(view, "edit", (String)parent.getItemAtPosition(position));
            }
        });
    }


    @Override
    // Set up menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        new ActivityChanger().changeActivity(item,this);
        return true;
    }

    /**
     * Simple function to grab the index of the selectable
     * item.
     *
     * @param spinner spinner object you want the index from
     * @param myString name of selection you want index for
     * @return
     */
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    /**
     * This is a overload of onAddIngredient used to update and edit existing
     * recipes.
     *
     * @param v the listview being edited
     * @param edit if the function will be editing
     * @param food the name of the recipe to be edited
     */
    public void onAddIngredient(View v, final String edit, final String food){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder    .setTitle("Enter the Quantity, Units, and Name");
        LinearLayout layout = new LinearLayout(this);
        layout     .setOrientation(LinearLayout.VERTICAL);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._units);

        // Set up the inputs
        final EditText quantity     = new EditText(this);
        final Spinner unitsDropdown = new Spinner (this);
        final EditText name         = new EditText(this);

        // add tooltips
        quantity.setHint("quantity");
        name    .setHint("name"    );

        // add inputs to new layout
        layout       .addView(quantity     );
        layout       .addView(unitsDropdown);
        layout       .addView(name         );
        unitsDropdown.setAdapter(adapter   );

        // Specify the type of input expected
        quantity.setInputType(InputType.TYPE_CLASS_TEXT);
        name    .setInputType(InputType.TYPE_CLASS_TEXT);
        builder .setView(layout                        );

        if (edit.equals("edit")) {
            Food editFood = ingredients.get(food);
            if (editFood != null) {
                quantity.setText("" + editFood.quantity());
                name.setText(editFood.type());
                unitsDropdown.setSelection(getIndex(unitsDropdown, editFood.units().toString()));

            }
            else
                System.out.println("editFood is null");
        }

        // run if ok is clicked
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                ListView lv1 = (ListView)findViewById(R.id.ingredients);
                Food newFood = new Food();
                if (edit.equals("edit"))
                    newFood = ingredients.get(food);

                foodList   .remove(newFood.toString());
                newFood    .quantity(Misc.processUserQuantityInput(quantity.getText().toString()));
                newFood    .units(Units.fromString(unitsDropdown.getSelectedItem().toString()));
                newFood    .type(Format.capitalizeFully(name.getText().toString()));

                ingredients.replace(newFood);
                foodList.add(newFood.toString());

                adapter1.notifyDataSetChanged();
            }
        });
        // run if cancel is clicked
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Food newFood = ingredients.get(food);
                ingredients.remove(newFood);
                foodList.remove(newFood.toString());
                adapter1.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        //adapter1.notifyDataSetChanged();
        builder.show();
    }

    /**
     * onAddIngredient is used to add an ingredient to a new or existing recipe
     *
     * @param v button pressed
     */
    public void onAddIngredient(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder    .setTitle("Enter the Quantity, Units, and Name");
        LinearLayout layout = new LinearLayout(this);
        layout     .setOrientation(LinearLayout.VERTICAL);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._units);

        // Set up the inputs
        final EditText quantity     = new EditText(this);
        final Spinner unitsDropdown = new Spinner (this);
        final EditText name         = new EditText(this);

        // add tooltips
        quantity.setHint("quantity");
        name    .setHint("name"    );

        // add inputs to new layout
        layout       .addView(quantity     );
        layout       .addView(unitsDropdown);
        layout       .addView(name         );
        unitsDropdown.setAdapter(adapter   );

        // Specify the type of input expected
        quantity.setInputType(InputType.TYPE_CLASS_TEXT);
        name    .setInputType(InputType.TYPE_CLASS_TEXT);
        builder .setView(layout                        );

        // run if ok is clicked
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                ListView lv1 = (ListView)findViewById(R.id.ingredients);
                Food newFood = new Food();
                newFood    .quantity(Misc.processUserQuantityInput(quantity.getText().toString()));
                newFood    .units(Units.fromString(unitsDropdown.getSelectedItem().toString()));
                newFood    .type(Format.capitalizeFully(name.getText().toString()));

                ingredients.replace(newFood);

                foodList.add(newFood.toString());
                adapter1.notifyDataSetChanged( );
                lv1     .setAdapter(adapter1   );
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

    /**
     * onSubmit collects all given user info and inserts it into a recipe
     * object then saves it to the RecipeLog(Cookbook). Also used to update
     * pre-existing recipes.
     *
     * @param v receives a view
     */
    public void onSubmit(View v){
        EditText rName     = (EditText)findViewById(R.id.recipeName );
        EditText rInstruc  = (EditText)findViewById(R.id.directions );
        EditText rCookTime = (EditText)findViewById(R.id.cookTime   );
        EditText rServings = (EditText)findViewById(R.id.servingSize);
        int cooktime = 0;
        Recipe recipe1 = null;
        if (!rCookTime.getText().toString().trim().equals("")) {
            if (Float.parseFloat(rCookTime.getText().toString()) % 1 < 1) {
                Log.w(Warnings.POTENTIAL_DATA_LOSS, "Cook Times should be rounded to the nearest whole!");
            }
            float fcooktime = Float.parseFloat(rCookTime.getText().toString());
            cooktime = (int) fcooktime;
        }


        if (rName.getText().toString().trim().equals("")
                || rCookTime.getText().toString().trim().equals("")
                || rServings.getText().toString().trim().equals("")
                || rInstruc.getText().toString().trim().equals("")) {

            if (rName.getText().toString().trim().equals(""))
                rName.setError("Recipe Name is Required!");
            if (rCookTime.getText().toString().trim().equals(""))
                rCookTime.setError("Cook Time is Required!");
            if (rServings.getText().toString().trim().equals(""))
                rServings.setError("Serving Size is Required!");
            if (rInstruc.getText().toString().trim().equals(""))
                rInstruc.setError("Directions are Required!");
        }
        else {
        recipe1 = new Recipe(Format.capitalizeFully(rName.getText().toString()), ingredients,
                rInstruc.getText().toString(), cooktime,//Integer.parseInt(rCookTime.getText().toString()),
                Integer.parseInt(rServings.getText().toString()));

        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        int id = pref.getInt("recipe_id_counter", 1);

        pref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        recipe1.ID(Misc.generateIDfromInt(id));
        edit.putInt("recipe_id_counter", ++id);
        edit.commit();

        System .out.print(recipe1.toString());



            Intent intent = new Intent(this, Cookbook.class);
            if (recipe == null) {
                handler.cookbook.add(recipe1);
                handler.save();
                intent.putExtra("Recipe Name", recipe1.name());
                setResult(002, intent);
            }
            if (recipe != null) {
                recipe1.ID(recipe.ID());
                handler.update(recipe1);
                setResult(003, intent);
            }
            finish();
        }
    }
}
