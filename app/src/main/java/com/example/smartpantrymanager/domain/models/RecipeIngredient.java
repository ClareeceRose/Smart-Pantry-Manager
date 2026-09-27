package com.example.smartpantrymanager.domain.models;

import com.example.smartpantrymanager.core.enums.UnitType;

public class RecipeIngredient {

    private int recipeIngredientId;
    private int ingredientId;
    private int recipeId;
    private int recipeIngredientRequiredQty;
    private UnitType recipeIngredientUnit;

    public RecipeIngredient(
            UnitType recipeIngredientUnit,
            int recipeIngredientRequiredQty,
            int recipeId,
            int ingredientId,
            int recipeIngredientId
    ) {
        this.recipeIngredientUnit = recipeIngredientUnit;
        this.recipeIngredientRequiredQty = recipeIngredientRequiredQty;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.recipeIngredientId = recipeIngredientId;
    }

    public int getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public void setRecipeIngredientId(int recipeIngredientId) {
        this.recipeIngredientId = recipeIngredientId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeIngredientRequiredQty() {
        return recipeIngredientRequiredQty;
    }

    public void setRecipeIngredientRequiredQty(int recipeIngredientRequiredQty) {
        this.recipeIngredientRequiredQty = recipeIngredientRequiredQty;
    }

    public UnitType getRecipeIngredientUnit() {
        return recipeIngredientUnit;
    }

    public void setRecipeIngredientUnit(UnitType recipeIngredientUnit) {
        this.recipeIngredientUnit = recipeIngredientUnit;
    }
}
