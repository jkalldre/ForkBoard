package com.forkboard.forkboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Conversion_Calculator extends AppCompatActivity {
    private Spinner s;
    private Spinner s2;
    private Spinner s3;
    String  typeSelected = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion__calculator);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);

        s  = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        String[] choice = {"Weights", "Volumes"};

        final ArrayAdapter<String> weights = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._weights);
        final ArrayAdapter<String> volumes = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Misc._volumes);
        final ArrayAdapter<String> choices = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, choice);

        s.setAdapter(weights);
        s2.setAdapter(weights);
        s3.setAdapter(choices);

        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Weights")){
                    s.setAdapter(weights);
                    s2.setAdapter(weights);
                }
                else{
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

    public void calc(View v) {
        System.out.println("Clicked");
       // UnitConverter.
    }
}
