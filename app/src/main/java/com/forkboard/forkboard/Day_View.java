package com.forkboard.forkboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;

public class Day_View extends AppCompatActivity {
    @Bind(R.id.breakfast) TextView breakfast;
    @Bind(R.id.lunch)     TextView lunck;
    @Bind(R.id.dinner)    TextView dinner;

    TextView lastPressed = null;
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
        new ActivityChanger().changeActivity(item, this);
        return true;
    }

    public void goToCookbook(View v){
        lastPressed = (TextView)v;
        Intent intent = new Intent(this,Cookbook_Selecter.class);
        startActivityForResult(intent, 001);
       // startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (lastPressed != null){
            lastPressed.setText(data.getStringExtra("Recipe Name"));
        }
    }
    /*
    public void onResume(){
        super.onResume();
        if(lastPressed != null) {
            lastPressed.setText(getIntent().getStringExtra("Recipe Name"));
        }
    }*/
}
