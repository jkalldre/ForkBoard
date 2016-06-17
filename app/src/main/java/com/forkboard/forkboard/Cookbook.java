package com.forkboard.forkboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Cookbook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook__book);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        tb.setTitle("Cookbook");
        ListView list = (ListView) findViewById(R.id.listView);
        RecipeLogHandler handler = new RecipeLogHandler();
        handler.load();
        RecipeLog cookbook = handler.cookbook;

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cookbook.recipeList()));
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

    public void toRecipeInput(View v){
        Intent intent = new Intent(this,Recipe_Input.class);
        startActivity(intent);
    }

}
