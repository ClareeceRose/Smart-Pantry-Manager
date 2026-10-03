package com.example.smartpantrymanager.data.repositories;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartpantrymanager.core.enums.UnitType;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.domain.interfaces.IRecipeIngredientRepository;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.ArrayList;

public class RecipeIngredientRepository implements IRecipeIngredientRepository {
    private DBHelper dbHelper;
    public RecipeIngredientRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // method used to get all recipe ingredients from the Recipe_Ingredient table
    public ArrayList<RecipeIngredient> getAllRecipeIngredients() {

        // stores recipe ingredients in arraylist
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();

        // creates a local db to read data from
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // the cursor acts as the pointer
        Cursor cursor = db.rawQuery(
                "SELECT RI_Id, Recipe_Id, Ingredient_Id, RI_Required_Qty, RI_Unit " +
                        "FROM Recipe_Ingredient",
                null );

        // tries to read the cursor if it contains at least one row.
        if (cursor.moveToFirst()) {

            // must be done at least once which is why I used a do-while loop
            do {
                // gets data from each column and stores them
                int recipeIngredientId =
                        cursor.getInt(cursor.getColumnIndexOrThrow("RI_Id"));
                int recipeId =
                        cursor.getInt(cursor.getColumnIndexOrThrow("Recipe_Id"));
                int ingredientId =
                        cursor.getInt(cursor.getColumnIndexOrThrow("Ingredient_Id"));
                double requiredQty =
                        cursor.getDouble(cursor.getColumnIndexOrThrow("RI_Required_Qty"));
                String unitString =
                        cursor.getString(cursor.getColumnIndexOrThrow("RI_Unit"));

                UnitType unit = convertUnit(unitString); // attempts to convert + store unit from db to UnitType

                // creates a new recipe ingredient
                RecipeIngredient recipeIngredient =
                        new RecipeIngredient(
                                unit,
                                requiredQty,
                                recipeId,
                                ingredientId,
                                recipeIngredientId );

                // adds this new instance to the arraylist
                recipeIngredients.add(recipeIngredient);
            }
            // moves cursor to the next row
            while (cursor.moveToNext());

        }

        cursor.close(); // removes ref to cursor when no more rows exist to read

        return recipeIngredients; // and these are the recipe ingredients returned

    }

    // this was a MUCH-needed method since the units in the recipe ingredient table couldn't just
    // convert to the program's unit type with a basic .toUpper() function since
    // most of them are abbreviated.
    private UnitType convertUnit(String unitString){

        switch (unitString.toLowerCase()){

            case "g": // like this, for example.
                return UnitType.GRAM; // so now we convert g to GRAM

            case "kg":
                return UnitType.KILOGRAM;

            case "l":
                return UnitType.LITER;

            case "ml":
                return UnitType.MILLILITER;

            case "piece":
                return UnitType.PIECE;

            case "tsp":
                return UnitType.TEASPOON;

            case "tbsp":
                return UnitType.TABLESPOON;

            case "oz":
                return UnitType.OUNCE;

            case "pound":
                return UnitType.POUND;

            case "fluid oz":
                return UnitType.FLUID_OUNCE;

            default:
                // an argument is thrown if an odd unit is returned from the db.
                throw new IllegalArgumentException(
                        "Unknown unit: " + unitString
                );
        }
    }
}
