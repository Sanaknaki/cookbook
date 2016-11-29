package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditIngredients extends AppCompatActivity {

    private MainScreen cookBook;

    ArrayList<Ingredient> cookBookIngredients = new ArrayList<Ingredient>();
    ArrayList<String> cookBookStringIngredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        Bundle bundle = getIntent().getExtras();

        cookBook = (MainScreen) bundle.getSerializable("cookBook");

        cookBookIngredients = cookBook.get_cookBookIngredients();

        for(int i = 0; i<cookBookIngredients.size(); i++){
            cookBookStringIngredients.add(cookBookIngredients.get(i).get_IngredientName());
        }

        populateListView();

    }

    public void populateListView(){
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cookBookStringIngredients);
        final ListView ingredientListView = (ListView) findViewById(R.id.currentIngredientListView);
        ingredientListView.setAdapter(stringArrayAdapter);
        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ingredientToEdit = cookBookStringIngredients.get(i);

                Intent intent = new Intent(EditIngredients.this,EditTextIngredient.class);

                Bundle b = new Bundle();
                b.putSerializable("ingredientToEdit",ingredientToEdit);
                b.putSerializable("cookBook",cookBook);

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void clickAddIngredient(View view){
        EditText userInput = (EditText) findViewById(R.id.addIngredientInput);

        String userInputString = userInput.getText().toString();
        userInput.setText("");

        if(!cookBookIngredients.contains(new Ingredient(userInputString))) {

            if(userInputString != null && !userInputString.equals("")){
                cookBook.add_cookBookIngredient(userInputString);
                updateCookBookIngredients();

                Toast toast = Toast.makeText(getApplicationContext(), ("Ingredient Added: " + userInputString), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Must Enter an Ingredient to Add", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Ingredient Already stored", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void clickDeleteIngredient(View view){
        EditText userInput = (EditText) findViewById(R.id.addIngredientInput);

        String userInputString = userInput.getText().toString();
        userInput.setText("");

        if(userInputString!= null && !userInputString.equals("")){
            if(cookBookIngredients.contains(new Ingredient(userInputString))){
                cookBook.delete_cookBookIngredient(userInputString);

                for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
                    if(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().contains(new Ingredient(userInputString))){
                        cookBook.get_cookBookRecipes().remove(i);
                    }
                }

                updateCookBookIngredients();

                Toast.makeText(getApplicationContext(),("Ingredient deleted: " + userInputString),Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),("No such Ingredient: " + userInputString),Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),("Must Enter Ingredient to Delete"),Toast.LENGTH_SHORT).show();
        }

    }

    public void clickFinishedEditIngredient(View view){
        Intent intent = new Intent(this,MainScreen.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable("cookBook",cookBook);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void updateCookBookIngredients(){
        cookBookIngredients = cookBook.get_cookBookIngredients();

        cookBookStringIngredients = new ArrayList<String>();

        for(int i = 0; i<cookBookIngredients.size(); i++){
            cookBookStringIngredients.add(cookBookIngredients.get(i).get_IngredientName());
        }

        populateListView();
    }


}
