package com.forkboard.forkboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This activity lets  us select specific days of the
 * month to then go to Day_View and add desired recipes
 */
public class Calendar_View extends AppCompatActivity {
    Intent intentToDay = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view     );
        Toolbar tb =  (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        intentToDay = new Intent(Calendar_View.this,Day_View.class);
        Calendar today = Calendar.getInstance();
       // System.out.println("**********************" + "" + today.get(Calendar.MONTH) + " " + today.get(Calendar.DAY_OF_MONTH) + ", " + today.get(Calendar.YEAR));

        intentToDay.putExtra("Day"  , today.get(Calendar.DAY_OF_MONTH));
        intentToDay.putExtra("Month", today.get(Calendar.MONTH)       );
        intentToDay.putExtra("Year" , today.get(Calendar.YEAR)        );

        CalendarView calendar = (CalendarView) findViewById(R.id.cv);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Intent intent = new Intent(Calendar_View.this,Day_View.class);
                intentToDay.putExtra("Day"  , dayOfMonth);
                intentToDay.putExtra("Month", month     );
                intentToDay.putExtra("Year" , year      );

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        new ActivityChanger().changeActivity(item, this);
        return true;
    }

    public void toDay(View v) {
        startActivity(intentToDay);
    }
}
