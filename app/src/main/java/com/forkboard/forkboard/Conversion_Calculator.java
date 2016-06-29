package com.forkboard.forkboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.util.Log;

public class Conversion_Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion__calculator);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        //String[] arraySpinner = {"tsp","tbsp","cup","pt","oz","g","ml","l"};
        Spinner s = (Spinner) findViewById(R.id.spinner);
        Spinner s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._units);
        s.setAdapter(adapter);
        s2.setAdapter(adapter);
    }

    /**
     * Methods are used to call the toolbar
     *
     * @param menu The Menu creates a menu object
     * @return The resulting is a boolean
     */

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
