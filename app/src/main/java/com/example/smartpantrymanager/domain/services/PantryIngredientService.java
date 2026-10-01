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

    public ArrayList<PantryIngredient> getAllPantryIngredients(){

        pantryIngredients = pantryIngredientRepo.getAllPantryIngredients();
        return pantryIngredients;

    }
    public void addPantryIngredient(PantryIngredient pantryIngredient){}
    public void deletePantryIngredient(PantryIngredient pantryIngredient){}
    public void updatePantryIngredient(PantryIngredient pantryIngredient){}
}
