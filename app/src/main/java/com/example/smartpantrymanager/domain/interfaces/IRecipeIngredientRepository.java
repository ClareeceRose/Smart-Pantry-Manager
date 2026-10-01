package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.HashSet;

public interface IRecipeIngredientRepository {
    HashSet<RecipeIngredient> getAllRecipeIngredients();
}
