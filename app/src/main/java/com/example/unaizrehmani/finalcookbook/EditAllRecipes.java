package com.example.unaizrehmani.finalcookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditAllRecipes extends AppCompatActivity {

    private MainScreen cookBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_recipes);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

    }
}
