package com.forkboard.forkboard;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("ForkBoard");


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
                switch(position){
                    case 0:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, CalendarView.class);
                        break;
                        // and so on
                       // .....

                }
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
/*
class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public SpinnerActivity() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        spinner.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        System.out.println(pos);
        if (parent.getItemAtPosition(pos) == "Calendar") {
            Intent intent = new Intent(this, CalendarView.class);
            startActivity(intent);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
*/