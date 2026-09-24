Title: Smart Pantry Manager
Author: Clareece Rose Hoosen


Introduction:
The Smart Pantry Manager is a mobile application used to suggest recipes 
to users based strictly on leftover ingredients to cut food waste.

How it would work is by having the user add whatever ingredients is currently
in their pantry, and from there they can search for recipes based on what 
they have available. Ingredients will be tracked and have their quantities 
adjusted once they've already been used in a recipe.

The user will not be required to go on a shopping trip, nor will a 
recipe be suggested unless the user actually has everything they need for 
a particular recipe.

The Smart Pantry Manager enforces a strict-matching rule, which is the most 
important piece of business logic of the app.


The Plural Ingredient Business Logic:
There are many issues that could come about when deciding the approach to ensure that 
the system takes into account the real-world messiness of ingredient matching.

1) At first, I wanted to use a basic substring methodology where the system would 
compare strings after they have been normalized to a certain degree. For example:
if a user typed in "tomatoes" then the system could be designed to remove the
"es" from it to form a singular word "tomato", making it much easier to track 
and compare it with the available ingredients for each recipe. But, what if the user 
were to type "cheeses" which would then be normalized to "chees"? Such a simple approach 
could lead to frustrated users.

2) So, why not match this badly normalized word to the recipe ingredients stored in the 
database then have them stored and displayed for the user as such? Because, imagine this 
"chees" is compared with "cheeseburger" using a .contains() method, and is stored as such, 
making the user get recipes such as "cheeseburger casserole". Not convinced? What of 
"pepper" being stored and match with recipes that require "peppermint"?

3) Okay, so since this requires stricter logic, the system could extract the string name 
of the ingredient the user enters, trim it, convert it to lowercase, then immediately compare it 
to the stored recipe ingredients in the db. Instead of using .contains() we could use .equals() 
which is much stricter in terms of matching (avoiding the "pepper" and "peppermint" problem).
And if no ingredient matches, then we can normalize. But not immediately by removing "es" from 
words like "cheese" or "tomatoes". I believe this should be done iteratively, where we'd remove 
the last letter "s" and then attempt to match it with the database ingredient once more, 
which matches "cheeses" with "cheese" | "chocolates" with "chocolate".
If that doesn't work then it can remove the second last letter as well "es", matching "tomatoes" with 
"tomato". We'd stop it there (unless I find another structure of plural words that needs normalizing), 
but we also need to ensure that plurals like "berries" have "ies" converted to "y".


The Quantity Comparison Business Logic:


Overall Matching Business Logic:


Screens:
1) Pantry Screen

2) Suggested Recipe Screen

3) Settings Screen

4) Adding + Editing Ingredient Screen

5) Recipe Detail Screen