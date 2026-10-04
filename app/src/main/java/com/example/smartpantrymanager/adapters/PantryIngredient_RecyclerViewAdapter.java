package com.example.smartpantrymanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;
import com.example.smartpantrymanager.domain.services.PantryIngredientService;

import java.util.ArrayList;

public class PantryIngredient_RecyclerViewAdapter extends RecyclerView.Adapter<PantryIngredient_RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<PantryIngredient> pantryIngredients;
    private IngredientService ingredientService;
    private PantryIngredientService pantryIngredientService;
    public PantryIngredient_RecyclerViewAdapter(
            Context context,
            ArrayList<PantryIngredient> pantryIngredients,
            IngredientService ingredientService,
            PantryIngredientService pantryIngredientService
    ){
        this.context = context;
        this.pantryIngredients = pantryIngredients;
        this.ingredientService = ingredientService;
        this.pantryIngredientService = pantryIngredientService;
    }

    @NonNull
    @Override
    public PantryIngredient_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pantry_ingredient_recycler_view_row,
                parent,
                false
        );

        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PantryIngredient_RecyclerViewAdapter.MyViewHolder holder, int position) {

        PantryIngredient pantryIngredient = pantryIngredients.get(position);

        // gets the ingredient name
        String ingredientName = ingredientService.findIngredientById(pantryIngredient.getIngredientId())
                .getIngredientName().toLowerCase().trim();

        // sets the pantry ingredient name
        holder.ingredient.setText(ingredientName);

        // sets the qty and unit
        holder.ingredientQty.setText(pantryIngredient.getPantryIngredientQty() +
                " " +
                pantryIngredient.getPantryIngredientUnit());

        // the edit button // coming soon... with next commit
        holder.editIngredientButton.setOnClickListener(v -> {});

        // the delete button
        holder.deleteIngredientButton.setOnClickListener(v -> {

            // this will show a confirmation dialog before deleting.
            new AlertDialog.Builder(context).setTitle("Delete Ingredient")
                    .setMessage(
                            "Are You Sure You Want To Delete " +
                            ingredientName +
                            "?")
                    .setPositiveButton(
                            "Yes",
                            (dialog, which) -> {

                                // this will delete the ingredient from the database.
                                pantryIngredientService.deletePantryIngredient(pantryIngredient);

                                // while THIS gets the current position of the item.
                                int currentPosition = holder.getBindingAdapterPosition();

                                // now we remove it from the RecyclerView list.
                                if (currentPosition != RecyclerView.NO_POSITION){

                                    // this deletes the pantry ingredient from a specific index
                                    pantryIngredients.remove(currentPosition);

                                    // while this tells the recyclerview that an item has been deleted in
                                    // this pos
                                    // basically updates the screen without needing to reload the whole list
                                    notifyItemRemoved(currentPosition);

                                }
                            })
                    // this is if the user selects no
                    .setNegativeButton("No", null).show();

        });
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
