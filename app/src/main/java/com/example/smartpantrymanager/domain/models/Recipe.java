package com.example.smartpantrymanager.domain.models;

public class Recipe {
    private int recipeId;
    private String recipeName;
    private String recipeInstructions;

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

}
