package com.forkboard.forkboard;

import android.app.Activity;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Recipe_Input extends AppCompatActivity {

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
}
