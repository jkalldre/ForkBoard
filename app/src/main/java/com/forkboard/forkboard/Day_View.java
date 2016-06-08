package com.forkboard.forkboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Day_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day__view);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        String date = "";
        TextView d = (TextView) findViewById(R.id.textView);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        date += months[getIntent().getIntExtra("Month", 0)] + " " + getIntent().getIntExtra("Day",0) + ", " + getIntent().getIntExtra("Year",0);
        d.setText(date);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        Intent intent = null;
        if(item.getTitle().equals("Main Menu")){
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
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

    public void goToCookbook(View v){
        Intent intent = new Intent(this,Cookbook.class);
        startActivity(intent);
    }
}
