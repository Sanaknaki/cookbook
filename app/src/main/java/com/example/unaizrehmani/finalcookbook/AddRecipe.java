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

public class AddRecipe extends AppCompatActivity {

    //Check comments on add recipe class.

    private MainScreen cookBook;
    ArrayList<Ingredient> chosenAddIngredients = new ArrayList<Ingredient>();
    String chosenType;
    String chosenCategory;
    Spinner typeSpinner;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AddRecipe.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categoriesRecipe));

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(AddRecipe.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.typesRecipe));

        categorySpinner = (Spinner) findViewById(R.id.getCreateRecipeCategorySpinner);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //CREATE AND SET SPINNER FOR TYPE.
        typeSpinner = (Spinner) findViewById(R.id.getCreateRecipeTypeSpinner);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        populateIngredientListView();
    }

    public void populateIngredientListView(){
        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(AddRecipe.this,cookBook.get_cookBookIngredients());
        ListView ingredientList = (ListView)findViewById(R.id.ingredientsToAddListView);
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

    public void clickAddRecipeDirections(View view){
        String recipeName = ((EditText)findViewById(R.id.getCreateRecipeNameEditView)).getText().toString();
        String cookTime = ((EditText)findViewById(R.id.getCreateCookEditView)).getText().toString();
        String prepTime = ((EditText)findViewById(R.id.getCreatePrepEditView)).getText().toString();
        String caloriesTime = ((EditText)findViewById(R.id.getCreateCaloriesEditView)).getText().toString();

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

                Intent intent = new Intent(AddRecipe.this,AddRecipeDirections.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("cookBook",cookBook);
                bundle.putSerializable("recipeName",recipeName);
                bundle.putSerializable("chosenCategory",chosenCategory);
                bundle.putSerializable("chosenType",chosenType);
                bundle.putSerializable("chosenAddIngredients",chosenAddIngredients);
                bundle.putSerializable("prepIntTime",prepIntTime);
                bundle.putSerializable("cookIntTime",cookIntTime);
                bundle.putSerializable("caloriesIntTime",caloriesIntTime);

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
