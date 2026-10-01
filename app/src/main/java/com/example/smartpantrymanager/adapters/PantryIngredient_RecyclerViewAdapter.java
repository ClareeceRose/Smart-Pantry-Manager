package com.example.smartpantrymanager.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;

import java.util.ArrayList;

public class PantryIngredient_RecyclerViewAdapter extends RecyclerView.Adapter<PantryIngredient_RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<PantryIngredient> pantryIngredients;

    private IngredientService ingredientService;

    public PantryIngredient_RecyclerViewAdapter(
            Context context,
            ArrayList<PantryIngredient> pantryIngredients,
            IngredientService ingredientService
    ){
        this.context = context;
        this.pantryIngredients = pantryIngredients;
        this.ingredientService = ingredientService;
    }

    @NonNull
    @Override
    public PantryIngredient_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PantryIngredient_RecyclerViewAdapter.MyViewHolder holder, int position) {

        PantryIngredient pantryIngredient = pantryIngredients.get(position);

        // let us set the pantry ingredient name
        holder.ingredient.setText(
                ingredientService.findIngredientById(pantryIngredient.getIngredientId())
                        .getIngredientName().toLowerCase().trim());
        holder.ingredientQty.setText(pantryIngredient.getPantryIngredientQty() +
                " " +
                pantryIngredient.getPantryIngredientUnit());

        // the edit button
        holder.editIngredientButton.setOnClickListener(v -> {});

        // the delete button
        holder.deleteIngredientButton.setOnClickListener(v -> {});
    }

    @Override
    public int getItemCount() {
        return pantryIngredients.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // now we get all the elements from the row we want in our recycler
        TextView ingredient, ingredientQty;
        View pantryIngredientDetailDivider;
        ImageButton editIngredientButton, deleteIngredientButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredient = itemView.findViewById(R.id.ingredient);
            ingredientQty = itemView.findViewById(R.id.ingredient_qty);
            pantryIngredientDetailDivider = itemView.findViewById(R.id.pantry_ingredient_detail_divider);
            editIngredientButton = itemView.findViewById(R.id.edit_ingredient_btn);
            deleteIngredientButton = itemView.findViewById(R.id.delete_ingredient_btn);
        }
    }
}
