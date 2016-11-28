package com.example.unaizrehmani.finalcookbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class singleRecipeScreen extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe_screen);

        recipe  = (Recipe) getIntent().getExtras().getSerializable("currentRecipe");

        TextView recipeName = (TextView) findViewById(R.id.recipeNamePopUpTextView);
        recipeName.setText(recipe.getRecipeName());
    }
}
