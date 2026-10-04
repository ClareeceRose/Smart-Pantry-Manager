package com.example.smartpantrymanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.activities.RecipeDetailActivity;
import com.example.smartpantrymanager.domain.models.Recipe;
import com.example.smartpantrymanager.domain.models.RecipeIngredient;

import java.util.ArrayList;

// now we can just follow the same steps from when the PantryIngredient_RecyclerViewAdapter was written.

// the logic here is, for every recipe object suggested, that is how many rows will display.
// each row will display the recipe name, the number of recipe ingredients and a button to view recipe details when
// clicked.
// the RecipeIngredient objects are filtered by matching Recipe_Id.
// and the ingredient count is incremented for every recipe ingredient.
public class SuggestedRecipe_RecyclerViewAdapter
        extends RecyclerView.Adapter<SuggestedRecipe_RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<RecipeIngredient> recipeIngredients;
    private ArrayList<Recipe> recipes;

    public SuggestedRecipe_RecyclerViewAdapter(
            Context context,
            ArrayList<RecipeIngredient> recipeIngredients,
            ArrayList<Recipe> recipes
    ){
        this.context = context;
        this.recipeIngredients = recipeIngredients;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public SuggestedRecipe_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.recipe_recycler_view_row,
                parent,
                false
        );

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull SuggestedRecipe_RecyclerViewAdapter.MyViewHolder holder,
            int position) {

        // this gets the recipe represented by this RecyclerView row.
        Recipe recipe = recipes.get(position);

        // then the recipe name is displayed.
        holder.recipeName.setText(recipe.getRecipeName());

        // holds the count for how many ingredients belong to this recipe.
        int ingredientCount = 0;

        // and we're using a for-each loop here to increment for each recipe ingredient
        for (RecipeIngredient recipeIngredient : recipeIngredients) {

            // only if the recipeIds match (Recipe <---> Recipe_Ingredient)
            if (recipeIngredient.getRecipeId() == recipe.getRecipeId()) {

                ingredientCount++;

            }
        }

        // this displays the number of required ingredients.
        holder.ingredientCount.setText(ingredientCount + " ingredients");

        // while this opens the recipe detail screen when the arrow is clicked.
        holder.recipeArrow.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    RecipeDetailActivity.class
            );

            // here, the ID of the selected recipe is sent to the detail screen.
            intent.putExtra(
                    "recipe_id",
                    recipe.getRecipeId()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        TextView ingredientCount;
        ImageView recipeArrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            ingredientCount = itemView.findViewById(R.id.ingredient_count);
            recipeArrow = itemView.findViewById(R.id.arrow_to_recipe_detail_screen);
        }
    }
}