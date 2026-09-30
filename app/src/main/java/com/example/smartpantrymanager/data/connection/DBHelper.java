package com.example.smartpantrymanager.data.connection;

// imports
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

// this class is meant to create and manage the database for the smart pantry manager mobile app
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(
                context,
                "smart_pantry_manager.db",
                null,
                1
        );
    }

    // this method is called the first time when the db is accessed
    // well, it creates tables and seeds data when the app is installed,
    // and since my app is already installed without them, I need to uninstall and reinstall.
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Now, lets create the tables

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Ingredient (" +
                "Ingredient_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Ingredient_Name TEST NOT NULL UNIQUE)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Recipe (" +
                        "Recipe_Id INTEGER PRIMARY KEY, " +
                        "Recipe_Name TEXT NOT NULL, " +
                        "Recipe_Instructions TEXT NOT NULL)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Pantry_Ingredient (" +
                        "PI_Id INTEGER PRIMARY KEY, " +
                        "Ingredient_Id INTEGER REFERENCES Ingredient(Ingredient_Id) NOT NULL, " +
                        "PI_Qty INTEGER NOT NULL, " +
                        "PI_Unit TEXT NOT NULL)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Recipe_Ingredient (" +
                        "RI_Id INTEGER PRIMARY KEY, " +
                        "Recipe_Id INTEGER REFERENCES Recipe(Recipe_Id) NOT NULL, " +
                        "Ingredient_Id INTEGER REFERENCES Ingredient(Ingredient_Id) NOT NULL, " +
                        "RI_Required_Qty INTEGER NOT NULL, " +
                        "RI_Unit TEXT NOT NULL)"
        );


        // here we seed the tables with data
        // Pantry_Ingredient is not seeded since the user will add items to their own pantry via the app UI

        // the ingredients:
        db.execSQL(
                "INSERT INTO Ingredient(Ingredient_Name) VALUES " +
                        "('black pepper')," +
                        "('egg')," +
                        "('bread')," +
                        "('garlic')," +
                        "('rice')," +
                        "('salt')," +
                        "('sugar')," +
                        "('honey')," +
                        "('milk')," +
                        "('blueberry')," +
                        "('strawberry')," +
                        "('flour')," +
                        "('chocolate')," +
                        "('cheddar cheese')," +
                        "('mozzarella cheese')," +
                        "('bell pepper')," +
                        "('carrot')," +
                        "('tomato')," +
                        "('potato')," +
                        "('mince')," +
                        "('macaroni')," +
                        "('spaghetti')," +
                        "('cream cheese')," +
                        "('baking soda')," +
                        "('cooking oil')," +
                        "('butter')," +
                        "('spinach')," +
                        "('noodles')," +
                        "('soy sauce')," +
                        "('chicken')"
        );

        // the recipes:
        db.execSQL(
                "INSERT INTO Recipe(Recipe_Name, Recipe_Instructions) VALUES " +
                        "('Omelette', 'You need to whisk 2 eggs, season it with a pinch of salt and pepper, then cook in butter over a medium-heated pan or pot until set')," +
                        "('Strawberry Pancakes', 'Mix flour, milk, and egg into a batter, folding in the strawberries. Multiple can be cooked in a buttered pan until golden brown')," +
                        "('Blueberry Pancakes', 'Mix flour, milk, and egg into a batter, folding in the blueberries. Multiple can be cooked in a buttered pan until golden brown')," +
                        "('Chocolate Chip', 'Mix flour, sugar, butter, and chocolate chips into a dough, scoop the dough onto a tray, then bake until its edges are golden')," +
                        "('Ramen', 'Boil the ramen noodles in water with soy sauce until it becomes soft, then add a soft-boiled egg on top of it')," +
                        "('Lasagna', 'Boiled the spaghetti sheets in salted water, then layer them along with mince, cheddar cheese, and tomato, then bake until cheese is melted and bubbly')," +
                        "('Vegetable Stir Fry', 'Stir fry the bell pepper, carrot, and spinach in oil with soy sauce until its tender.')," +
                        "('Chicken Stir Fry', 'Stir fry the chicken with bell pepper and carrot in oil and soy sauce until the chicken is cooked through')," +
                        "('Buttered Toast', 'Toast the bread, then butter on it while its still warm')," +
                        "('Egg Toast', 'Mix an egg, some salt and black pepper, dip the bread in and fry it in a buttered pan')," +
                        "('Cake', 'Mix flour, sugar, butter, and eggs into a batter, pour into a pan, bake until a toothpick comes out clean')," +
                        "('Pizza', 'Mix flour, water, salt and baking soda, flatten it using a rolling pin. Then top the base with tomato, mozzarella cheese, and bell pepper, bake until the cheese is melted.')," +
                        "('Spaghetti Bolognese', 'Cook mince with tomato and garlic into a sauce, serve over boiled spaghetti')," +
                        "('Grilled Cheese', 'Toast bread with butter, melt cheddar and mozzarella cheese between the slices in a pan')," +
                        "('Honey Tea', 'Leave a tea bag in hot water for a bit, then add honey and stir')," +
                        "('Macaroni & Cheese', 'Boil macaroni, mix it with melted cheddar cheese and a splash of milk until its creamy')"
        );

        // finally, the recipe ingredients:
        // the standard metric units that were used: g, kg, l, ml, piece, tsp, tbsp
        db.execSQL(
                "INSERT INTO Recipe_Ingredient(Recipe_Id, Ingredient_Id, RI_Required_Qty, RI_Unit) VALUES " +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Omelette'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'egg'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Omelette'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 2, 'tsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Omelette'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'salt'), 1, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Omelette'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'black pepper'), 1, 'g')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Strawberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'flour'), 150, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Strawberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'milk'), 250, 'ml')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Strawberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'egg'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Strawberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'strawberry'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Strawberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Blueberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'flour'), 150, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Blueberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'milk'), 250, 'ml')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Blueberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'egg'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Blueberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'blueberry'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Blueberry Pancakes'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chocolate Chip'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'flour'), 200, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chocolate Chip'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'sugar'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chocolate Chip'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chocolate Chip'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'chocolate'), 100, 'g')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Ramen'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'noodles'), 200, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Ramen'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'soy sauce'), 2, 'tbsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Ramen'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'egg'), 1, 'piece')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Lasagna'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'spaghetti'), 250, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Lasagna'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'mince'), 300, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Lasagna'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'cheddar cheese'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Lasagna'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'tomato'), 2, 'piece')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Vegetable Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'bell pepper'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Vegetable Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'carrot'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Vegetable Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'spinach'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Vegetable Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'cooking oil'), 1, 'tbsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Vegetable Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'soy sauce'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chicken Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'chicken'), 250, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chicken Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'bell pepper'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chicken Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'carrot'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chicken Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'cooking oil'), 1, 'tbsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Chicken Stir Fry'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'soy sauce'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Buttered Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'bread'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Buttered Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Egg Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'egg'), 1, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Egg Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'bread'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Egg Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'salt'), 1, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Egg Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'black pepper'), 1, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Egg Toast'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Cake'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'flour'), 250, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Cake'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'sugar'), 150, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Cake'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 125, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Cake'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'egg'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Cake'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'baking soda'), 1, 'tsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Pizza'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'flour'), 250, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Pizza'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'salt'), 1, 'tsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Pizza'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'baking soda'), 1, 'tsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Pizza'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'tomato'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Pizza'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'mozzarella cheese'), 150, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Pizza'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'bell pepper'), 1, 'piece')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Spaghetti Bolognese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'mince'), 250, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Spaghetti Bolognese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'tomato'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Spaghetti Bolognese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'garlic'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Spaghetti Bolognese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'spaghetti'), 250, 'g')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Grilled Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'bread'), 2, 'piece')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Grilled Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'butter'), 1, 'tbsp')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Grilled Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'cheddar cheese'), 50, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Grilled Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'mozzarella cheese'), 50, 'g')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Honey Tea'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'honey'), 1, 'tbsp')," +

                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Macaroni & Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'macaroni'), 250, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Macaroni & Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'cheddar cheese'), 100, 'g')," +
                        "((SELECT Recipe_Id FROM Recipe WHERE Recipe_Name = 'Macaroni & Cheese'), (SELECT Ingredient_Id FROM Ingredient WHERE Ingredient_Name = 'milk'), 150, 'ml')"
        );
    }

    // while this one is called in case the db version number is changed,
    // preventing previous versions of the app from breaking when the db changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
