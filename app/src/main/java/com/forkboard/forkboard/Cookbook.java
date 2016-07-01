package com.forkboard.forkboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class Cookbook extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private RecipeLogHandler handler;
    private RecipeLog cookbook = new RecipeLog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook__book        );
        Toolbar tb =  (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        tb.setTitle("Cookbook");

        ListView list = (ListView) findViewById(R.id.listView);
        handler       = new RecipeLogHandler(this);
        handler.load();

        if (cookbook.recipeList().length == 0) {
            Log.i(Warnings.EMPTY_OBJECT, "There is nothing in the cookbook!");
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, handler.cookbook.recipeList());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cookbook.this);
                final String name = (String)parent.getAdapter().getItem(position);
                builder.setTitle("Options");
                //builder.setMessage("Select one");

                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), Display_Recipe_Item.class);
                        intent.putExtra("selected", name);
                        startActivityForResult(intent, 003);
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Cookbook.this);
                        builder1.setTitle("Are You Sure?");
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                handler.remove(handler.cookbook.get(name));
                                handler.load();
                                adapter.notifyDataSetChanged();
                                refresh();
                            }
                        });
                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder1.show();


                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        new ActivityChanger().changeActivity(item,this);
        return true;
    }

    /**
     * Method calls recipe input method
     *
     * @param v The v is a view object
     */
    public void toRecipeInput(View v){
        Intent intent = new Intent(this,Recipe_Input.class);
        intent.putExtra("selected", "");
        startActivityForResult(intent, 002);
    }

    /**
     * onActivityResult update the adapter and refresh the activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super       .onActivityResult(requestCode, resultCode, data);
        handler     .load();
        adapter     .notifyDataSetChanged();
        Intent refresh = new Intent(this, Cookbook.class);
        startActivity(refresh);
        this.finish();
    }

    /**
     * assists in refreshing
     */
    public void onResume(){
        super.onResume();
        handler.load();
        adapter.notifyDataSetChanged();
    }

    private void refresh(){
        Intent refresh = new Intent(this, Cookbook.class);
        startActivity(refresh);
        this.finish();
    }

}
