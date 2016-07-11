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
import java.util.Calendar;
import java.util.GregorianCalendar;
import butterknife.Bind;

/**
 * Day_View serves as the activity where users can tie their
 * recipes to the calendar and the shopping list will generate
 * the needed ingredients to buy from the selected recipes.
 */
public class Day_View extends AppCompatActivity {
    @Bind(R.id.breakfast) LinearLayout meal1;
    @Bind(R.id.lunch)     LinearLayout meal2;
    @Bind(R.id.dinner)    LinearLayout meal3;

    String[] months = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};

    TextView lastPressed = null;
    Calendar calendar;
    RecipeLogHandler handler = new RecipeLogHandler(this);
    Day dayObject;
    String file;

    TextView mealchoice1;
    TextView mealchoice2;
    TextView mealchoice3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day__view);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        String date = "";
        TextView d = (TextView) findViewById(R.id.textView);
        handler.load();

        int month = getIntent().getIntExtra("Month", 0);
        int day   = getIntent().getIntExtra("Day"  , 0);
        int year  = getIntent().getIntExtra("Year" , 0);
        file = Format.generateDateString(year, month, day);
        dayObject = new Day(this);
        dayObject.load(file);

        calendar = new GregorianCalendar(year, month, day);
        date += months[month] + " " + day + ", " + year;
        d.setText(date);

        // set up custom layouts
        LinearLayout breakf = (LinearLayout)findViewById(R.id.breakfast);
        LinearLayout lunch  = (LinearLayout)findViewById(R.id.lunch);
        LinearLayout dinner = (LinearLayout)findViewById(R.id.dinner);
        TextView meal1 = (TextView)breakf.findViewById(R.id.meal);
        TextView meal2 = (TextView)lunch .findViewById(R.id.meal);
        TextView meal3 = (TextView)dinner.findViewById(R.id.meal);
        mealchoice1 = (TextView)breakf.findViewById(R.id.mealchoice);
        mealchoice2 = (TextView)lunch.findViewById(R.id.mealchoice);
        mealchoice3 = (TextView)dinner.findViewById(R.id.mealchoice);
        meal1.setText("Breakfast");
        meal2.setText("Lunch");
        meal3.setText("Dinner");
        mealchoice1.setText(dayObject.breakfast.name());
        mealchoice2.setText(dayObject.lunch.name());
        mealchoice3.setText(dayObject.dinner.name());
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
     * This function takes the user to Cookbook_Selecter,
     * a class that is uneditable where they can select their
     * desired recipes.
     *
     * @param v button clicked
     */
    public void goToCookbook(View v){
        lastPressed = (TextView)v.findViewById(R.id.mealchoice);
        TextView warning = (TextView)findViewById(R.id.warning);
        if (handler.cookbook.recipeList().length <= 0) {
            warning.setError("");
            warning.setVisibility(View.VISIBLE);
            lastPressed.setError("Add a Recipe to Cookbook First!");
        }
        else {
            warning.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, Cookbook_Selecter.class);
            intent.putExtra("Current Meal", lastPressed.getText());
            startActivityForResult(intent, 001);
        }
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
            if(tmp != null)
                lastPressed.setText(data.getStringExtra("Recipe Name"));
            else
                lastPressed.setText("(No Meal Selected)");
        }
    }

    /**
     * Advance to next day
     *
     * @param v button clicked
     */
    public void nextDay(View v) {
        Intent intent = new Intent(getApplicationContext(), Day_View.class);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        intent.putExtra("Month", calendar.get(Calendar.MONTH));
        intent.putExtra("Year", calendar.get(Calendar.YEAR));
        intent.putExtra("Day", calendar.get(Calendar.DAY_OF_MONTH));
        startActivity(intent);
        overridePendingTransition(0, 0);
        setChanges();
        finish();
    }

    /**
     * Return to previous day
     *
     * @param v button clicked
     */
    public void prevDay(View v) {
        Intent intent = new Intent(getApplicationContext(), Day_View.class);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        intent.putExtra("Month", calendar.get(Calendar.MONTH));
        intent.putExtra("Year", calendar.get(Calendar.YEAR));
        intent.putExtra("Day", calendar.get(Calendar.DAY_OF_MONTH));
        startActivity(intent);
        overridePendingTransition(0, 0);
        setChanges();
        finish();
    }

    @Override
    public void onBackPressed() {
        setChanges();
        super.onBackPressed();
    }

    public void setChanges() {
        dayObject.breakfast = handler.cookbook.get(mealchoice1.getText().toString());
        dayObject.lunch     = handler.cookbook.get(mealchoice2.getText().toString());
        dayObject.dinner    = handler.cookbook.get(mealchoice3.getText().toString());
        dayObject.save(file);
    }

    @Override
    protected void onPause() {
        setChanges();
        super.onPause();
    }
}
