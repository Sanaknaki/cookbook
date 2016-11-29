package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteRecipe extends AppCompatActivity {

    private MainScreen cookBook;
    ArrayList<String> cookBookStringRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recipe);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");

        cookBookStringRecipes = new ArrayList<String>();

        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
            cookBookStringRecipes.add(cookBook.get_cookBookRecipes().get(i).getRecipeName());
        }

        populateListView();

        Button delete = (Button) findViewById(R.id.deleteDARecipe);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText)findViewById(R.id.editText);
                String userStringInput = userInput.getText().toString();

                Toast.makeText(getApplicationContext(), userStringInput, Toast.LENGTH_SHORT).show();

                if(userStringInput!=null && !userStringInput.equals("")){
                    if (cookBook.get_cookBookRecipes().contains(new Recipe(userStringInput))){
                        cookBook.get_cookBookRecipes().remove(new Recipe(userStringInput));
                        Toast.makeText(getApplicationContext(), ("Removed: " +userStringInput), Toast.LENGTH_SHORT).show();
                        populateListView();
                    }
                }
            }
        });

        Button finished = (Button) findViewById(R.id.finishedDeleting);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (DeleteRecipe.this,MainScreen.class);
                Bundle b = new Bundle();
                b.putSerializable("cookBook",cookBook);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void populateListView(){
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(DeleteRecipe.this,
                android.R.layout.simple_list_item_1,
               cookBookStringRecipes);

        ListView listView = (ListView)findViewById(R.id.deleteListView);

        listView.setAdapter(myArrayAdapter);

    }

}
