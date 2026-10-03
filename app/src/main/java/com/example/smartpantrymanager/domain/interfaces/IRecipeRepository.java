package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.Recipe;

import java.util.ArrayList;

public interface IRecipeRepository {
    ArrayList<Recipe> getAllRecipes();
}
