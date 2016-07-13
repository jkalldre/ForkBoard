package com.forkboard.forkboard;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by Jacob on 6/13/2016.
 * The purpose of this class is to handle the menu's onitemselected listener
 * and change to the appropriate activity.
 */
public class ActivityChanger {

    static boolean changeActivity(MenuItem item, Context context){
        // depending on menuitem given, the related activity will be executed
        Intent intent = null;
        if(item.getTitle().equals("Main Menu")){
            intent = new Intent(context,MainActivity.class);
        }
        if(item.getTitle().equals("Calendar")){
            intent = new Intent(context,Calendar_View.class);
        }
        if(item.getTitle().equals("Shopping List")){
            intent = new Intent(context,ShoppingListWeek.class);
        }
        if(item.getTitle().equals("Cookbook")){
            intent = new Intent(context,Cookbook.class);
        }
        if(item.getTitle().equals("Conversion Calc")){
            intent = new Intent(context,Conversion_Calculator.class);
        }
        if (intent != null){
            intent.putExtra("hasLogged", true);
            context.startActivity(intent);
        }

        return true;
    }
}
