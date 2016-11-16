package com.uottawa.alisanaknaki.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by unaizrehmani on 2016-11-16.
 */

public class Recipe extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recipes);

        Button seeRecipeInfo = (Button) findViewById(R.id.chickenNoodleSoup);
        seeRecipeInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Recipe.this,RecipeInfo.class);
                startActivity(intent);
            }
        });
    }
}
