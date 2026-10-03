package com.example.smartpantrymanager.data.repositories;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.domain.interfaces.IRecipeRepository;
import com.example.smartpantrymanager.domain.models.Recipe;

import java.util.ArrayList;

public class RecipeRepository implements IRecipeRepository {
    private DBHelper dbHelper;

    public RecipeRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // this method returns all recipes from the Recipe table
    public ArrayList<Recipe> getAllRecipes() {
        // where the recipes will be stored
        ArrayList<Recipe> recipes = new ArrayList<>();

        // we can use this db to get our data
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // the query
        Cursor cursor = db.rawQuery(
                "SELECT Recipe_Id, Recipe_Name, Recipe_Instructions " +
                        "FROM Recipe",
                null );

        // this tries to read the cursor if it contains at least one row.
        if (cursor.moveToFirst()) {

            do {
                int recipeId =
                        cursor.getInt(cursor.getColumnIndexOrThrow("Recipe_Id"));
                String recipeName =
                        cursor.getString(cursor.getColumnIndexOrThrow("Recipe_Name"));
                String recipeInstructions =
                        cursor.getString(cursor.getColumnIndexOrThrow("Recipe_Instructions"));

                // creates and assigns a new recipe
                Recipe recipe = new Recipe(recipeId, recipeName, recipeInstructions);

                // and this adds it to the arraylist of recipes
                recipes.add(recipe);

            }
            while (cursor.moveToNext()); // keeps moving to the next row
        }

        cursor.close(); // closes / releases ref to cursor

        return recipes; // returns the arraylist
    }

}
