package com.forkboard.forkboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;

public class Display_Recipe_Item extends AppCompatActivity {
    // instance variables
    private Recipe recipe = null;
    private RecipeLogHandler handler;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__recipe__item);
        Toolbar tb = (Toolbar)findViewById(R.id.my_toolbar);
        TextView recipeName  = (TextView)findViewById(R.id.recipeName );
        TextView cookTime    = (TextView)findViewById(R.id.cookTime   );
        TextView servingSize = (TextView)findViewById(R.id.servingSize);
        TextView directions  = (TextView)findViewById(R.id.directions );
        ListView lv          = (ListView)findViewById(R.id.ingredients);
        directions.setMovementMethod(new ScrollingMovementMethod());
        setSupportActionBar(tb);

        handler = new RecipeLogHandler(this);
        handler.load();
        recipe = handler.cookbook.get(getIntent().getStringExtra("selected"));
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, recipe.ingredients().ingredientList());
        lv.setAdapter(adapter);

        recipeName .setText(recipe.name());
        cookTime   .setText("" + recipe.cookTime() + " min.");
        servingSize.setText("" + recipe.serveCount() + " servings");
        directions .setText(recipe.instructions());

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
     * Switch to Recipe_Input to edit selected recipe
     * @param v button clicked
     */
    public void toEdit(View v) {
        Intent intent = new Intent(getApplicationContext(), Recipe_Input.class);
        intent.putExtra("selected", recipe.name());
        startActivityForResult(intent, 003);
    }

    public void delete(View v) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Are You Sure?");
        builder1.setMessage("You cannot undo this action.");
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.remove(recipe);
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(003,data);
        finish();
    }
}
