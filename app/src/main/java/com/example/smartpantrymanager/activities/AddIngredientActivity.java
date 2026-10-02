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
import com.example.smartpantrymanager.domain.models.Ingredient;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;
import com.example.smartpantrymanager.domain.services.PantryIngredientService;
import com.example.smartpantrymanager.utils.InputValidator;
import com.example.smartpantrymanager.utils.IngredientNameNormalizer;

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
    private String pantryIngredientName;
    TextView addIngredientErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredient);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_ingredient_form), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        dbHelper = new DBHelper(this);
        pantryIngredientRepo = new PantryIngredientRepository(dbHelper);
        pantryIngredientService = new PantryIngredientService(pantryIngredientRepo);
        ingredientRepo = new IngredientRepository(dbHelper);
        ingredientService = new IngredientService(ingredientRepo);
        pantryIngredients = pantryIngredientService.getAllPantryIngredients();

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

        // when the user clicks the confirm button the addIngredient() method will trigger
        Button confirmAddIngredientBtn = findViewById(R.id.confirm_add_ingredient_btn);
        confirmAddIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });

    }

    // when an item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

        // this is for retrieving the selected item and storing it
        String selectedUnit = parent.getItemAtPosition(pos).toString();

        // this is what maps the selected unit to its corresponding UnitType
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
    // uses InputValidator
    public boolean validateAddIngredientForm(String ingredientName, String ingredientQty){
        boolean isValid = true;

        // will return false if the ingredient given is blank or null
        if (InputValidator.isNullOrBlank(ingredientName)){
            addIngredientErrorMsg.setText(R.string.empty_ingredient_error_msg);
            addIngredientErrorMsg.setVisibility(View.VISIBLE);
            isValid = false;
            return isValid;
        }

        // returns false if qty is blank or null
        if (InputValidator.isNullOrBlank(ingredientQty)){
            addIngredientErrorMsg.setText(R.string.empty_qty_error_msg);
            addIngredientErrorMsg.setVisibility(View.VISIBLE);
            isValid = false;
            return isValid;
        }

        // false if qty is not a positive decimal
        if (!InputValidator.isPositiveDecimal(ingredientQty)){
            addIngredientErrorMsg.setText(R.string.invalid_qty_error_msg);
            addIngredientErrorMsg.setVisibility(View.VISIBLE);
            isValid = false;
            return isValid;
        }

        return isValid; // returns true if it passes each check

    }

    // this will trigger once the user clicks the confirm button to add an ingredient
    public void addIngredient(){

        // getting the input fields
        EditText addIngredientNameInput = findViewById(R.id.add_ingredient_name);
        EditText addIngredientQtyInput = findViewById(R.id.add_ingredient_qty);

        // the error message - will be reassigned based on the error occurred
        addIngredientErrorMsg = findViewById(R.id.add_ingredient_error_msg);

        // gets the user's input
        // the ingredient name value
        String ingredientName =
                addIngredientNameInput.getText()
                        .toString()
                        .trim()
                        .toLowerCase();
        // the ingr qty
        String ingredientQty =
                addIngredientQtyInput.getText()
                        .toString()
                        .trim();

        // this will validate the form first
        if (!validateAddIngredientForm(ingredientName, ingredientQty)){
            return;
        }

        double newIngredientQty = Double.parseDouble(ingredientQty);

        // now the system needs to try and find the ingredient exactly as the user entered it.
        // just an initial search.
        // for example:
        // "tomato" -> finds "tomato"
        // "potatoes" -> probably does not find anything
        Ingredient ingredient = ingredientService.findIngredientByName(ingredientName);

        // and for our next step,
        // if the exact ingredient was not found, then it tries to find the normalized version of said ingr.
        // here's an example:
        // "potatoes" -> "potato"
        // "berries" -> "berry"
        if (ingredient == null){

            // this will be used to normalize the given ingr name
            IngredientNameNormalizer ingredientNameNormalizer =
                    new IngredientNameNormalizer(ingredientName);

            // then we use this to normalize AND find the normalized ingr
            ingredient =
                    normalizeAndFindIngredient(
                            ingredientNameNormalizer,
                            ingredientName
                    );
        }

        // and if we found an existing ingredient then we use its Ingredient_Id to check if it
        // already exists in the pantry.
        if (ingredient != null){

            if (pantryIngredientExistsByIngredientId(
                    ingredient.getIngredientId()
            )){

                // so, this error message will display if the ingredient already exists in the pantry
                // assigns the text
                addIngredientErrorMsg.setText(
                        R.string.pantry_ingredient_already_exists_error_msg
                );

                // and sets the visibility of the error message
                addIngredientErrorMsg.setVisibility(View.VISIBLE);

                return;
            }

            // if the ingredient already exists in the Ingredient table, but it is NOT in the pantry
            // then, we only need to create the PantryIngredient instance.
            PantryIngredient newPantryIngredient =
                    new PantryIngredient(
                            ingredient.getIngredientId(),
                            newIngredientQty,
                            unitType
                    );

            // and add it to the Pantry_Ingredient table
            pantryIngredientService.addPantryIngredient(newPantryIngredient);

            // lastly, we add the new instance to the ArrayList of PantryIngredient's
            pantryIngredients.add(newPantryIngredient);

            // returns to the main activity screen (pantry)
            goToMainActivity();
            return;

        }

        // now, if no exact or normalized ingredient exists then we'd need to make a new ingr
        // example:
        // "banana"
        // -> no "banana"?
        // -> no normalized match?
        // then
        // -> create new Ingredient
        Ingredient newIngredient = new Ingredient(ingredientName);

        // and add it to the Ingredient table
        // id is set automatically
        ingredientService.addIngredient(newIngredient);

        // this creates a new pantry ingredient
        PantryIngredient newPantryIngredient =
                new PantryIngredient(
                        newIngredient.getIngredientId(),
                        newIngredientQty,
                        unitType
                );

        // then this adds it to the pantry ingredient table
        pantryIngredientService.addPantryIngredient(newPantryIngredient);

        // and now it gets added to the ArrayList of pantry ingredients
        pantryIngredients.add(newPantryIngredient);

        goToMainActivity(); // goes back to the main activity
    }

    // I'd say that this is one of the key methods in our Smart Pantry Manager
    // its purpose is to normalize the entered ingredient name (from form), and have that value
    // be compared with the already seeded ingredient names in the Ingredient table
    public Ingredient normalizeAndFindIngredient(
            IngredientNameNormalizer ingredientNameNormalizer,
            String ingredientName){

        Ingredient ingredient;

        // checks if the ingredient name ends with "ies" and converts it to a value ending with a "y"
        if (ingredientName.endsWith("ies")) {

            String normalizedIngredientName = ingredientNameNormalizer.convertIesToY();

            ingredient = ingredientService.findIngredientByName(normalizedIngredientName);

            if (ingredient != null){
                return ingredient; // only returns if ingredient != null
                // (meaning it goes to the next check if not found)
            }
        }

        // this removes "s" from a word that ends with it and looks for the ingr in the Ingredient table
        if (ingredientName.endsWith("s")) {

            String normalizedIngredientName = ingredientNameNormalizer.removeS();

            ingredient = ingredientService.findIngredientByName(normalizedIngredientName);

            // if not found, then it goes to the next check
            // perfect if we're trying to match "tomatoes" but it normalizes as "tomatoe"
            // it can just go to the next normalization technique, converting -> "tomato" correctly
            if (ingredient != null){
                return ingredient;
            }

        }

        // and this removes "es" if the ingredient name ends with it
        if (ingredientName.endsWith("es")) {

            String normalizedIngredientName = ingredientNameNormalizer.removeEs();

            ingredient = ingredientService.findIngredientByName(normalizedIngredientName);

            if (ingredient != null){
                return ingredient;
            }
        }

        // if there was no normalized ingredient found then it returns null
        // this means that the entered ingredient will be its own new instance after looking up failed
        return null;
    }

    // this method is meant to check if a pantry ingredient exists using an ingredient id
    public boolean pantryIngredientExistsByIngredientId(int ingredientId){

        // if the pantry array list has not been loaded,
        // them there is no existing pantry ingredient to check
        if (pantryIngredients == null){
            return false;
        }

        // this loop checks every ingredient that's currently in the pantry.
        for (PantryIngredient pantryIngredient : pantryIngredients){

            // compares the Ingredient id instead of comparing the ingredient names
            // and if the ids are same, then this ingredient is already in the pantry
            if (pantryIngredient.getIngredientId() == ingredientId){
                return true;
            }
        }

        return false; // returns false if no pantry ingredient with this ingr id was found.
    }

    private void goToMainActivity(){

        // this creates the intent to navigate from the Add Ingredient screen
        // back to the MainActivity (my pantry screen)
        Intent intent = new Intent(
                AddIngredientActivity.this,
                MainActivity.class
        );

        // opens the MainActivity
        startActivity(intent);

        // while this closes the current AddIngredientActivity to ensure it's not
        // left in the Activity back stack.
        finish();
    }
}