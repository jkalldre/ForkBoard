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

        //stuff janine added
        if(getIntent().getBooleanExtra("loggingIn", true)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        System.out.println(item.getTitle());
        Intent intent = null;
        if(item.getTitle().equals("Main Menu")){
            intent = new Intent(this,MainActivity.class);

            //intent.putExtra("loggingIn", false);
            if(getIntent().getBooleanExtra("loggingIn", false)){
                startActivity(intent);
            }


        }
        if(item.getTitle().equals("Calendar")){
            intent = new Intent(this,Calendar_View.class);
            startActivity(intent);
        }
        if(item.getTitle().equals("Cookbook")){
            intent = new Intent(this,Cookbook.class);
            startActivity(intent);
        }
        return true;
    }

    public void changeActivity(View v){
        Intent intent = null;
        if(v.getId() == R.id.buttonToInfo){
        }
        if(v.getId() == R.id.buttonToCalendar){
            intent = new Intent(this,Calendar_View.class);
        }
        if(v.getId() == R.id.buttonToCookbook){
            intent = new Intent(this,Cookbook.class);
        }
        if(v.getId() == R.id.buttonToShopping){
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}