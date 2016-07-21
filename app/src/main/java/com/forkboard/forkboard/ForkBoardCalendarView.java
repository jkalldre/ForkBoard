package com.forkboard.forkboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;
import java.util.logging.Handler;

public class ForkBoardCalendarView extends AppCompatActivity {

    Intent intentToDay = null;

    public Calendar month;
    public CalendarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork_board_calendar_view);
        Toolbar tb =  (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        intentToDay = new Intent(this,Day_View.class);
        month = Calendar.getInstance();
        adapter = new CalendarAdapter(this, month);
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        TextView title  = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        ImageView previous  = (ImageView) findViewById(R.id.prev);
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
                    month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
                } else {
                    month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
                }
                refreshCalendar();
            }
        });

        ImageView next  = (ImageView) findViewById(R.id.nex);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {
                    month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
                } else {
                    month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
                }
                refreshCalendar();

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selected_day = parent.getItemAtPosition(position).toString();
                if (selected_day != null && !selected_day.equals("")) {
                    Log.i("THIS ONE", "DAY ==> " + selected_day);
                    intentToDay.putExtra("Day", Integer.parseInt(selected_day));
                    intentToDay.putExtra("Month", month.get(Calendar.MONTH));
                    intentToDay.putExtra("Year", month.get(Calendar.YEAR));
                    startActivity(intentToDay);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        new ActivityChanger().changeActivity(item, this);
        return true;
    }

    public void refreshCalendar()
    {
        TextView title  = (TextView) findViewById(R.id.title);
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }
}
