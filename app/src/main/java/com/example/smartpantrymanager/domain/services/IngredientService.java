package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.data.repositories.IngredientRepository;
import com.example.smartpantrymanager.domain.models.Ingredient;

public class IngredientService {
    private IngredientRepository ingredientRepo;
    private Ingredient ingredient;

    // parameterized constructor for IngredientService
    public IngredientService(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    // method used to find an ingredient by name
    public Ingredient findIngredientByName(String name){
        ingredient = ingredientRepo.findIngredientByName(name);

        // this is if the ingredient could not be found.
        if (ingredient == null){
            return null;
        }

        return ingredient; // a found ingredient
    }

    // finds an ingredient by id
    public Ingredient findIngredientById(int id){
        ingredient = ingredientRepo.findIngredientById(id);

        // this is if the ingredient could not be found.
        if (ingredient == null){
            return null;
        }

        return ingredient; // a found ingredient
    }

    // method used to add a new ingredient
    public void addIngredient(Ingredient ingredient){

        int generatedId = ingredientRepo.addIngredient(ingredient);
        ingredient.setIngredientId(generatedId);

    }

}
