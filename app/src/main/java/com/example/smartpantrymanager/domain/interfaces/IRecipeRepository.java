package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.HashSet;

public interface IRecipeRepository {
    HashSet<RecipeIngredient> getAllRecipeIngredients();
}
