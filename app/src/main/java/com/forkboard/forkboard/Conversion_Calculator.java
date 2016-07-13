package com.forkboard.forkboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Janine on 6/13/2016.
 * Modified by Jake on 6/29/2016.
 * The purpose of this class is to handle the conversion of units
 * by asking the user the conversion type, the unit from, the value, and the unit to.
 */
public class Conversion_Calculator extends AppCompatActivity {
    // instance variables
    private Spinner s;
    private Spinner s2;
    private Spinner s3;
    private EditText unitValue;
    private TextView result;
    private String  typeSelected = "Weights";
    private String  toType       = null;
    private String  fromType     = null;
    private double  toConvert    = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Standard app startup //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion__calculator);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        // Standard app startup //

        // bind views
        s  = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        unitValue = (EditText) findViewById(R.id.unit_value);
        result    = (TextView) findViewById(R.id.convert_result);
        String[] choice = {"Volumes", "Weights"};

        // set up spinner adapters
        final ArrayAdapter<String> weights = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._weights);
        final ArrayAdapter<String> volumes = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._volumes);
        final ArrayAdapter<String> choices = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, choice);

        // set spinner adapters
        s.setAdapter(volumes);
        s2.setAdapter(volumes);
        s3.setAdapter(choices);

        // set itemselectedlistener so when s3 is changed it will switch s & s2's adapters
        // this prevents user from comparing volumes and weights
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Weights")){
                    typeSelected = "Weights";
                    s.setAdapter(weights);
                    s2.setAdapter(weights);
                }
                else{
                    typeSelected = "Volumes";
                    s.setAdapter(volumes);
                    s2.setAdapter(volumes);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Methods are used to call the toolbar
     *
     * @param menu The Menu creates a menu object
     * @return The resulting is a boolean
     */

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
     * Calculate the conversion between two unit types
     *
     * @param v button clicked
     */
    public void calc(View v) {
        if (!unitValue.getText().toString().equals("")) {
            // calculate new value using our matrix of values
            toConvert = Double.parseDouble(unitValue.getText().toString());
            Double conversion = 0.0;

            if (typeSelected.equals("Weights"))
                conversion = UnitConverter.convert_weight(toConvert, Units.fromString(fromType),
                        Units.fromString(toType));
            else
                conversion = UnitConverter.convert_volume(toConvert, Units.fromString(fromType),
                        Units.fromString(toType));

            result.setText(Format.fractionize(conversion) + " " + toType);
        }
        else
            unitValue.setError("A Unit Value is Required!");
    }
}
