package com.forkboard.forkboard;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeBackground(View v) {
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.background);
        rl.setBackgroundColor(Color.CYAN);
    }
}
