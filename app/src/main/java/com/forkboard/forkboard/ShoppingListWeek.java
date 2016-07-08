package com.forkboard.forkboard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;
    private ArrayAdapter<String> mAdapter;
    private RecipeLogHandler handler = new RecipeLogHandler(this);
    private FoodInventory    allFood = new FoodInventory();
    private ArrayAdapter<String> adapter;
    private List<String> foodList = new ArrayList<>();

    private Calendar fromDate = new GregorianCalendar();
    private Calendar toDate   = new GregorianCalendar();


    //UI References for the DATE PICKER
    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_week);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        mShoppingList = (ListView) findViewById(R.id.shopping_listView);
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);

        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        Calendar cal = Calendar.getInstance();
        String today = "" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.YEAR);
        String fDate = pref.getString("SHOPPING_LIST_toDate", today);
        String tDate   = pref.getString("SHOPPING_LIST_fromDate", today);

        fromDateEtxt.setText(fDate);
        toDateEtxt.setText(tDate);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        generateList(Format.MM$DD$YYYY_to_Gregorian(fDate), Format.MM$DD$YYYY_to_Gregorian(tDate));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodList);

        // Picker stuff
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();

        handler.load();
        mShoppingList.setAdapter(adapter);



    }

    // Date Picker
    // http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/
    private void findViewsById() {

        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }

        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("SHOPPING_LIST_fromDate", fromDateEtxt.getText().toString());
        edit.putString("SHOPPING_LIST_toDate", toDateEtxt.getText().toString());
        edit.commit();
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
        FoodInventory newIngrList = new FoodInventory();
        Day dayObject = null;
       /* if (!fromDateEtxt.getText().toString().equals("")
                && !toDateEtxt.getText().toString().equals("")) {
            fromDate = Format.MM$DD$YYYY_to_Gregorian(fromDateEtxt.getText().toString());
            toDate   = Format.MM$DD$YYYY_to_Gregorian(toDateEtxt.getText().toString());
        }*/
        List<String> listDates = Misc.generateDateList(fromDate, toDate);
        for (String date : listDates) {
            dayObject = new Day(this);
            dayObject.load(date);
            if(!dayObject.breakfast.name().equals("(No Meal Selected)"))
                loadFood(dayObject.breakfast, newIngrList);
            if(!dayObject.lunch.name().equals("(No Meal Selected)"))
                loadFood(dayObject.lunch, newIngrList);
            if(!dayObject.dinner.name().equals("(No Meal Selected)"))
                loadFood(dayObject.dinner, newIngrList);

            System.out.println("++++" + "B:" + dayObject.breakfast.name() + " L:"
            + dayObject.lunch.name() + " D:" + dayObject.dinner.name() + "++++");
        }

        foodList = newIngrList.ingredientList();
        adapter.notifyDataSetChanged();
        mShoppingList.setAdapter(adapter);
       // System.out.println("***" + allFood.toString());
        for (String food : foodList)
        System.out.println(food);
    }

    private void loadFood(Recipe r, FoodInventory f) {
        for(Food foodItem : r.ingredients().toArray()) {
            f.add(foodItem);
        }
        System.out.println("---- added " + r.name() + "-----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        generateList(new GregorianCalendar(), new GregorianCalendar());
        adapter.notifyDataSetChanged();
    }
}
