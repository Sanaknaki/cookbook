package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SingleEditRecipeDirectionClass extends AppCompatActivity {

    private MainScreen cookBook;
    private Recipe currentRecipe;
    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_edit_recipe_direction_class);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        currentRecipe = (Recipe) getIntent().getExtras().getSerializable("currentRecipe");
        oldName = (String) getIntent().getExtras().getSerializable("oldName");

        populateListView();

        Button finished = (Button)findViewById(R.id.changeSingleRecipeDirectionButton);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to mainscreen with changes.
                Intent intent = new Intent(SingleEditRecipeDirectionClass.this,MainScreen.class);

                Bundle b = new Bundle();

                cookBook.get_cookBookRecipes().remove(new Recipe(oldName));

                cookBook.add_cookBookRecipe(currentRecipe);

                b.putSerializable("cookBook",cookBook);

                intent.putExtras(b);

                startActivity(intent);

            }
        });
    }

    public void populateListView(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                SingleEditRecipeDirectionClass.this,
                android.R.layout.simple_list_item_1,
                currentRecipe.getRecipeDirections());

        ListView myListView = (ListView) findViewById(R.id.getEditDirectionListView);

        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String oldDirectionName = (String)adapterView.getItemAtPosition(i);

                Intent intent= new Intent(SingleEditRecipeDirectionClass.this,ChangeSingleRecipeDirection.class);
                Bundle b = new Bundle();
                b.putSerializable("oldDirectionName",oldDirectionName);
                b.putSerializable("cookBook",cookBook);
                b.putSerializable("direction position",i);
                b.putSerializable("currentRecipe",currentRecipe);
                b.putSerializable("oldName",oldName);

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

}
