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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class findRecipe extends AppCompatActivity {

    private MainScreen cookBook;

    private ArrayList<Ingredient> cookBookIngredients = new ArrayList<Ingredient>();
    private ArrayList<Recipe> cookBookRecipes = new ArrayList<Recipe>();

    ArrayList<Ingredient> chosenIngredients = new ArrayList<Ingredient>();
    String chosenType;
    String chosenCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

        cookBookIngredients = cookBook.get_cookBookIngredients();
        cookBookRecipes = cookBook.get_cookBookRecipes();

        //CREATE AND SET SPINNER FOR CATEGORY.

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(findRecipe.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categories));

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(findRecipe.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.types));

        Spinner categorySpinner = (Spinner) findViewById(R.id.spinnerCategory);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //CREATE AND SET SPINNER FOR TYPE.
        Spinner typeSpinner = (Spinner) findViewById(R.id.spinnerType);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        //SAVING CHOICES FOR SELECTED TYPE AND CATEGORY
        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        //SET TEXTVIEWS FOR SPINNERS
        TextView categoryTextView = (TextView) findViewById(R.id.categoryTextView);
        categoryTextView.setText("CATEGORY");

        TextView typeTextView = (TextView) findViewById(R.id.typeTextView);
        typeTextView.setText("TYPE");

        populateIngredientListView();
    }

    public void populateIngredientListView(){
        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(findRecipe.this,cookBookIngredients);
        ListView ingredientList = (ListView)findViewById(R.id.includeIngredientListView);
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
                        chosenIngredients.add(curIngredient);
                    } else {
                        chosenIngredients.remove(curIngredient);
                    }

                }
            });

            ingredientSelected.setChecked(curIngredient.is_selected());
            ingredientSelected.setTag(curIngredient);

            return customView;
        }

    }

    public void clickNextExcludeIngredients(View view){
        Intent intent = new Intent(this, excludeRecipe.class);

        chosenType = ((Spinner) findViewById(R.id.spinnerType)).getSelectedItem().toString();
        chosenCategory = ((Spinner) findViewById(R.id.spinnerCategory)).getSelectedItem().toString();

        Bundle bundle = new Bundle();

        //uncheck is selected for cookBook Ingredients
        for(int i =0 ; i<cookBook.get_cookBookIngredients().size(); i++){
            cookBook.get_cookBookIngredients().get(i).set_selected(false);
        }

        bundle.putSerializable("cookBook",cookBook);
        bundle.putSerializable("chosenIngredients", chosenIngredients);
        bundle.putSerializable("chosenType", chosenType);
        bundle.putSerializable("chosenCategory", chosenCategory);

        intent.putExtras(bundle);

        startActivity(intent);
    }

}
