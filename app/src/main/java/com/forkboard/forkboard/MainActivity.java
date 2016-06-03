package com.forkboard.forkboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    public static int myCOLOR = Color.parseColor("#fef2b9");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ActionBar bar = getSupportActionBar();
      //  bar.setBackgroundDrawable(new ColorDrawable(myCOLOR));
    }

    public void changeBackground(View v) {
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.background);
        rl.setBackgroundColor(myCOLOR);

    }

    public void toCalendar(View v) {
        Intent intent = new Intent(this, CalendarView.class);
        startActivity(intent);
    }

    public void test(View v) {
        Intent intent = new Intent();
        startActivity(intent);
    }
}
