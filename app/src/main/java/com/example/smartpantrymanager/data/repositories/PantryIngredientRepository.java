package com.example.smartpantrymanager.data.repositories;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.smartpantrymanager.core.enums.UnitType;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.domain.interfaces.IPantryIngredientRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;

import java.util.ArrayList;

public class PantryIngredientRepository implements IPantryIngredientRepository {
    private DBHelper dbHelper;

    public PantryIngredientRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public ArrayList<PantryIngredient> getAllPantryIngredients(){

        ArrayList<PantryIngredient> pantryIngredients = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT PI_Id, Ingredient_Id, PI_Qty, PI_Unit " +
                        "FROM Pantry_Ingredient ",
                null
        );

        PantryIngredient pantryIngredient = null;

        if (cursor.moveToFirst()) {
            int piId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("PI_Id")
            );

            int ingredientId = cursor.getInt(
                    cursor.getColumnIndexOrThrow("Ingredient_Id")
            );

            int piQty = cursor.getInt(
                    cursor.getColumnIndexOrThrow("PI_Qty")
            );

            String piUnitString = cursor.getString(
                    cursor.getColumnIndexOrThrow("PI_Unit")
            );

            UnitType piUnit = UnitType.valueOf(piUnitString);

            pantryIngredient = new PantryIngredient(piId, ingredientId, piQty, piUnit);
            pantryIngredients.add(pantryIngredient);
        }

        cursor.close();

        return pantryIngredients;
    }

    public void addPantryIngredient(PantryIngredient pantryIngredient){}
    public void deletePantryIngredient(PantryIngredient pantryIngredient){}
    public void updatePantryIngredient(PantryIngredient pantryIngredient){}
}
