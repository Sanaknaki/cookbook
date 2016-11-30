package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleEditRecipeClass extends AppCompatActivity {

    //Variables for class.
    //Test commit
    private Recipe currentRecipe;
    private MainScreen cookBook;
    ArrayList<Ingredient> chosenAddIngredients = new ArrayList<Ingredient>();
    String chosenType;
    String chosenCategory;
    Spinner typeSpinner;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_edit_recipe_class);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        currentRecipe = (Recipe)getIntent().getExtras().getSerializable("currentRecipe");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(SingleEditRecipeClass.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categories));

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(SingleEditRecipeClass.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.types));

        categorySpinner = (Spinner) findViewById(R.id.getEditRecipeCategorySpinner);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //CREATE AND SET SPINNER FOR TYPE.
        typeSpinner = (Spinner) findViewById(R.id.getEditRecipeTypeSpinner);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        //Set the names to what Recipe originally had.
        ((EditText)findViewById(R.id.getEditRecipeNameEditView)).setText(currentRecipe.getRecipeName());
        ((EditText)findViewById(R.id.getEditPrepEditView)).setText(String.valueOf(currentRecipe.get_prepTime()));
        ((EditText)findViewById(R.id.getEditCookEditView)).setText(String.valueOf(currentRecipe.get_cookTime()));
        ((EditText)findViewById(R.id.getEditCaloriesEditView)).setText(String.valueOf(currentRecipe.get_calories()));


        //Set both Spinners to what Recipe initially had
        String[] categoryArray  = getResources().getStringArray(R.array.categories);
        int indexCategory = 0;

        for(int i = 0; i<categoryArray.length; i++ ){
            if(currentRecipe.getRecipeCategory().equals(categoryArray[i])){
                indexCategory = i;
                break;
            }
        }

        ((Spinner)findViewById(R.id.getEditRecipeCategorySpinner)).setSelection(indexCategory);

        String[] typesArray  = getResources().getStringArray(R.array.types);
        int indexType = 0;

        for(int i = 0; i<categoryArray.length; i++ ){
            if(currentRecipe.getRecipeCategory().equals(typesArray[i])){
                indexType = i;
                break;
            }
        }

        ((Spinner)findViewById(R.id.getEditRecipeTypeSpinner)).setSelection(indexType);

        //Set selections to what they initially were.
        for(Ingredient ingredient: cookBook.get_cookBookIngredients()){
            if(currentRecipe.getRecipeIngredients().contains(ingredient)){
                cookBook.get_cookBookIngredients().get(cookBook.get_cookBookIngredients().indexOf(ingredient)).set_selected(true);
            }
        }

        populateIngredientListView();
    }

    public void populateIngredientListView(){
        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(SingleEditRecipeClass.this,cookBook.get_cookBookIngredients());
        ListView ingredientList = (ListView)findViewById(R.id.getEditIngredientsToAddListView);
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
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            ingredientSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                    curIngredient.set_selected(isChecked);

                    if(isChecked){
                        chosenAddIngredients.add(curIngredient);
                    } else {
                        chosenAddIngredients.remove(curIngredient);
                    }

                }
            });

            ingredientSelected.setChecked(curIngredient.is_selected());
            ingredientSelected.setTag(curIngredient);

            return customView;
        }

    }

    public void clickEditRecipeSave(View view){
        String recipeName = ((EditText)findViewById(R.id.getEditRecipeNameEditView)).getText().toString();
        String cookTime = ((EditText)findViewById(R.id.getEditCookEditView)).getText().toString();
        String prepTime = ((EditText)findViewById(R.id.getEditPrepEditView)).getText().toString();
        String caloriesTime = ((EditText)findViewById(R.id.getEditCaloriesEditView)).getText().toString();

        //Toast.makeText(getApplicationContext(), recipeName, Toast.LENGTH_SHORT).show();

        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        //all fields must be satisfied;
        if(recipeName!=null && cookTime!=null
                && prepTime!=null && caloriesTime!=null
                && chosenType!=null && chosenCategory!=null
                && chosenAddIngredients.size() > 0){

            try{
                int prepIntTime = Integer.parseInt(prepTime);
                int caloriesIntTime = Integer.parseInt(caloriesTime);
                int cookIntTime = Integer.parseInt(cookTime);

                for (int i = 0; i<cookBook.get_cookBookIngredients().size(); i++){

                    cookBook.get_cookBookIngredients().get(i).set_selected(false);

                }

                Intent intent = new Intent(SingleEditRecipeClass.this,SingleEditRecipeDirectionClass.class);
                Bundle bundle = new Bundle();

                int positionRecipe = cookBook.get_cookBookRecipes().indexOf(currentRecipe);

                bundle.putSerializable("cookBook",cookBook);
                bundle.putSerializable("recipeName",recipeName);
                bundle.putSerializable("chosenCategory",chosenCategory);
                bundle.putSerializable("chosenType",chosenType);
                bundle.putSerializable("chosenAddIngredients",chosenAddIngredients);
                bundle.putSerializable("prepIntTime",prepIntTime);
                bundle.putSerializable("cookIntTime",cookIntTime);
                bundle.putSerializable("caloriesIntTime",caloriesIntTime);
                bundle.putSerializable("positionRecipe", positionRecipe);

                intent.putExtras(bundle);
                startActivity(intent);

            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Prep Time, Cook Time and Calories must be Numbers.", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "All Fields Must be Satisfied.", Toast.LENGTH_SHORT).show();

        }
    }
}
