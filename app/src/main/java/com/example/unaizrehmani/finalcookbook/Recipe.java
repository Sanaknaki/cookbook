package com.example.unaizrehmani.finalcookbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Recipe implements Serializable{

    private ArrayList<Ingredient> _recipeIngredients = new ArrayList<Ingredient>();
    private ArrayList<String> _recipeDirections = new ArrayList<String>();
    private String _recipeName;
    private String _recipeCategory;
    private String _recipeType;

    public Recipe(String newName, String newCategory, String newType){
        _recipeName = newName;
        _recipeType = newType;
        _recipeCategory = newCategory;
    }


    public Recipe(String newName, String newCategory, String newType,
                  ArrayList<Ingredient> newIngredients, ArrayList<String> newDirections){
        _recipeName = newName;
        _recipeIngredients = newIngredients;
        _recipeDirections = newDirections;
        _recipeType = newType;
        _recipeCategory = newCategory;

        //Collections.sort(_recipeIngredients);
        Collections.sort(_recipeDirections);
        Collections.sort(_recipeIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });
    }

    public String getRecipeName(){
        return _recipeName;
    }

    public void setRecipeName(String newName){ //to be used when editing recipe name
        _recipeName = newName;
    }

    public ArrayList<Ingredient> getRecipeIngredients(){
        return _recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<Ingredient> newIngredients){ //to be used when editing recipe ingredients
        _recipeIngredients = newIngredients;
        Collections.sort(_recipeIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });    }

    public ArrayList<String> getRecipeDirections(){
        return _recipeDirections;
    }

    public void setRecipeDirections(ArrayList<String> newDirections){ //to be used when editing recipe directions
        _recipeDirections = newDirections;
        Collections.sort(_recipeDirections);
    }

    public void addRecipeIngredient(Ingredient newIngredient){
        _recipeIngredients.add(newIngredient);
        Collections.sort(_recipeIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });    }

    public void addRecipeDirection(String newDirection){
        _recipeDirections.add(newDirection);
        Collections.sort(_recipeDirections);
    }

    public void removeRecipeIngredient(int index){
        _recipeIngredients.remove(index);
    }

    public void removeDirectionIngredient(int index){
        _recipeDirections.remove(index);
    }

    public String getRecipeCategory(){
        return _recipeCategory;
    }

    public String getRecipeType(){
        return _recipeType;
    }

    public void setRecipeCategory(String newCategory){
        _recipeCategory = newCategory;
    }

    public void setRecipeType(String newRecipeType){
        _recipeType = newRecipeType;
    }


}
