package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapters.SuggestedRecipe_RecyclerViewAdapter;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.data.repositories.PantryIngredientRepository;
import com.example.smartpantrymanager.data.repositories.RecipeIngredientRepository;
import com.example.smartpantrymanager.data.repositories.RecipeRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.models.Recipe;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;
import com.example.smartpantrymanager.domain.services.PantryIngredientService;
import com.example.smartpantrymanager.domain.services.RecipeIngredientService;
import com.example.smartpantrymanager.domain.services.RecipeMatchingService;
import com.example.smartpantrymanager.domain.services.RecipeService;

import java.util.ArrayList;

/*

    Now we're onto the other major activity of the program.
    The SuggestedRecipesActivity file needs to get and store Recipes and their Recipe Ingredients,
    which will then have their Ingredient_Ids compared with that of those in the pantry.

    A separate file will perform the actual matching logic: RecipeMatchingService.java

*/

public class SuggestedRecipesActivity extends AppCompatActivity {

    // db helper
    private DBHelper dbHelper;

    // pantry ingredients
    private PantryIngredientRepository pantryIngredientRepo;
    private PantryIngredientService pantryIngredientService;
    private ArrayList<PantryIngredient> pantryIngredients;

    // recipes
    private RecipeRepository recipeRepo;
    private RecipeService recipeService;
    private ArrayList<Recipe> recipes;

    private RecipeMatchingService recipeMatchingService;

    // recipe ingredients
    private RecipeIngredientRepository recipeIngredientRepo;
    private RecipeIngredientService recipeIngredientService;
    private ArrayList<RecipeIngredient> allRecipeIngredients;

    private RecyclerView suggestedRecipeRecyclerView;

    private SuggestedRecipe_RecyclerViewAdapter suggestedRecipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.suggested_recipes_screen), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });

        // db helper
        dbHelper = new DBHelper(this);

        // pantry ingredients
        pantryIngredientRepo = new PantryIngredientRepository(dbHelper);
        pantryIngredientService = new PantryIngredientService(pantryIngredientRepo);
        pantryIngredients = pantryIngredientService.getAllPantryIngredients();

        // recipes
        recipeRepo = new RecipeRepository(dbHelper);
        recipeService = new RecipeService(recipeRepo);
        recipes = recipeService.getAllRecipes();

        // recipe ingredients
        recipeIngredientRepo = new RecipeIngredientRepository(dbHelper);
        recipeIngredientService = new RecipeIngredientService(recipeIngredientRepo);
        allRecipeIngredients = recipeIngredientService.getAllRecipeIngredients();

        suggestedRecipeRecyclerView = findViewById(R.id.recipes_recycler_view);
        suggestedRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // finds recipes that can be made using the ingredients in the pantry.
        ArrayList<Recipe> suggestedRecipes = suggestRecipe();

        // and this displays the matching recipes.
        loadAllSuggestedRecipes(suggestedRecipes);

        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.recipes);

        // changes color of recipes tab when on this activity
        LinearLayout recipeTab = findViewById(R.id.recipe_tab);
        recipeTab.setBackgroundResource(R.color.selected_tab_background);

        LinearLayout pantryTab = (LinearLayout) findViewById(R.id.pantry_tab);
        pantryTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuggestedRecipesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout settingsTab = (LinearLayout) findViewById(R.id.settings_tab);
        settingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuggestedRecipesActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Recipe> suggestRecipe(){
        ArrayList<Recipe> suggestedRecipes = new ArrayList<>();
        boolean isMatch;
        int recipeId;

        for (Recipe recipe : recipes) {

            // This list will only contain the RecipeIngredients that belong to the current recipe.
            ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();

            recipeId = recipe.getRecipeId();

            // I'll ensure filtering by recipe id is done before match attempts
            for (RecipeIngredient recipeIngredient : allRecipeIngredients){

                if (recipeIngredient.getRecipeId() == recipeId){
                    recipeIngredients.add(recipeIngredient);
                }

            }

            recipeMatchingService = new RecipeMatchingService(recipeIngredients, pantryIngredients);

            // returns and stores whether ingr and their quantities match
            isMatch = recipeMatchingService.doIngredientsMatch();

            // only if they're matched are they added to the suggested recipe array list
            if (isMatch){

                suggestedRecipes.add(recipe);

            }

        }

        // returns the suggested recipe arraylist
        return suggestedRecipes;
    }

    // method used to load all suggested recipes to the screen
    public void loadAllSuggestedRecipes(ArrayList<Recipe> suggestedRecipes){
        TextView emptySuggestedRecipeMsg = findViewById(R.id.empty_recipe_screen_msg);

        // but if the array list of suggested recipes are empty,
        // a message indicating its emptiness will display
        // and the recyclerview will be gone
        if (suggestedRecipes.isEmpty()){
            emptySuggestedRecipeMsg.setVisibility(View.VISIBLE);
            suggestedRecipeRecyclerView.setVisibility(View.GONE);
            return;
        }

        // and if there are suggested recipes
        // the opposite happens
        // the msg is gone, and the rcycler view is visible
        emptySuggestedRecipeMsg.setVisibility(View.GONE);
        suggestedRecipeRecyclerView.setVisibility(View.VISIBLE);

        suggestedRecipeAdapter = new SuggestedRecipe_RecyclerViewAdapter(
                this,
                allRecipeIngredients,
                suggestedRecipes
        );

        // sets the adapter
        suggestedRecipeRecyclerView.setAdapter(suggestedRecipeAdapter);

    }
}