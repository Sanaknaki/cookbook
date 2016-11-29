package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    private ArrayList<String> directions = new ArrayList<String>();

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


        populateListView();

        Button add = (Button) findViewById(R.id.addRecipeDirectionStepButton);
        Button finish = (Button) findViewById(R.id.createRecipeFinalButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addRecipeDirectionEditText);
                String userStringInput = userInput.getText().toString();
                userInput.setText("");

                if(userStringInput!=null && !userStringInput.equals("")){
                    directions.add((directions.size()+1) + ") " + userStringInput);
                    populateListView();
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(directions.size() == 0){
                    Toast.makeText(getApplicationContext(), "Need to Add at Least One Step.", Toast.LENGTH_SHORT).show();
                } else {
                    Recipe recipe = new Recipe(recipeName,chosenCategory,chosenType,chosenAddIngredients,directions,prepIntTime,cookIntTime,caloriesIntTime);
                    Toast.makeText(getApplicationContext(), ("Added " + recipe + " to Cook Book."), Toast.LENGTH_SHORT).show();

                    cookBook.add_cookBookRecipe(recipe);

                    Intent intent = new Intent(AddRecipeDirections.this,MainScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cookBook", cookBook);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

    }

    public void populateListView(){
        ListView myListView = (ListView) findViewById(R.id.addRecipeDirectionListView);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddRecipeDirections.this,android.R.layout.simple_list_item_1,directions);
        myListView.setAdapter(myAdapter);
    }


}
