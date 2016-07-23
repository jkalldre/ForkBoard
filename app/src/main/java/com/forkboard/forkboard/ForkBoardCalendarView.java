package com.forkboard.forkboard;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Handler;

public class ForkBoardCalendarView extends AppCompatActivity {

    Intent intentToDay = null;

    private Calendar month;
    private Calendar focusDay;
    private CalendarAdapter adapter;
    TextView lastPressed = null;
    RecipeLogHandler handler = new RecipeLogHandler(this);
    GridView gridview;
    Day dayObject;
    String file;
    TextView mealchoice1;
    TextView mealchoice2;
    TextView mealchoice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork_board_calendar_view);
        Toolbar tb =  (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        intentToDay     = new Intent(this,Day_View.class);
        gridview        = (GridView) findViewById(R.id.gridview);
        month           = Calendar.getInstance();
        focusDay        = Calendar.getInstance();
        adapter         = new CalendarAdapter(this, month, null);
        TextView title  = (TextView) findViewById(R.id.title);
        mealchoice1     = (TextView)findViewById(R.id.meal_b);
        mealchoice2     = (TextView)findViewById(R.id.meal_l);
        mealchoice3     = (TextView)findViewById(R.id.meal_d);

        // Populate calendar with current month
        gridview.setAdapter(adapter);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        // pre-load the current day's meals and auto-select today's date.
        handler.load();
        Date day = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        String today = sdf.format(day);
        int[] date = Format.parseDate(today);
        file = Format.generateDateString(date[0], date[1]-1, date[2]);
        loadDay();

        // set the onClick for navigating between months
        ImageView previous  = (ImageView) findViewById(R.id.prev);
        ImageView next  = (ImageView) findViewById(R.id.nex);
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

        // set the onClick for each day of the calendar.
        final Context that = this;
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selected_day = parent.getItemAtPosition(position).toString();
                if (selected_day != null && !selected_day.equals("")) {
                    intentToDay.putExtra("Day", Integer.parseInt(selected_day));
                    intentToDay.putExtra("Month", month.get(Calendar.MONTH));
                    intentToDay.putExtra("Year", month.get(Calendar.YEAR));
                    focusDay.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH), Integer.parseInt(selected_day));
                    adapter = new CalendarAdapter(that, month, focusDay);
                    gridview.setAdapter(adapter);

                    file = Format.generateDateString(
                            month.get(Calendar.YEAR),
                            month.get(Calendar.MONTH),
                            Integer.parseInt(selected_day));
                    loadDay();
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
        focusDay.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH), 99);
        adapter = new CalendarAdapter(this, month, focusDay);
        gridview.setAdapter(adapter);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
        mealchoice1.setText("");
        mealchoice2.setText("");
        mealchoice3.setText("");
    }

    /**
     * This function takes the user to Cookbook_Selecter,
     * a class that is uneditable where they can select their
     * desired recipes.
     *
     * @param v button clicked
     */
    public void goToCookbook(View v){
        switch (((TableRow) v).getId()) {
            case R.id.b : lastPressed = (TextView)findViewById(R.id.meal_b); break;
            case R.id.l : lastPressed = (TextView)findViewById(R.id.meal_l); break;
            case R.id.d : lastPressed = (TextView)findViewById(R.id.meal_d); break;
        }
        Intent intent = new Intent(this, Cookbook_Selecter.class);
        intent.putExtra("Current Meal", lastPressed.getText());
        startActivityForResult(intent, 001);
    }

    /**
     * onActivityResult will change the selected meal to the one of the
     * users choice, which will be saved to a SQLite table.
     *
     * @param requestCode sent code
     * @param resultCode returned code
     * @param data holds the data retrieved from child activity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String tmp = null;
        if (lastPressed != null){
            tmp = data.getStringExtra("Recipe Name");
            if(tmp != null) {
                lastPressed.setText(tmp);
                setChanges();
            }
            else
                lastPressed.setText("(No Meal Selected)");
        }
    }

    /**
     * Save the changes made to the day file. Called every time user makes a move
     */
    public void setChanges() {
        dayObject.breakfast = handler.cookbook.get(mealchoice1.getText().toString());
        dayObject.lunch     = handler.cookbook.get(mealchoice2.getText().toString());
        dayObject.dinner    = handler.cookbook.get(mealchoice3.getText().toString());
        dayObject.save(file);
    }

    /**
     * loads the current day and populates the meal board.
     */
    private void loadDay() {
        dayObject = new Day(this);
        dayObject.load(file);
        mealchoice1.setText(dayObject.breakfast.name());
        mealchoice2.setText(dayObject.lunch.name());
        mealchoice3.setText(dayObject.dinner.name());
    }
}
