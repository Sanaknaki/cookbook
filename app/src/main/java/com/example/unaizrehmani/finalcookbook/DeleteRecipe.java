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
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DeleteRecipe extends AppCompatActivity {

    private MainScreen cookBook;
    ArrayList<String> cookBookStringRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recipe);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

        cookBookStringRecipes = new ArrayList<String>();

        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
            cookBookStringRecipes.add(cookBook.get_cookBookRecipes().get(i).getRecipeName());
        }

        populateListView();

        Button finished = (Button) findViewById(R.id.finishedDeleting);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (DeleteRecipe.this,MainScreen.class);
                Bundle b = new Bundle();
                b.putSerializable("cookBook",cookBook);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void populateListView(){
        ListView listView = (ListView) findViewById(R.id.deleteListView);
        ArrayAdapter<Recipe> recipeArrayAdapter = new RecipeAdapter(DeleteRecipe.this,cookBook.get_cookBookRecipes());
        listView.setAdapter(recipeArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button);
            ingredientText.setText(currentIngredient);

            ingredientText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe currentRecipe = cookBook.get_cookBookRecipes().get(pos);

                    cookBook.delete_cookBookRecipe(currentRecipe.getRecipeName());
                    populateListView();
                }
            });

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }

    public void update(){
        cookBookStringRecipes = new ArrayList<String>();

        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
            cookBookStringRecipes.add(cookBook.get_cookBookRecipes().get(i).getRecipeName());
        }
    }

}
