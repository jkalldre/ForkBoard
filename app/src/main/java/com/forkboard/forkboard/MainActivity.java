package com.forkboard.forkboard;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
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

    /**
     * Handles button input to change activity
     * @param v button clicked
     */
    public void changeActivity(View v){
        // for MainActivity buttons
        Intent intent = null;
        if(v.getId() == R.id.buttonToInfo){
        }
        if(v.getId() == R.id.buttonToCalendar){
            intent = new Intent(this, Calendar_View.class);
        }
        if(v.getId() == R.id.buttonToCookbook){
            intent = new Intent(this, Cookbook.class);
        }
        if(v.getId() == R.id.buttonToCalc){
            intent = new Intent(this, Conversion_Calculator.class);
        }
        if(v.getId() == R.id.buttonToShopping){
            intent = new Intent(this, ShoppingListWeek.class);
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}