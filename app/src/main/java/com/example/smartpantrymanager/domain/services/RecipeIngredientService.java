package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.data.repositories.RecipeIngredientRepository;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.ArrayList;


public class RecipeIngredientService {
    private RecipeIngredientRepository recipeIngredientRepo;
    private ArrayList<RecipeIngredient> recipeIngredients;

    // constructor
    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepo) {
        this.recipeIngredientRepo = recipeIngredientRepo;
    }

    // this method returns all recipe ingredients in an arraylist using the recipe ingredient repo
    public ArrayList<RecipeIngredient> getAllRecipeIngredients(){
        recipeIngredients = recipeIngredientRepo.getAllRecipeIngredients();
        return recipeIngredients;
    }
}
