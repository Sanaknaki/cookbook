package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditIngredients extends AppCompatActivity {

    private MainScreen cookBook;

    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> cookBookStringIngredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        Bundle bundle = getIntent().getExtras();

        cookBook = (MainScreen) bundle.getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        for(int i = 0; i<cookBook.get_cookBookIngredients().size(); i++){
            cookBookStringIngredients.add(cookBook.get_cookBookIngredients().get(i).get_IngredientName());
        }

        populateListView();

    }

    public void populateListView(){
        ArrayAdapter<Ingredient> stringArrayAdapter = new IngredientAdapter(getApplicationContext(),cookBook.get_cookBookIngredients());
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
                b.putSerializable("typeList",typeList);
                b.putSerializable("categoryList",categoryList);

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private class IngredientAdapter extends ArrayAdapter<Ingredient>{


        private Context context;

        public IngredientAdapter(Context context, ArrayList<Ingredient> objects) {
            super(context, R.layout.single_recipe_white, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe_white,parent,false);

            final Ingredient curIngredient = getItem(position);

            String currentIngredient = curIngredient.get_IngredientName();
            //boolean selection = curIngredient.is_selected();

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button_designWhite);
            ingredientText.setText(currentIngredient);

            ingredientText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ingredient currentRecipe = cookBook.get_cookBookIngredients().get(pos);

                    EditText userInput = (EditText) findViewById(R.id.addIngredientInput);
                    userInput.setTextColor(Color.WHITE);
                    userInput.setText(currentRecipe.get_IngredientName());

                    //Toast.makeText(getApplicationContext(), "Clicked: " + currentRecipe.get_IngredientName(), Toast.LENGTH_SHORT).show();
                    //populateListView();
                }
            });

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }
    public void clickAddIngredient(View view){
        EditText userInput = (EditText) findViewById(R.id.addIngredientInput);

        String userInputString = userInput.getText().toString();
        userInput.setText("");

        if(!cookBook.get_cookBookIngredients().contains(new Ingredient(userInputString))) {

            if(userInputString != null && !userInputString.equals("")){
                cookBook.add_cookBookIngredient(userInputString);
                populateListView();

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
            if(cookBook.get_cookBookIngredients().contains(new Ingredient(userInputString))){
                cookBook.delete_cookBookIngredient(userInputString);

                for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
                    if(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().contains(new Ingredient(userInputString))){
                        cookBook.get_cookBookRecipes().remove(i);
                    }
                }

                populateListView();

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
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }


}
