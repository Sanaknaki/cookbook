package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextIngredient extends AppCompatActivity {

    private MainScreen cookBook;
    private String oldIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_ingredient);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        oldIngredient = (String) getIntent().getExtras().getSerializable("ingredientToEdit");

        TextView myText = (TextView) findViewById(R.id.ingreidentToEditTextView);
        myText.setText(oldIngredient);


        Button save = (Button) findViewById(R.id.editSingleIngredientNameButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (EditTextIngredient.this, EditIngredients.class);

                Bundle b = new Bundle();

                String newIngredient = ((EditText) findViewById(R.id.newIngredientNameEditText)).getText().toString();

                for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){

                    if(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().contains(new Ingredient(oldIngredient))){
                        cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().remove(new Ingredient(oldIngredient));
                        cookBook.get_cookBookRecipes().get(i).addRecipeIngredient(new Ingredient(newIngredient));
                    }

                }

                for(int i = 0; i<cookBook.get_cookBookIngredients().size();i++){
                    if(cookBook.get_cookBookIngredients().contains(new Ingredient(oldIngredient))){
                        cookBook.get_cookBookIngredients().remove(new Ingredient(oldIngredient));
                        cookBook.add_cookBookIngredient(newIngredient);
                    }
                }

                b.putSerializable("cookBook",cookBook);

                intent.putExtras(b);

                startActivity(intent);

                //Toast.makeText(getApplicationContext(), ingredientChange, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
