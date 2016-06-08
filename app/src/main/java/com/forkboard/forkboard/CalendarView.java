package com.forkboard.forkboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.menu, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {
                // hide selection text
                ((TextView) view).setText(null);
                // if you want you can change background here
                Intent intent = null;
                if (position == 0){
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                if (position == 1){
                    intent = new Intent(getApplicationContext(),CalendarView.class);
                    startActivity(intent);
                }
               /*switch(position){
                    case 0:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, CalendarView.class);
                        startActivity(intent);
                        break;
                        // and so on
                       // .....

                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
