package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.core.enums.UnitType;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.ArrayList;

/*

    this is where strict matching occurs.
    the purpose of this strict matching is to ensure that recipes are displayed based on the
    ingredients the user has in their pantry and the ones that belong to the actual
    recipe.

    Ingredients will be matched based their Ingredient_Id and shared Qty (which will be converted to
    the same unit to properly match)

*/
public class RecipeMatchingService {
    private ArrayList<RecipeIngredient> recipeIngredients;
    private ArrayList<PantryIngredient> pantryIngredients;

    public RecipeMatchingService(ArrayList<RecipeIngredient> recipeIngredients, ArrayList<PantryIngredient> pantryIngredients) {
        this.recipeIngredients = recipeIngredients;
        this.pantryIngredients = pantryIngredients;
    }

    // we'd need to ensure that all ingredients present in Recipe_Ingredient table are in Pantry_Ingredient table
    public boolean doIngredientIdsMatch(){

        for (RecipeIngredient recipeIngredient : recipeIngredients){

            boolean found = false;

            for (PantryIngredient pantryIngredient : pantryIngredients){

                if (recipeIngredient.getIngredientId()
                        == pantryIngredient.getIngredientId()){

                    found = true;
                    break;

                }
            }

            // this means that if even ONE required ingredient isn't
            // in the pantry, this recipe doesn't match.
            if (!found) {

                return false;

            }
        }

        return true;
    }

    // We'd also need to check if the quantities are enough.
    // If the units are different, they will be converted
    // to a common unit before being compared.

    public boolean isQuantityEnough(){

        UnitConversionService unitConversionService = new UnitConversionService();

        for (RecipeIngredient recipeIngredient : recipeIngredients){

            boolean found = false;

            for (PantryIngredient pantryIngredient : pantryIngredients){

                // we need to find the pantry ingredient that matches
                // the current recipe ingredient.
                if (recipeIngredient.getIngredientId() == pantryIngredient.getIngredientId()){

                    found = true;

                    double requiredQty = recipeIngredient.getRecipeIngredientRequiredQty();
                    double pantryQty = pantryIngredient.getPantryIngredientQty();

                    UnitType requiredUnit = recipeIngredient.getRecipeIngredientUnit();
                    UnitType pantryUnit = pantryIngredient.getPantryIngredientUnit();

                    // and the conversion and comparison happens here

                    // we first will check if it's a mass unit
                    if (isMassUnit(requiredUnit) && isMassUnit(pantryUnit)){

                        // this converts both of the quantities to grams.
                        double requiredGrams =
                                unitConversionService.convertToGrams(requiredQty, requiredUnit);

                        double pantryGrams =
                                unitConversionService.convertToGrams(pantryQty, pantryUnit);

                        // now we check if the pantry contains less than enough,
                        // meaning the recipe does not match, returning false.

                        if (pantryGrams < requiredGrams){

                            return false;

                        }
                    }
                    // then volume unit
                    else if (isVolumeUnit(requiredUnit) && isVolumeUnit(pantryUnit)){

                        // converts both quantities to milliliters.

                        double requiredMilliliters =
                                unitConversionService.convertToMilliliters(requiredQty, requiredUnit);
                        double pantryMilliliters =
                                unitConversionService.convertToMilliliters(pantryQty, pantryUnit);

                        // if the pantry doesn't contain enough, then the recipe does not match.
                        if (pantryMilliliters < requiredMilliliters){

                            return false;

                        }

                    }
                    // the code within this statement executes if the units are spoon units
                    else if (isSpoonUnit(requiredUnit) && isSpoonUnit(pantryUnit)){

                        // then it converts both quantities to teaspoons.
                        double requiredTeaspoons =
                                unitConversionService.convertToTeaspoons(requiredQty, requiredUnit);
                        double pantryTeaspoons =
                                unitConversionService.convertToTeaspoons(pantryQty, pantryUnit);

                        // and if the pantry doesn't contain enough,
                        // the recipe does not match.
                        if (pantryTeaspoons < requiredTeaspoons){

                            return false;

                        }
                    }
                    // now we check if the units are pieces (basically another version of count)
                    else if (requiredUnit == UnitType.PIECE && pantryUnit == UnitType.PIECE){

                        // and pieces DON'T NEED conversion.
                        if (pantryQty < requiredQty){

                            return false;

                        }
                    }
                    // now this is to account for incompatible units
                    else {

                        // for example: GRAM <---> LITER and PIECE <---> GRAM

                        return false;

                    }

                    // we found the matching pantry ingr, so there's no need
                    // to check the remaining pantry ingredients
                    break;
                }
            }

            // and if the recipe ingredient was not found in the pantry,
            // then the qty requirement can't be satisfied.
            if(!found){
                return false;
            }
        }

        // if every recipe ingredient was found and had enough quantity,
        // the recipe passes the quantity check, returning true
        return true;
    }

    // the method for mass unit checks
    private boolean isMassUnit(UnitType unit){

        return unit == UnitType.GRAM
                || unit == UnitType.KILOGRAM
                || unit == UnitType.OUNCE
                || unit == UnitType.POUND;
    }

    // this is for volume checks
    private boolean isVolumeUnit(UnitType unit){

        return unit == UnitType.MILLILITER
                || unit == UnitType.LITER
                || unit == UnitType.FLUID_OUNCE;
    }

    // and the last unit check is for spoon checks
    private boolean isSpoonUnit(UnitType unit){

        return unit == UnitType.TEASPOON
                || unit == UnitType.TABLESPOON;
    }

    // then we'd need to return true or false on whether there's enough (if any) of the same ingredients
    // in the pantry. If one recipe ingredient is missing or low in qty, then it will return false
    // resulting in that recipe being left out of the suggestedRecipe ArrayList (not displayed)
    public boolean doIngredientsMatch(){
        if (doIngredientIdsMatch() && isQuantityEnough()){
            return true;
        }
        return false;
    }
}
