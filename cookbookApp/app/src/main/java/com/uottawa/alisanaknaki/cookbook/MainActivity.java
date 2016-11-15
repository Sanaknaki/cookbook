package com.uottawa.alisanaknaki.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button advanceToFindRecipe = (Button) findViewById(R.id.findRecipeButton);
        advanceToFindRecipe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,FindRecipe.class);
                startActivity(intent);
            }
        });


    }
}
