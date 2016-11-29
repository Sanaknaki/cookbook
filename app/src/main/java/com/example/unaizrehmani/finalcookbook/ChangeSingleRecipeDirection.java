package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeSingleRecipeDirection extends AppCompatActivity {

    private MainScreen cookBook;
    private int directionPosition;
    private String toChange;
    private int positionOfRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_single_recipe_direction);

        toChange = (String)getIntent().getExtras().getSerializable("toChange");
        cookBook = (MainScreen)getIntent().getExtras().getSerializable("cookBook");
        directionPosition = (Integer)getIntent().getExtras().getSerializable("direction position");
        positionOfRecipe = (Integer) getIntent().getExtras().getSerializable("recipe position");

        final EditText myEditText = (EditText)findViewById(R.id.editTextChange);
        myEditText.setText(toChange);

        Button save = (Button)findViewById(R.id.saveRecipeDirectionButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText myEditText = (EditText)findViewById(R.id.editTextChange);

                String newString = myEditText.getText().toString();
                cookBook.get_cookBookRecipes().get(positionOfRecipe).setRecipeDirections(directionPosition,newString);

                Intent intent = new Intent(ChangeSingleRecipeDirection.this,SingleEditRecipeDirectionClass.class);

                Bundle b = new Bundle();
                b.putSerializable("cookBook",cookBook);
                b.putSerializable("positionRecipe",positionOfRecipe);

                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }
}
