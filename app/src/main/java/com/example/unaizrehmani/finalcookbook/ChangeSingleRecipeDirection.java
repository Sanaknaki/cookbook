package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ChangeSingleRecipeDirection extends AppCompatActivity {

    private MainScreen cookBook;
    private int directionPosition;
    private String oldDirectionName;
    private String oldName;
    private Recipe currentRecipe;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_single_recipe_direction);

        oldDirectionName = (String)getIntent().getExtras().getSerializable("oldDirectionName");
        cookBook = (MainScreen)getIntent().getExtras().getSerializable("cookBook");
        directionPosition = (Integer)getIntent().getExtras().getSerializable("direction position");
        currentRecipe = (Recipe) getIntent().getExtras().getSerializable("currentRecipe");
        oldName = (String) getIntent().getExtras().getSerializable("oldName");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        final EditText myEditText = (EditText)findViewById(R.id.editTextChange);
        myEditText.setText(oldDirectionName);

        Button save = (Button)findViewById(R.id.saveRecipeDirectionButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText myEditText = (EditText)findViewById(R.id.editTextChange);
                String newDirectionName = myEditText.getText().toString();

                currentRecipe.setRecipeDirections(directionPosition,newDirectionName);

                Intent intent = new Intent(ChangeSingleRecipeDirection.this,SingleEditRecipeDirectionClass.class);


                //typeList.add("Change Single Recipe Direction.");
                //categoryList.add("Change Single Recipe Direction.");

                Bundle b = new Bundle();
                b.putSerializable("cookBook",cookBook);
                b.putSerializable("currentRecipe",currentRecipe);
                b.putSerializable("oldName",oldName);
                b.putSerializable("typeList",typeList);
                b.putSerializable("categoryList",categoryList);

                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }
}
