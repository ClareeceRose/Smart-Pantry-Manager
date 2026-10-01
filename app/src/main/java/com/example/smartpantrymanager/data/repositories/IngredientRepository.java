package com.example.smartpantrymanager.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.domain.interfaces.IIngredientRepository;
import com.example.smartpantrymanager.domain.models.Ingredient;

// okay, so this file's responsibilities are simple
// it needs to be able to find an Ingredient and add a new one if it does not exist in the Ingredients table
public class IngredientRepository implements IIngredientRepository {
    private DBHelper dbHelper;

    public IngredientRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // this method is used to find an ingredient by name
    public Ingredient findIngredientByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT Ingredient_Id, Ingredient_Name " +
                        "FROM Ingredient " +
                        "WHERE Ingredient_Name = ?",
                new String[]{name}
        );

        Ingredient ingredient = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("Ingredient_Id")
            );

            String ingredientName = cursor.getString(
                    cursor.getColumnIndexOrThrow("Ingredient_name")
            );

            ingredient = new Ingredient(id, ingredientName);
        }

        cursor.close();

        return ingredient;

    }

    // method is used to add a new ingredient if it doesn't exist in the Ingredient table first
    public void addIngredient(Ingredient ingredient){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Ingredient_Name", ingredient.getIngredientName());
        db.insert("Ingredient", null, values);
    }

}
