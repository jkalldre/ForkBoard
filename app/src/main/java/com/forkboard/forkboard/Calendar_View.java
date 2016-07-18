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
    // instance variable
    Intent intentToDay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Standard app startup //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view     );
        Toolbar tb =  (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        // Standard app startup //

        // set up day to current day by default
        intentToDay = new Intent(Calendar_View.this,Day_View.class);
        Calendar today = Calendar.getInstance();
        intentToDay.putExtra("Day"  , today.get(Calendar.DAY_OF_MONTH));
        intentToDay.putExtra("Month", today.get(Calendar.MONTH)       );
        intentToDay.putExtra("Year" , today.get(Calendar.YEAR)        );

        CalendarView calendar = (CalendarView) findViewById(R.id.cv);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // change day in intent if new day on calendar is selected
                intentToDay.putExtra("Day"  , dayOfMonth);
                intentToDay.putExtra("Month", month     );
                intentToDay.putExtra("Year" , year      );
                toDay(view);
            }
        });
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
     * Send user to selected day
     * @param v button clicked. not used.
     */
    public void toDay(View v) {
        // when button is clicked open selected day (Day_View)
        startActivity(intentToDay);
    }
}
