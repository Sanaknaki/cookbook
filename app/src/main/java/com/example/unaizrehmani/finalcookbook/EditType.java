package com.example.unaizrehmani.finalcookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class EditType extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_type);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");


    }
}
