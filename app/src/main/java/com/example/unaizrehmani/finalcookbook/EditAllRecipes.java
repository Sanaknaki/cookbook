package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditAllRecipes extends AppCompatActivity {

    private MainScreen cookBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_recipes);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

        populateListView();

    }

    public void populateListView(){
        ListView listView = (ListView) findViewById(R.id.getEditRecipeListView);
        ArrayAdapter<Recipe> recipeArrayAdapter = new RecipeAdapter(EditAllRecipes.this,cookBook.get_cookBookRecipes());
        listView.setAdapter(recipeArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe currentRecipe = cookBook.get_cookBookRecipes().get(i);

                Intent intent = new Intent(EditAllRecipes.this,SingleEditRecipeClass.class);

                Bundle b = new Bundle();
                b.putSerializable("currentRecipe",currentRecipe);
                b.putSerializable("cookBook",cookBook);
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }

    private class RecipeAdapter extends ArrayAdapter<Recipe>{


        private Context context;

        public RecipeAdapter(Context context, ArrayList<Recipe> objects) {
            super(context, R.layout.single_recipe, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe,parent,false);

            final Recipe curIngredient = getItem(position);

            String currentIngredient = curIngredient.getRecipeName();
            //boolean selection = curIngredient.is_selected();

            TextView ingredientText = (TextView) customView.findViewById(R.id.singleRecipeTextView);
            ingredientText.setText(currentIngredient);

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }
}
