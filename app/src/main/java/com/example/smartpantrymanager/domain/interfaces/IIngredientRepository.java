package com.example.smartpantrymanager.domain.interfaces;

import com.example.smartpantrymanager.domain.models.Ingredient;

import java.util.HashSet;

public interface IIngredientRepository {
    Ingredient findIngredientByName(String name);
    Ingredient findIngredientById(int id);
    int addIngredient(Ingredient ingredient);
}
