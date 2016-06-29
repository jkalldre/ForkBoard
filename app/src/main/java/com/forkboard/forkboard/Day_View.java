package com.forkboard.forkboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.Bind;

public class Day_View extends AppCompatActivity {
    @Bind(R.id.breakfast) LinearLayout meal1;
    @Bind(R.id.lunch)     LinearLayout meal2;
    @Bind(R.id.dinner)    LinearLayout meal3;
    TextView lastPressed = null;
    int month;// = getIntent().getIntExtra("Month", 0);
    int day;//   = getIntent().getIntExtra("Day"  , 0);
    int year;//  = getIntent().getIntExtra("Year" , 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day__view);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        String date = "";
        TextView d = (TextView) findViewById(R.id.textView);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        month = getIntent().getIntExtra("Month", 0);
        day   = getIntent().getIntExtra("Day"  , 0);
        year  = getIntent().getIntExtra("Year" , 0);
        date += months[month] + " " + day + ", " + year;
        d.setText(date);
        // set up custom layouts
        LinearLayout breakf = (LinearLayout)findViewById(R.id.breakfast);
        LinearLayout lunch  = (LinearLayout)findViewById(R.id.lunch);
        LinearLayout dinner = (LinearLayout)findViewById(R.id.dinner);
        TextView meal1 = (TextView)breakf.findViewById(R.id.meal);
        TextView meal2 = (TextView)lunch .findViewById(R.id.meal);
        TextView meal3 = (TextView)dinner.findViewById(R.id.meal);
        meal1.setText("Breakfast");
        meal2.setText("Lunch");
        meal3.setText("Dinner");
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
        lastPressed = (TextView)v.findViewById(R.id.mealchoice);
        Intent intent = new Intent(this,Cookbook_Selecter.class);
        startActivityForResult(intent, 001);
       // startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String tmp = null;
        if (lastPressed != null){
            System.out.println("NULLLLLL");
            if (data.getStringExtra("Recipe Name") != null)
              tmp = data.getStringExtra("Recipe Name");
            if(tmp != null)
                lastPressed.setText(data.getStringExtra("Recipe Name"));
            else
                lastPressed.setText("(No Meal Selected)");
        }
    }
    /*
    public void onResume(){
        super.onResume();
        if(lastPressed != null) {
            lastPressed.setText(getIntent().getStringExtra("Recipe Name"));
        }
    }*/
    public void nextDay(View v) {
        Intent intent = new Intent(this, Day_View.class);
        //intent.putExtra()
        startActivity(intent);
    }

    public void prevDay(View v) {

    }
}
