package com.example.unaizrehmani.finalcookbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class AddRecipeDirections extends AppCompatActivity {

    private MainScreen cookBook;
    private String recipeName;
    private String chosenCategory;
    private String chosenType;
    private ArrayList<Ingredient> chosenAddIngredients;
    private int prepIntTime;
    private int cookIntTime;
    private int caloriesIntTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_directions);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        recipeName = (String) getIntent().getExtras().getSerializable("recipeName");
        chosenCategory = (String)getIntent().getExtras().getSerializable("chosenCategory");
        chosenType = (String)getIntent().getExtras().getSerializable("chosenType");
        chosenAddIngredients = (ArrayList<Ingredient>) getIntent().getExtras().getSerializable("chosenAddIngredients");
        prepIntTime = (Integer) getIntent().getExtras().getSerializable("prepIntTime");
        cookIntTime = (Integer) getIntent().getExtras().getSerializable("cookIntTime");
        caloriesIntTime = (Integer) getIntent().getExtras().getSerializable("caloriesIntTime");


    }
}
