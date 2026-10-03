package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.ArrayList;

public interface IRecipeIngredientRepository {
    ArrayList<RecipeIngredient> getAllRecipeIngredients();
}
