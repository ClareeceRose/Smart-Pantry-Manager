-- This file is used to create and seed tables for the Smart Pantry Manager mobile app
-- It includes 4 tables: Ingredient, Pantry_Ingredient, Recipe_Ingredient, Recipe

CREATE TABLE IF NOT EXISTS Ingredient (
    Ingredient_Id INTEGER PRIMARY KEY,
    Ingredient_name TEXT NOT NULL UNIQUE); -- unique because if no ingredient name match is found, the sys adds a new record

CREATE TABLE IF NOT EXISTS Recipe(
    Recipe_Id INTEGER PRIMARY KEY,
    Recipe_Name TEXT NOT NULL,
    Recipe_Instructions TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS Pantry_Ingredient (
    PI_Id INTEGER PRIMARY KEY,
    Ingredient_Id INTEGER REFERENCES Ingredient(Ingredient_Id) NOT NULL,
    PI_Qty INTEGER NOT NULL,
    PI_Unit TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS Recipe_Ingredient(
    RI_Id INTEGER PRIMARY KEY,
    Ingredient_Id INTEGER REFERENCES Ingredient(Ingredient_Id) NOT NULL,
    Recipe_Id INTEGER REFERENCES Recipe(Recipe_Id) NOT NULL,
    RI_Required_Qty INTEGER NOT NULL,
    RI_Unit TEXT NOT NULL);