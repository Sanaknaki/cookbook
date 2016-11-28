package com.example.unaizrehmani.finalcookbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class singleRecipeScreen extends AppCompatActivity {

    private Recipe recipe;
    private ArrayList<Ingredient> recipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe_screen);

        recipe  = (Recipe) getIntent().getExtras().getSerializable("currentRecipe");
        recipeIngredients = recipe.getRecipeIngredients();

        TextView recipeName = (TextView) findViewById(R.id.recipeNamePopUpTextView);
        recipeName.setText(recipe.getRecipeName());

        TextView recipeCookTime = (TextView) findViewById(R.id.getCookTextView);
        recipeCookTime.setText(Integer.toString(recipe.get_cookTime()));

        TextView prepTime = (TextView) findViewById(R.id.getPrepTextView);
        prepTime.setText(Integer.toString(recipe.get_prepTime()));

        TextView calories = (TextView) findViewById(R.id.getCaloriesTextView);
        calories.setText(Integer.toString(recipe.get_calories()));

        TextView category = (TextView) findViewById(R.id.getCategoryTextView);
        category.setText(recipe.getRecipeCategory());

        TextView type = (TextView) findViewById(R.id.getTypeTextView);
        type.setText(recipe.getRecipeType());


        //TextView prepTime = (TextView) findViewById(R.id.getPrepTextView);
        //prepTime.setText(recipe.get_prepTime());
    }
}
