package com.example.smartpantrymanager.data.repositories;

import com.example.smartpantrymanager.domain.interfaces.IPantryIngredientRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;

import java.util.HashSet;

public class PantryIngredientRepository implements IPantryIngredientRepository {
    public HashSet<PantryIngredient> getAllPantryIngredients(){
        return null;
    }
    public void addPantryIngredient(PantryIngredient pantryIngredient){}
    public void deletePantryIngredient(PantryIngredient pantryIngredient){}
    public void updatePantryIngredient(PantryIngredient pantryIngredient){}
}
