package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.data.repositories.RecipeRepository;
import com.example.smartpantrymanager.domain.models.Recipe;

import java.util.ArrayList;

public class RecipeService {
    private RecipeRepository recipeRepo;
    private ArrayList<Recipe> recipes;

    // constructor
    public RecipeService(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    // method used to return an arraylist of every recipe in the db table using the recipes repo
    public ArrayList<Recipe> getAllRecipes(){
        recipes = recipeRepo.getAllRecipes();
        return recipes;
    }
}
