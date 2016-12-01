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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class excludeRecipe extends AppCompatActivity {

    //Reference to original MainScreen instantiation
    private MainScreen cookBook;

    //Stored choices of included ingredients from previous activity.
    private ArrayList<Ingredient> includeIngredients = new ArrayList<Ingredient>();

    //Only variable used in this activity, the rest are instantianted from intent of previous activity.
    private ArrayList<Ingredient> excludeIngredients = new ArrayList<Ingredient>();

    //Stored choices of user type and category from previous activity.
    private String type;
    private String category;

    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> typeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_recipe);

        //Previous variables are retrieved here.
        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        includeIngredients = (ArrayList<Ingredient>) getIntent().getExtras().getSerializable("chosenIngredients");
        type = (String)getIntent().getExtras().getSerializable("chosenType");
        category = (String)getIntent().getExtras().getSerializable("chosenCategory");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");

        //Populates list view.
        populateIngredientListView();

        //Sets onClick for getRecipes Button to go to next activity.
        Button getRecipes = (Button) findViewById(R.id.getRecipeResults);
        getRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(excludeRecipe.this,RecipeResult.class);

                //Potential recipe matches are retrieved used findRecipe method and passed in variable result.
                ArrayList<Recipe> result = findRecipe(type,category,includeIngredients,excludeIngredients);

                //Deselects all ingredients in Cook Book.
                for(int i =0 ; i<cookBook.get_cookBookIngredients().size(); i++){
                    cookBook.get_cookBookIngredients().get(i).set_selected(false);
                }

                //Passes results and reference to Cook Book.
                Bundle bundle = new Bundle();
                bundle.putSerializable("result",result);
                bundle.putSerializable("cookBook",cookBook);
                bundle.putSerializable("typeList",typeList);
                bundle.putSerializable("categoryList",categoryList);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //Same adapter and listview implementation as previous classes.
    public void populateIngredientListView(){
        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(excludeRecipe.this,cookBook.get_cookBookIngredients());
        ListView ingredientList = (ListView)findViewById(R.id.excludeIngredientsListView);
        ingredientList.setAdapter(ingredientListAdapter);
    }

    private class IngredientAdapter extends ArrayAdapter<Ingredient>{

        private Context context;

        public IngredientAdapter(Context context, ArrayList<Ingredient> objects) {
            super(context, R.layout.single_ingredient_black, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_ingredient_black,parent,false);

            final Ingredient curIngredient = getItem(position);

            String currentIngredient = curIngredient.get_IngredientName();

            TextView ingredientText = (TextView) customView.findViewById(R.id.singleIngredientName_black);
            ingredientText.setText(currentIngredient);

            CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection_black);

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

        //acts as fail-safe for Illegal arguments for finding recipe.
        if(type == null || category == null || include_ingredientList == null || exclude_ingredientList == null){
            throw new IllegalArgumentException("One or all of the arguments provided are null in findRecipe()");
        }

        //Stores possible matches in this variable.
        ArrayList<Recipe> result = new ArrayList<Recipe>();

        //Iterates through Cook Book recipes, first by isolating possible types and categories
        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++) {

             /*
                Then determines if some or all of user-selected ingredients are in a recipe.
                Then determines if none of the excluded ingredients are in that recipe.

                If these conditions are satisifed, adds to result variable as a potential recipe.
                */

            Recipe recipe = cookBook.get_cookBookRecipes().get(i);

            //Cut down by type
            if(include_ingredientList.size() == 0
                    && exclude_ingredientList.size() == 0
                    && category.equals(recipe.getRecipeCategory())
                    && type.equals(recipe.getRecipeType())){

                result.add(recipe);

            } else if (include_ingredientList.size() == 0
                    && exclude_ingredientList.size() != 0
                    && (recipe.getRecipeCategory().equals(category))
                    && (recipe.getRecipeType().equals(type))){

                if(recipe.getRecipeCategory().equals("Any")){

                } else{

                    if(recipe.getRecipeCategory().equals(category)){
                        
                    }

                }

                if(Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)){
                    result.add(recipe);
                }
            }

            if (type.equals(recipe.getRecipeType())) {

                if (category.equals(recipe.getRecipeCategory())) {

                    if(include_ingredientList.size()>0){

                        if(!Collections.disjoint(recipe.getRecipeIngredients(), include_ingredientList)
                                && Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)){
                            result.add(recipe);
                        }

                    } else {

                        if(exclude_ingredientList.size()>0){

                            if(Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)){
                                result.add(recipe);
                            }

                        } else {

                            Toast.makeText(getApplicationContext(), "Did we make it here", Toast.LENGTH_SHORT).show();
                            result.add(recipe);

                        }

                    }

                }

            } else if (type.equals("Any")) {

                if (category.equals(recipe.getRecipeCategory())) {

                    if(include_ingredientList.size()>0){

                        if(!Collections.disjoint(recipe.getRecipeIngredients(), include_ingredientList)
                                && Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)) {
                            result.add(recipe);
                        }

                    } else {

                        if(exclude_ingredientList.size()>0){

                            if(Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)){
                                result.add(recipe);
                            }

                        } else {

                            result.add(recipe);

                        }

                    }

                } else if (category.equals("Any")) {

                    if(include_ingredientList.size()>0){

                        if(!Collections.disjoint(recipe.getRecipeIngredients(), include_ingredientList)
                                && Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)) {
                            result.add(recipe);
                        }

                    } else {

                        if(exclude_ingredientList.size()>0){

                            if(Collections.disjoint(recipe.getRecipeIngredients(),exclude_ingredientList)){
                                result.add(recipe);
                            }

                        } else {

                            result.add(recipe);

                        }

                    }

                }
            }
        }




            /*
            if(include_ingredientList.size() == 0){

                if(exclude_ingredientList.size() == 0){

                    if(type.equals("Any") || category.equals("Any")){
                        if(type.equals("Any") && category.equals("Any")){

                            //No chosen ingredients. No chosen exclusion. Any type, any category. Populate all recipes.
                            result.add(recipe);

                        } else if(type.equals("Any") && category.equals(recipe.getRecipeCategory())) {

                            //No chosen ingredients. No chosen exclusion. Only recipes with a category.
                            result.add(recipe);

                        } else if(type.equals(recipe.getRecipeType()) && recipe.equals("Any")){

                            result.add(recipe);

                        } else if(type.equals(recipe.getRecipeType()) && category.equals(recipe.getRecipeCategory())){

                            result.add(recipe);

                        }

                    } else {

                        //There are strict parameters on type and category but no inclusions or disclusions.
                        if(type.equals(recipe.getRecipeCategory()) && type.equals(recipe.getRecipeType())){
                            result.add(recipe);
                        }

                    }


                } else{

                }

            } else {
                if(type.equals("Any") || category.equals("Any")){
                    if(type.equals("Any") && category.equals(cookBook.get_cookBookRecipes().get(i).getRecipeCategory())){

                    } else if (type.equals(cookBook.get_cookBookRecipes().get(i).getRecipeType()) && category.equals("Any")){

                    }
                }else{

                }
            }


            if(type.equals(cookBook.get_cookBookRecipes().get(i).getRecipeType())
                    && category.equals(cookBook.get_cookBookRecipes().get(i).getRecipeCategory())
                    && include_ingredientList.size() == 0 && exclude_ingredientList.size() == 0){

                result.add(cookBook.get_cookBookRecipes().get(i));

            } else if (include_ingredientList.size() == 0 && exclude_ingredientList.size() > 0){

                if(Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(),exclude_ingredientList)){
                    result.add(cookBook.get_cookBookRecipes().get(i));
                }

            } else if (include_ingredientList.size() == 0 && exclude_ingredientList.size() == 0){

                result.add(cookBook.get_cookBookRecipes().get(i));

            } else if(type.equals("Any") && category.equals("Any")){
                if(!Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(), include_ingredientList)
                        && Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(),exclude_ingredientList)){
                    result.add(cookBook.get_cookBookRecipes().get(i));
                }
            } else if (type.equals("Any") && category.equals(cookBook.get_cookBookRecipes().get(i).getRecipeCategory())){
                if(!Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(), include_ingredientList)
                        && Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(),exclude_ingredientList)){
                    result.add(cookBook.get_cookBookRecipes().get(i));
                }
            } else if (type.equals(cookBook.get_cookBookRecipes().get(i).getRecipeType()) && category.equals("Any")){
                if(!Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(), include_ingredientList)
                        && Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(),exclude_ingredientList)){
                    result.add(cookBook.get_cookBookRecipes().get(i));
                }
            } else if(type.equals(cookBook.get_cookBookRecipes().get(i).getRecipeType()) && category.equals(cookBook.get_cookBookRecipes().get(i).getRecipeCategory()))
            {
                if(!Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(), include_ingredientList)
                        && Collections.disjoint(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients(),exclude_ingredientList)){
                    result.add(cookBook.get_cookBookRecipes().get(i));
                }

            }
        }*/

        //Returns all possible recipes.
        return result;
    }

}
