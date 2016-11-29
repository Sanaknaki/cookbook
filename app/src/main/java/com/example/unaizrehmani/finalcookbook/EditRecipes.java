package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class EditRecipes extends AppCompatActivity {

    private MainScreen cookBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipes);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

    }

    public void clickAddRecipe(View view){
        Intent intent = new Intent(EditRecipes.this,AddRecipe.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void clickEditRecipe(View view){
        Intent intent = new Intent(EditRecipes.this,EditAllRecipes.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void clickDeleteRecipe(View view){
        Intent intent = new Intent(EditRecipes.this,DeleteRecipe.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        intent.putExtras(b);
        startActivity(intent);
    }
}
