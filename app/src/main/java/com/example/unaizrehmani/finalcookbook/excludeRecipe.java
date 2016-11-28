package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class excludeRecipe extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<Ingredient> includeIngredients = new ArrayList<Ingredient>();
    private String type;
    private String category;
    private ArrayList<Ingredient> excludeIngredients = new ArrayList<Ingredient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_recipe);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        includeIngredients = (ArrayList<Ingredient>) getIntent().getExtras().getSerializable("chosenIngredients");

        type = (String)getIntent().getExtras().getSerializable("chosenType");
        category = (String)getIntent().getExtras().getSerializable("chosenCategory");

        populateIngredientListView();
        Button getRecipes = (Button) findViewById(R.id.getRecipeResults);
        getRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(excludeRecipe.this,RecipeResult.class);
                ArrayList<Recipe> result = findRecipe(type,category,includeIngredients,excludeIngredients);

                //uncheck ingredients in cookBook
                for(int i =0 ; i<cookBook.get_cookBookIngredients().size(); i++){
                    cookBook.get_cookBookIngredients().get(i).set_selected(false);
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("result",result);
                bundle.putSerializable("cookBook",cookBook);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void populateIngredientListView(){
        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(excludeRecipe.this,cookBook.get_cookBookIngredients());
        ListView ingredientList = (ListView)findViewById(R.id.excludeIngredientsListView);
        ingredientList.setAdapter(ingredientListAdapter);
    }

    private class IngredientAdapter extends ArrayAdapter<Ingredient>{

        private Context context;

        public IngredientAdapter(Context context, ArrayList<Ingredient> objects) {
            super(context, R.layout.single_ingredient, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_ingredient,parent,false);

            final Ingredient curIngredient = getItem(position);

            String currentIngredient = curIngredient.get_IngredientName();
            boolean selection = curIngredient.is_selected();

            TextView ingredientText = (TextView) customView.findViewById(R.id.singleIngredientName);
            ingredientText.setText(currentIngredient);

            CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);

            //Need to double check to ensure that boxes stay ticked.

            ingredientSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                    curIngredient.set_selected(isChecked);

                    if(isChecked){
                        excludeIngredients.add(curIngredient);
                    } else {
                        excludeIngredients.remove(curIngredient);
                    }

                }
            });

            ingredientSelected.setChecked(curIngredient.is_selected());
            ingredientSelected.setTag(curIngredient);

            return customView;
        }

    }

    public ArrayList<Recipe> findRecipe(String type, String category, ArrayList<Ingredient> include_ingredientList, ArrayList<Ingredient> exclude_ingredientList){
        /*
        if(type == null || category == null || include_ingredientList == null || exclude_ingredientList == null){
            throw new IllegalArgumentException("One or all of the arguments provided are null in findRecipe()");
        }*/

        ArrayList<Recipe> result = new ArrayList<Recipe>();

        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
            if(type.equals(cookBook.get_cookBookRecipes().get(i).getRecipeType()) && category.equals(cookBook.get_cookBookRecipes().get(i).getRecipeCategory()))
            {
                //ArrayList<String> recipeIngredientsString = ingredientToString(recipes.get(i).getIngredients());
                //ArrayList<String> inFridgeString = ingredientToString(ingredientList);

                if(!Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(), include_ingredientList)
                        && Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(),exclude_ingredientList)){
                    result.add(cookBook.get_cookBookRecipes().get(i));
                }

            }
        }

        return result;
    }

}
