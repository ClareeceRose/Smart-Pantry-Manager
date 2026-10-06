# Smart Pantry Manager

**Author:** Clareece Rose Hoosen

## Introduction:

The Smart Pantry Manager is an Android mobile application used to suggest recipes 
to users based strictly on leftover ingredients to cut food waste.

How it would work is, users can add ingredients to their pantry and record their quantities. 
The application then compares pantry contents with ingredients and quantities 
required by stored recipes.

A recipe is only suggested when the user has all required ingredients and sufficient quantities. 
It's a strict matching rule that prevents recipes that need the user to go shopping, from being 
suggested.

The app also allows users to add, edit and delete the pantry ingredients, view suggested recipes, 
their details, and set a preferred measurement system.

## Setup And Running The Application

### Requirements:
Before you run the Smart Pantry Manager, please ensure that the following are installed:

- Android Studio
- Android SDK
- A compatible Java/JDK version
- An Android device or Android emulator

### Setup:
1) You first need to clone or download the Smart Pantry Manager repository from GitHub.
2) Open the project in Android Studio.
3) Then allow Android Studio to sync the Gradle files and download any required dependencies.
4) Connect an android device with USB debugging enabled, or start an Android emulator.
5) Build the project in Android Studio to ensure that the app compiles successfully.
6) Now run the application on the connected device or emulator.

### Running The Application:
After you launch the application, you'll be presented with the Pantry screen. From there, you can:
1. Add new pantry ingredients and their quantities.
2. Edit or delete existing pantry ingredients.
3. Navigate to Suggested Recipes to view recipes that can be prepared using the available pantry ingredients.
4. Select a recipe to view its required ingredients and preparation instructions.
5. Open Settings to change the preferred measurement system between Metric and Imperial.

## The Plural Ingredient Business Logic:
The ingredient matching accounts for both singular and plural forms.
It works like this: The entered pantry ingredient given by the user via the 
form (Add Ingredient screen) is trimmed, converted to lowercase, and has it's name compared 
with the names of the stored ingredients in the Ingredient table. Every entered ingredient 
is given an initial comparison before being normalized and compared again.

If a match is found, then a new pantry ingredient instance is created with a given 
Ingredient_Id. The pantry ingredients are stored in the SQLite database for persistence and loaded 
into an ArrayList when they are required for display and recipe matching.

If no match is found, then that ingredient name is normalized with the use of substrings and 
compared with the stored ingredient after each normalization process to find a match. For
example: 

    "tomatoes" -> "tomato" ("es" removal and comparison)
    "berries" -> "berry" ("ies" replaced with "y")
    "chocolates" -> "chocolate" ("s" removal)

After checking if the entered ingredient ends with either "ies", "es", or "s", 
the names are manipulated and compared with the ingredient name, and if no record exists, then a 
new instance is created, being stored as its existing form, and then added to the Pantry_Ingredient 
table and added to the ArrayList of pantry ingredients.

## The Quantity Comparison Business Logic:
Once the pantry ingredients exist, they are then compared with recipe ingredients. The process is not as 
simple. Each recipe has recipe ingredients that needs to be compared against the user's pantry, 
checking if they have EVERY ingredient needed for the recipe, AND if they have greater than or an 
equal amount of ingredient quantity. And, of course, if they quantities need to be compared, then 
they must be converted to a common base form first.

For example, if we simply did:
    
    Recipe Requires: flour - 1 kg
    but 
    Pantry contains: flour - 500 g

The recipe is not suggested.

This is when compatible units need to be converted to a common base 
before comparing them.

This application uses:
- grams as a base unit for mass
- ml as a base unit for volume
- and teaspoons as a base unit for tsp/tbsp measurements

Incompatible measurement types are not treated as equivalent.

So, so summarize:
The first comparison is Pantry ingredients entered are compared with Recipe ingredients for each recipe, using 
their Ingredient_Id attributes. The second comparison is having quantities compared, those that are of different 
units will be converted to a common base form and compared. If every recipe ingredient is in the user's pantry with 
sufficient amounts, then that recipe is added to the Suggested Recipe screen, stored in an arraylist 
temporarily. The user can then click the arrow on that recipe card and view more details, such as the
recipe ingredients needed and instructions to make it.

## Main Features:
- Add pantry ingredients with quantities and measurement units
- View all stored pantry ingredients
- Edit pantry ingredient quantities and units
- Delete pantry ingredients with confirmation
- Store pantry data persistently using SQLite
- Suggest recipes using strict ingredient and quantity matching
- Handle compatible measurement-unit conversions
- Account for common singular plural ingredient forms
- View recipe ingredients and preparation instructions
- Navigate between the Pantry, Suggested Recipes, and Settings screens
- Select between metric and imperial measurement options

## The Database Choice:
I had chosen SQLite as the database for the Smart Pantry Manager because:
1) The application does not require an internet connection or a remote server to perform any of the 
key functions.
2) Android Studio supports SQLite with the use of SQLiteOpenHelper (which is DBHelper in my project).
3) It provides data persistence, where SQLite stores the tables of data permanently on the device even 
an Activity closes.
4) Lastly, it's really lightweight, making it the ideal choice for the application considering the amount 
of data we need to store. Maybe in the future I'd swap with another database if the app needed to 
store more recipes and ingredients.

## The Database Structure:
This application uses SQLite as its local database. 
It consists of four main tables.

**Ingredient Table:**
This table stores the unique ingredients used by the application.

Ingredient_Id, 
Ingredient_Name

**Pantry Ingredient Table:**
Stores the ingredients currently available in the user's pantry.

PI_Id, 
Ingredient_Id, 
PI_Qty, 
PI_Unit

**Recipe Table:**
This stores the recipes available in the app.

Recipe_Id, 
Recipe_Name, 
Recipe_Instructions

**Recipe Ingredient Table:**
Stores the ingredients and quantities required for each recipe.

RI_Id, 
Recipe_Id, 
Ingredient_Id, 
RI_Required_Qty, 
RI_Unit

These relations allow recipe and pantry ingredients to refer to the same 
ingredient records through Ingredient_Id.

The db acts as the application's permanent source of data, while Java objects 
and ArrayLists are used as temporary representations of that data while the application
is running.

## Application Architecture:
The Smart Pantry Manager was designed to separate responsibilities between different layers:
**Activities -> Services -> Repositories -> SQLite DB**

Where activities are responsible for interacting with the UI, utilizing necessary 
services. The service layer contains application-level operations and business logic.
Repositories are responsible for communciating with the SQLite DB and performing various
CRUD operations.

The data retrived from SQLite is converted into Java model objects and stored in ArrayLists,
for when it needs to be processed or displayed.

The recipe-matching process used these Java objects to determine which recipes qualify to be 
suggested to the user.

## Screens:

**1) Pantry Screen**
This screen as MainActivity, is used to display ingredients that are currently
in the user's pantry. Users are able to add new ingredients, edit existing quantities
and units, or even delete specific ingredients.

**2) Suggested Recipe Screen**
This screen displays recipes that can be prepared using the current contents of the user's 
pantry. Recipes are only suggested if every recipe ingredient strictly matches pantry 
ingredients in terms of name and quantity.

**3) Settings Screen**
The settings screen allows users to configure the application's measurement preferences, which 
can be in either Imperial or Metric.

**4) Add/Edit Ingredient Screens**
These are 2 separate screens, given similar styles and rules. Users can use these screens to add 
new pantry ingredients or edit existing ones by quantity and unit type.

**5) Recipe Detail Screen**
This screen displays the selected recipe's required ingredients, quantities, measurement units, 
and their preparation instructions.

## Technologies Used:
- Java
- Android Studio
- Android SDK
- SQLite
- XML
- RecyclerView
- Git/GitHub

## Purpose:
The main purpose of Smart Pantry Manager is to provide a practical way for users to 
make better use of ingredients they already have available in their pantry. It reduces unnecessary 
food wastage and helps them avoid recipes that require additional shopping trips.