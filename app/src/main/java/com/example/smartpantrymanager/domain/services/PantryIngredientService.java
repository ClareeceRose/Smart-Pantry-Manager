package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.data.repositories.PantryIngredientRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;

import java.util.ArrayList;

public class PantryIngredientService {
    private PantryIngredientRepository pantryIngredientRepo;
    private ArrayList<PantryIngredient> pantryIngredients;

    public PantryIngredientService(PantryIngredientRepository pantryIngredientRepo) {
        this.pantryIngredientRepo = pantryIngredientRepo;
    }

    // method for getting all pantry ingredients in the form of an array list
    // I had initially wanted it stored in a hashset, but I'll stick to what I know best for now
    public ArrayList<PantryIngredient> getAllPantryIngredients(){

        pantryIngredients = pantryIngredientRepo.getAllPantryIngredients();
        return pantryIngredients;

    }

    // method for adding a new pantry ingredient instance
    public void addPantryIngredient(PantryIngredient pantryIngredient){
        pantryIngredientRepo.addPantryIngredient(pantryIngredient);
    }
    // method for delete a pantry ingredient
    public void deletePantryIngredient(PantryIngredient pantryIngredient){
        pantryIngredientRepo.deletePantryIngredient(pantryIngredient);
    }
    public void updatePantryIngredient(PantryIngredient pantryIngredient){}
}
