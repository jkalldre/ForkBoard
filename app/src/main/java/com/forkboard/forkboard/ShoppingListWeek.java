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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_week);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        handler.load();
        for (Recipe recipe : handler.cookbook.getRecipes()) {
            for (Food food : recipe.ingredients().toArray()){
                allFood.add(food);
            }
        }

        mShoppingList = (ListView) findViewById(R.id.shopping_listView);
        mItemEdit = (EditText) findViewById(R.id.item_editText);
        mAddButton = (Button) findViewById(R.id.add_button);

        //mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, allFood.ingredientList());
        mShoppingList.setAdapter(adapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = mItemEdit.getText().toString();
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
                mItemEdit.setText("");
            }
        });

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
}
