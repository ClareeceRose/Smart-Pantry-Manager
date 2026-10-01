package com.example.smartpantrymanager.domain.services;

/*

    this is where strict matching occurs.
    the purpose of this strict matching is to ensure that recipes are displayed based on the
    ingredients the user has in their pantry and the ones that belong to the actual
    recipe.

    Ingredients will be matched based their Ingredient_Id and shared Qty (which will be converted to
    the same unit to properly match)

*/
public class RecipeMatchingService {

    public RecipeMatchingService() {
    }
}
