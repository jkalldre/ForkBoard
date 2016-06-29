package com.forkboard.forkboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;

public class Display_Recipe_Item extends AppCompatActivity {
    @Bind(R.id.recipeName)  TextView recipeName;
    @Bind(R.id.cookTime)    TextView cookTime;
    @Bind(R.id.servingSize) TextView servingSize;
    @Bind(R.id.directions)  TextView directions;

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

    public void toEdit(View v) {
        Intent intent = new Intent(getApplicationContext(), Recipe_Input.class);
        intent.putExtra("selected", recipe.name());
        startActivityForResult(intent, 003);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(003,data);
        finish();
    }
}
