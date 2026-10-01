package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.data.repositories.IngredientRepository;
import com.example.smartpantrymanager.domain.models.Ingredient;

public class IngredientService {
    private IngredientRepository ingredientRepo;
    private Ingredient ingredient;

    public IngredientService(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    public Ingredient findIngredient(String name){
        ingredient = ingredientRepo.findIngredientByName(name);

        // this is if the ingredient could not be found.
        if (ingredient == null){
            return null;
        }

        return ingredient; // a found ingredient
    }

    public void addIngredient(Ingredient ingredient){

        ingredientRepo.addIngredient(ingredient);

    }

}
