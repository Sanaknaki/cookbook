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
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeResult extends AppCompatActivity {
    MainScreen cookBook;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_result);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        recipes = (ArrayList<Recipe>) getIntent().getExtras().getSerializable("result");

        populateListView();

        Button goHome = (Button) findViewById(R.id.homeScreenButton);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeResult.this,MainScreen.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("cookBook",cookBook);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void populateListView(){
        ListView listView = (ListView) findViewById(R.id.finalRecipeResult);
        ArrayAdapter<Recipe> recipeArrayAdapter = new RecipeAdapter(getApplicationContext(),recipes);
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

            TextView ingredientText = (TextView) customView.findViewById(R.id.singleRecipeTextView);
            ingredientText.setText(currentIngredient);

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }
}

