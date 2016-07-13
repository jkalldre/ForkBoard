package com.forkboard.forkboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Activity for creating a shopping list
 *
 * This activity is used to display the shopping list and add items to the shopping list.
 *
 * @author Janine
 */

public class ShoppingListWeek extends AppCompatActivity implements OnClickListener {

    // Instance variables
    private ListView mShoppingList;
    private RecipeLogHandler handler = new RecipeLogHandler(this);
    private FoodInventory    allFood = new FoodInventory();
    private ArrayAdapter<String> adapter;
    private List<String> foodList = new ArrayList<>();
    private Calendar fromDate = new GregorianCalendar();
    private Calendar toDate   = new GregorianCalendar();

    //UI References for the DATE PICKER
    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    // for date spinners
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Standard app startup //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_week);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        // Standard app startup //

        // initialize used views
        mShoppingList = (ListView) findViewById(R.id.shopping_listView);
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);

        // shared pref to remember last selected days
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        Calendar cal = Calendar.getInstance();
        String today = "" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.YEAR);
        String tDate = pref.getString("SHOPPING_LIST_toDate", today);
        String fDate   = pref.getString("SHOPPING_LIST_fromDate", today);
        String temp = "";

        // in case earlier date is selected for toDate
        if (Misc.compareDates(Format.changeFormat(tDate),Format.changeFormat(fDate))){
            temp = fDate;
            fDate = tDate;
            tDate = temp;
        }

        // set their text to appropriate date
        fromDateEtxt.setText(fDate);
        toDateEtxt.setText(tDate);

        // populate list on initilize with total ingredients
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        generateList(Format.MMDDYYYY_to_Gregorian(fDate), Format.MMDDYYYY_to_Gregorian(tDate));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodList);

        // Picker stuff
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        // fill listview
        handler.load();
        mShoppingList.setAdapter(adapter);

        // run when new date is selected
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // required for override
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // save new date to shared pref
                SharedPreferences pref = ShoppingListWeek.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("SHOPPING_LIST_fromDate", fromDateEtxt.getText().toString());
                edit.putString("SHOPPING_LIST_toDate", toDateEtxt.getText().toString());
                edit.commit();

                // update list
                generateList(fromDate, toDate);
                adapter.notifyDataSetChanged();
                refresh();
            }

            @Override
            public void afterTextChanged(Editable s) {
            // required for override
            }
        };

        // set listener for text change
        fromDateEtxt.addTextChangedListener(tw);
        toDateEtxt  .addTextChangedListener(tw);


    }

    // Date Picker
    // http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/
    private void findViewsById() {
        // set up date wheel input type
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt.setInputType(InputType.TYPE_NULL);

    }

    private void setDateTimeField() {
        // set onclick listener
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        // tell datepicker what to display in dialog box
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        // tell datepicker what to display in dialog box
        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        // display dialog when clicked
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }


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

    public void generateList(Calendar start, Calendar end){
        // generate list of dates from which ingredients will be collected
        Day dayObject = null;
        List<String> listDates = Misc.generateDateList(start, end);

        for (String date : listDates) {
            dayObject = new Day(this);
            dayObject.load(date);

            // see if recipe is selected and add ingredients of selected recipes
            if(!dayObject.breakfast.name().equals("(No Meal Selected)"))
                loadFood(dayObject.breakfast);
            if(!dayObject.lunch.name().equals("(No Meal Selected)"))
                loadFood(dayObject.lunch);
            if(!dayObject.dinner.name().equals("(No Meal Selected)"))
                loadFood(dayObject.dinner);
        }

        // update listview
        foodList = allFood.ingredientList();
        adapter.notifyDataSetChanged();
        mShoppingList.setAdapter(adapter);
    }

    private void loadFood(Recipe r) {
        // add all ingredients of passed recipe to allfood
        for(Food foodItem : r.ingredients().toArray()) {
            allFood.add(foodItem);
        }
    }

    @Override
    protected void onResume() {
        // onResume update list in case recipes slected have changed
        super.onResume();
        generateList(new GregorianCalendar(), new GregorianCalendar());
        adapter.notifyDataSetChanged();
    }

    /**
     * refresh the current activity
     */
    private void refresh(){
        // refresh activity and update list
        Intent refresh = new Intent(this, ShoppingListWeek.class);
        adapter.notifyDataSetChanged();
        startActivity(refresh);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onPause() {
        // if activity is left, close it
        super.onPause();
        finish();
    }
}
