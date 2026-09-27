package com.example.smartpantrymanager.domain.models;

import com.example.smartpantrymanager.core.enums.UnitType;

public class PantryIngredient {
    private int pantryIngredientId;
    private int ingredientId;
    private int pantryIngredientQty;
    private UnitType pantryIngredientUnit;

    public PantryIngredient(
            int pantryIngredientId,
            int ingredientId,
            int pantryIngredientQty,
            UnitType pantryIngredientUnit
    ) {
        this.pantryIngredientId = pantryIngredientId;
        this.ingredientId = ingredientId;
        this.pantryIngredientQty = pantryIngredientQty;
        this.pantryIngredientUnit = pantryIngredientUnit;
    }

    public int getPantryIngredientId() {
        return pantryIngredientId;
    }

    public void setPantryIngredientId(int pantryIngredientId) {
        this.pantryIngredientId = pantryIngredientId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getPantryIngredientQty() {
        return pantryIngredientQty;
    }

    public void setPantryIngredientQty(int pantryIngredientQty) {
        this.pantryIngredientQty = pantryIngredientQty;
    }

    public UnitType getPantryIngredientUnit() {
        return pantryIngredientUnit;
    }

    public void setPantryIngredientUnit(UnitType pantryIngredientUnit) {
        this.pantryIngredientUnit = pantryIngredientUnit;
    }
}
