package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.*;
import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.core.enums.*;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.data.repositories.IngredientRepository;
import com.example.smartpantrymanager.data.repositories.PantryIngredientRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;
import com.example.smartpantrymanager.domain.services.PantryIngredientService;
import com.example.smartpantrymanager.utils.InputValidator;

import java.util.ArrayList;

public class AddIngredientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner unitSpinner;
    private UnitType unitType;
    private ArrayList<PantryIngredient> pantryIngredients;
    private PantryIngredientService pantryIngredientService;
    private PantryIngredientRepository pantryIngredientRepo;
    private IngredientService ingredientService;
    private IngredientRepository ingredientRepo;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_ingredient_form), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            dbHelper = new DBHelper(this);
            pantryIngredientRepo = new PantryIngredientRepository(dbHelper);
            pantryIngredientService = new PantryIngredientService(pantryIngredientRepo);
            ingredientRepo = new IngredientRepository(dbHelper);
            ingredientService = new IngredientService(ingredientRepo);
            pantryIngredients = pantryIngredientService.getAllPantryIngredients();

            return insets;
        });

        String savedMeasurement = getSharedPreferences(
                "MeasurementSystemSettings",
                MODE_PRIVATE
        ).getString("measurement_system", MeasurementSystemType.METRIC.name());

        MeasurementSystemType measurementSystemType = MeasurementSystemType.valueOf(savedMeasurement);

        unitSpinner = (Spinner) findViewById(R.id.add_ingredient_unit_spinner);
        ArrayAdapter<CharSequence> unitAdapter;

        if (measurementSystemType == MeasurementSystemType.METRIC){
            unitAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.metric_unit_options,
                    android.R.layout.simple_spinner_item
            );
        }
        else{
            unitAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.imperial_unit_options,
                    android.R.layout.simple_spinner_item
            );
        }
        // states which layout should be used when the options are listed
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // this will apply the adapter to the spinner
        unitSpinner.setAdapter(unitAdapter);
        // listens for selected items using this activity
        unitSpinner.setOnItemSelectedListener(this);

        // gets and sets title to "Add Ingredient"
        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.add_ingredient_title);


        // allows the user to move to the suggested recipes screen using the tab
        LinearLayout recipesTab = (LinearLayout) findViewById(R.id.recipe_tab);
        recipesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });

        // and this one allows users to move to the settings screen using the tab
        LinearLayout settingsTab = (LinearLayout) findViewById(R.id.settings_tab);
        settingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // so, when the cancel button is clicked, the user will be redirected to the pantry screen
        Button cancelAddIngredientBtn = (Button) findViewById(R.id.cancel_add_ingredient_btn);
        cancelAddIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    // when an item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

        // this is for retrieving the selected item and storing it
        String selectedUnit = parent.getItemAtPosition(pos).toString();

        // this is what actually sets the measurement system used
        if (selectedUnit.equals("g")){
            unitType = UnitType.GRAM;
        }
        else if(selectedUnit.equals("kg")){
            unitType = UnitType.KILOGRAM;
        }
        else if(selectedUnit.equals("ml")){
            unitType = UnitType.MILLILITER;
        }
        else if(selectedUnit.equals("l")){
            unitType = UnitType.LITER;
        }
        else if(selectedUnit.equals("pound")){
            unitType = UnitType.POUND;
        }
        else if(selectedUnit.equals("piece")){
            unitType = UnitType.PIECE;
        }
        else if(selectedUnit.equals("tsp")){
            unitType = UnitType.TEASPOON;
        }
        else if(selectedUnit.equals("tbsp")){
            unitType = UnitType.TABLESPOON;
        }
        else if(selectedUnit.equals("oz")){
            unitType = UnitType.OUNCE;
        }
        else if(selectedUnit.equals("fluid oz")){
            unitType = UnitType.FLUID_OUNCE;
        }
    }

    // and when an item is not selected
    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    // let's perform form validation now

    public void validateAddIngredientForm(){
        // we need to get the data entered.
        EditText addIngredientNameInput = findViewById(R.id.add_ingredient_name);
        EditText addIngredientQtyInput = findViewById(R.id.add_ingredient_qty);
        TextView addIngredientErrorMsg = findViewById(R.id.add_ingredient_error_msg);

        String ingredientName = addIngredientNameInput.getText().toString().trim().toLowerCase();
        String ingredientQty = addIngredientQtyInput.getText().toString().trim();

        if (InputValidator.isNullOrBlank(ingredientName)){
            addIngredientErrorMsg.setText(R.string.empty_ingredient_error_msg);
            addIngredientErrorMsg.setVisibility(View.VISIBLE);
            return;
        }

        if (InputValidator.isNullOrBlank(ingredientQty)){
            addIngredientErrorMsg.setText(R.string.empty_qty_error_msg);
            addIngredientErrorMsg.setVisibility(View.VISIBLE);
            return;
        }

        if (!InputValidator.isPositiveDecimal(ingredientQty)){
            addIngredientErrorMsg.setText(R.string.invalid_qty_error_msg);
            addIngredientErrorMsg.setVisibility(View.VISIBLE);
            return;
        }

    }

    // this will trigger once the user clicks the confirm button to add an ingredient
    public void addIngredient(){
        // the process

        // validate inputs first
        validateAddIngredientForm();

        // now we check if this pantry ingredient already exists to prevent duplicates

    }


}