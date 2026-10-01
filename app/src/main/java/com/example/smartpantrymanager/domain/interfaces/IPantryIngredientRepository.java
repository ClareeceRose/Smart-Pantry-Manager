package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.PantryIngredient;

import java.util.ArrayList;

public interface IPantryIngredientRepository {
    ArrayList<PantryIngredient> getAllPantryIngredients();
    void addPantryIngredient(PantryIngredient pantryIngredient);
    void deletePantryIngredient(PantryIngredient pantryIngredient);
    void updatePantryIngredient(PantryIngredient pantryIngredient);
}
