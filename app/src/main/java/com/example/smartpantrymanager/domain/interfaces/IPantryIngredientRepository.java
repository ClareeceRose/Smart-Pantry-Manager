package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.PantryIngredient;

import java.util.HashSet;

public interface IPantryIngredientRepository {
    HashSet<PantryIngredient> getAllPantryIngredients();
    void addPantryIngredient(PantryIngredient pantryIngredient);
    void deletePantryIngredient(PantryIngredient pantryIngredient);
    void updatePantryIngredient(PantryIngredient pantryIngredient);
}
