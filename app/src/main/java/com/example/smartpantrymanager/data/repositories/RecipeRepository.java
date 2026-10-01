package com.example.smartpantrymanager.data.repositories;

import com.example.smartpantrymanager.domain.interfaces.IRecipeRepository;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.HashSet;

public class RecipeRepository implements IRecipeRepository {
    public HashSet<RecipeIngredient> getAllRecipeIngredients(){
        return null;
    }
}
