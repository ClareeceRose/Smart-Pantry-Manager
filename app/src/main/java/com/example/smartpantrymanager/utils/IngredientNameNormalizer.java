package com.example.smartpantrymanager.utils;

public class IngredientNameNormalizer {

    private String unnormalizedPantryIngredientName;
    public IngredientNameNormalizer(String unnormalizedPantryIngredientName) {
        this.unnormalizedPantryIngredientName = unnormalizedPantryIngredientName;
    }

    // every normalizer method makes use of substrings to manipulate the given ingredient name

    // for converting 'ies' to 'y'
    // for berries -> berry
    public String convertIesToY(){
        String name = unnormalizedPantryIngredientName;

        if (name.endsWith("ies")) {
            return name.substring(0, name.length() - 3) + "y";
        }

        return name;
    }

    // for removing 'es' from the end of a pantry ingredient
    // tomatoes -> tomato
    public String removeEs(){
        String name = unnormalizedPantryIngredientName;

        if (name.endsWith("es")) {
            return name.substring(0, name.length() - 2);
        }

        return name;
    }

    // for removing 's' from the end of a pantry ingredient
    // chocolates -> chocolate
    public String removeS(){
        String name = unnormalizedPantryIngredientName;

        if (name.endsWith("s")) {
            return name.substring(0, name.length() - 1);
        }

        return name;
    }

}
