package com.forkboard.forkboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CalendarView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

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
            startActivity(intent);
        }
        if(item.getTitle().equals("Calendar")){
            intent = new Intent(this,CalendarView.class);
            startActivity(intent);
        }
        return true;
    }
}
