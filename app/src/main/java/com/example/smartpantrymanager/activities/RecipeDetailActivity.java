package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.core.enums.UnitType;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.data.repositories.IngredientRepository;
import com.example.smartpantrymanager.data.repositories.RecipeIngredientRepository;
import com.example.smartpantrymanager.data.repositories.RecipeRepository;
import com.example.smartpantrymanager.domain.models.Ingredient;
import com.example.smartpantrymanager.domain.models.Recipe;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;
import com.example.smartpantrymanager.domain.services.RecipeIngredientService;
import com.example.smartpantrymanager.domain.services.RecipeService;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;
    private IngredientService ingredientService;
    private Recipe recipe;
    private ArrayList<RecipeIngredient> recipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recipe_detail_screen), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        DBHelper dbHelper = new DBHelper(this);

        RecipeRepository recipeRepo = new RecipeRepository(dbHelper);
        RecipeIngredientRepository recipeIngredientRepo =
                new RecipeIngredientRepository(dbHelper);
        recipeService = new RecipeService(recipeRepo);
        recipeIngredientService = new RecipeIngredientService(recipeIngredientRepo);

        IngredientRepository ingredientRepo = new IngredientRepository(dbHelper);
        ingredientService = new IngredientService(ingredientRepo);

        // this gets the recipe id sent from the suggested recipe adapter
        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        // now we find the selected recipe.
        recipe = findRecipeById(recipeId);

        // if the recipe couldn't be found, then the user is returned to the suggested recipes screen.
        if (recipe == null){
            goToSuggestedRecipes();
            return;
        }

        // here we get all the recipe ingredients from the db
        recipeIngredients = recipeIngredientService.getAllRecipeIngredients();

        // this displays the recipe info.
        displayRecipeDetails();

        // now we set up the back button to leave the recipe detail screen
        ImageView backButton = findViewById(R.id.recipe_back_btn);

        backButton.setOnClickListener(v -> goToSuggestedRecipes());
    }

    // alright, so this method is used to find the recipe using the recipe id
    private Recipe findRecipeById(int recipeId){
        ArrayList<Recipe> recipes = recipeService.getAllRecipes();

        for (Recipe recipe : recipes) {
            if (recipe.getRecipeId() == recipeId){
                return recipe;
            }
        }
        return null;
    }

    // this method's for displaying the selected recipe's details.
    private void displayRecipeDetails(){

        TextView recipeHeading = findViewById(R.id.recipe_heading);
        TextView ingredientsText = findViewById(R.id.recipe_ingredients);
        TextView instructionsText = findViewById(R.id.recipe_instructions);

        // this displays the recipe name.
        recipeHeading.setText(recipe.getRecipeName());

        // while this builds the ingredient list.
        StringBuilder ingredientList = new StringBuilder();

        for (RecipeIngredient recipeIngredient : recipeIngredients) {

            // displays ingredients that belong to this speficic recipe
            if (recipeIngredient.getRecipeId() == recipe.getRecipeId()){
                Ingredient ingredient = ingredientService.findIngredientById(recipeIngredient.getIngredientId());

                if (ingredient != null){
                    ingredientList.append(recipeIngredient.getRecipeIngredientRequiredQty())
                            .append(" ").append(getUnitDisplayName(recipeIngredient.getRecipeIngredientUnit()))
                            .append(" ").append(ingredient.getIngredientName()).append("\n");
                }
            }
        }

        // this displays the ingredient list.
        ingredientsText.setText(ingredientList.toString());

        // and this displays the recipe instructions.
        instructionsText.setText(recipe.getRecipeInstructions());
    }

    // this method is to convert the UnitType enum to something displayable
    private  String getUnitDisplayName(UnitType unit) {
        switch(unit){
            case GRAM:
                return "g";

            case KILOGRAM:
                return "kg";

            case MILLILITER:
                return "ml";

            case LITER:
                return "l";

            case PIECE:
                return "piece";

            case TEASPOON:
                return "tsp";

            case TABLESPOON:
                return "tbsp";

            case OUNCE:
                return "oz";

            case POUND:
                return "pound";

            case FLUID_OUNCE:
                return "fluid oz";

            default:
                return "";
        }
    }

    // and this method is soley for returning the user toi the suggested recipes screen
    private void goToSuggestedRecipes(){
        Intent intent = new Intent(RecipeDetailActivity.this, SuggestedRecipesActivity.class);

        startActivity(intent);
        finish();
    }
}