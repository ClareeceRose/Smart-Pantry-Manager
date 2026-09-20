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


The Business Logic:
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
3) We must become stricter.