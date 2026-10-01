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
import com.example.smartpantrymanager.adapters.PantryIngredient_RecyclerViewAdapter;
import com.example.smartpantrymanager.core.enums.UnitType;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.data.repositories.IngredientRepository;
import com.example.smartpantrymanager.data.repositories.PantryIngredientRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;
import com.example.smartpantrymanager.domain.services.PantryIngredientService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<PantryIngredient> pantryIngredients;
    private RecyclerView pantryRecyclerView;
    private PantryIngredientService pantryIngredientService;
    private IngredientRepository ingredientRepo;
    private IngredientService ingredientService;
    private PantryIngredient_RecyclerViewAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });

        // let's initialize the db helper
        DBHelper dbHelper = new DBHelper(this);
        PantryIngredientRepository pantryIngredientRepo = new PantryIngredientRepository(dbHelper);
        pantryIngredientService = new PantryIngredientService(pantryIngredientRepo);

        ingredientRepo = new IngredientRepository(dbHelper);
        ingredientService = new IngredientService(ingredientRepo);

        // this gets the header title and changes its value to "My Pantry"
        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.my_pantry);

        // now the recycler view
        pantryRecyclerView = findViewById(R.id.pantry_recycler_view);
        pantryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // and load all pantry ingredients
        loadAllPantryIngredients();

        // allows the user to move to the suggested recipes screen using the tab
        LinearLayout recipesTab = (LinearLayout) findViewById(R.id.recipe_tab);
        recipesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });

        // and this one allows users to move to the settings screen using the tab
        LinearLayout settingsTab = (LinearLayout) findViewById(R.id.settings_tab);
        settingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // gets the floating action button that adds an ingredient
        // then it redirects the user to an add ingredient form
        FloatingActionButton addIngredientButton = (FloatingActionButton) findViewById(R.id.add_ingredient_button);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddIngredientActivity.class);
                startActivity(intent);
            }
        });
    }

    // this needs to be loaded as soon as the user enters app or clicks to view pantry.
    // it must also be refreshed when a new ingredient is added to the pantry
    public void loadAllPantryIngredients(){
        TextView emptyIngredientMsg = findViewById(R.id.edit_ingredient_error_msg);
        pantryIngredients = pantryIngredientService.getAllPantryIngredients();

        if (pantryIngredients.isEmpty()){
            emptyIngredientMsg.setVisibility(View.VISIBLE);
            pantryRecyclerView.setVisibility(View.GONE);
            return;
        }


        // now, if the pantry DOES have ingredients
        emptyIngredientMsg.setVisibility(View.GONE);
        pantryRecyclerView.setVisibility(View.VISIBLE);

        pantryAdapter = new PantryIngredient_RecyclerViewAdapter(this, pantryIngredients, ingredientService);

        pantryRecyclerView.setAdapter(pantryAdapter);

    }
}