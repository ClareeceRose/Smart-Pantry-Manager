package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.core.enums.MeasurementSystemType;
import com.example.smartpantrymanager.core.enums.UnitType;
import com.example.smartpantrymanager.data.connection.DBHelper;
import com.example.smartpantrymanager.data.repositories.IngredientRepository;
import com.example.smartpantrymanager.data.repositories.PantryIngredientRepository;
import com.example.smartpantrymanager.domain.models.PantryIngredient;
import com.example.smartpantrymanager.domain.services.IngredientService;
import com.example.smartpantrymanager.domain.services.PantryIngredientService;
import com.example.smartpantrymanager.utils.InputValidator;

import java.util.ArrayList;

public class EditIngredientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner unitSpinner;
    private UnitType unitType;
    private ArrayList<PantryIngredient> pantryIngredients;
    private PantryIngredientService pantryIngredientService;
    private PantryIngredientRepository pantryIngredientRepo;
    private PantryIngredient pantryIngredient;
    private IngredientRepository ingredientRepo;
    private IngredientService ingredientService;
    private DBHelper dbHelper;
    TextView editIngredientErrorMsg;

    /*
        okay, so I'm just going to make the ingredient qty and unit
        editable. Why? coz if the name was editable then we'd need to check if that
        ingredient exists in the Ingredient table, creating a new instance if it doesn't,
        and reassigning a new id to the pantry ingredient.

        I'd rather keep it simple given the time.
        (I had an extension because I got delayed by a few weeks. I started with the UI but
        Android Studio kept on crashing, heating up my device, and saying I was out of memory, so that delayed me
        even more.)
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_ingredient);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_ingredient_form), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        dbHelper = new DBHelper(this);
        pantryIngredientRepo = new PantryIngredientRepository(dbHelper);
        pantryIngredientService = new PantryIngredientService(pantryIngredientRepo);
        pantryIngredients = pantryIngredientService.getAllPantryIngredients();
        ingredientRepo = new IngredientRepository(dbHelper);
        ingredientService = new IngredientService(ingredientRepo);

        // for this we'd need to get the pantry ingredient id
        int pantryIngredientId = getIntent().getIntExtra("pantry_ingredient_id", -1);

        // and find the pantry ingredient that was selected from the pantry recyclerview
        pantryIngredient = findPantryIngredientById(pantryIngredientId);

        // if the ingredient couldn't be found then it just goes back to main activity
        // coz there's nothing to edit
        if (pantryIngredient == null){
            goToMainActivity();
            return;
        }

        // now for the measurement system
        String savedMeasurement = getSharedPreferences(
                "MeasurementSystemSettings",
                MODE_PRIVATE
        ).getString("measurement_system", MeasurementSystemType.METRIC.name());

        MeasurementSystemType measurementSystemType = MeasurementSystemType.valueOf(savedMeasurement);

        // and the unit spinner
        unitSpinner = (Spinner) findViewById(R.id.edit_ingredient_unit_spinner);
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

        // gets and sets title to "Edit Ingredient"
        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.edit_ingredient_title);

        // now, we need to display the existing ingredient name
        // I changed it to a TextView instead of an EditText coz the name isn't being changed
        TextView ingredientName = findViewById(R.id.edit_ingredient_name);

        ingredientName.setText(
                ingredientService
                        .findIngredientById(pantryIngredient.getIngredientId())
                        .getIngredientName()
        );

        // and now for displaying the EXISTING qty, not just the default text like in AddIngredientActivity
        EditText ingredientQty = findViewById(R.id.edit_ingredient_qty);
        ingredientQty.setText(String.valueOf(pantryIngredient.getPantryIngredientQty()));

        // now we display the already set unit in spinner
        setExistingUnitInSpinner(pantryIngredient.getPantryIngredientUnit(), unitAdapter);

        // navigation
        // allows the user to move to the suggested recipes screen using the tab
        LinearLayout recipesTab = (LinearLayout) findViewById(R.id.recipe_tab);
        recipesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditIngredientActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });

        // and this one allows users to move to the settings screen using the tab
        LinearLayout settingsTab = (LinearLayout) findViewById(R.id.settings_tab);
        settingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditIngredientActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // so, when the cancel button is clicked, the user will be redirected to the pantry screen
        Button cancelEditIngredientBtn = (Button) findViewById(R.id.cancel_edit_ingredient_btn);
        cancelEditIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditIngredientActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // when the user clicks the confirm button the editIngredient() method will trigger
        Button confirmEditIngredientBtn = findViewById(R.id.confirm_edit_ingredient_btn);
        confirmEditIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editIngredient();
            }
        });
    }

    // for when an item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedUnit = parent.getItemAtPosition(position).toString();

        // of course, we need to convert the selected unit in the spinner to one of the UnitType enums
        if (selectedUnit.equals("g")){

            unitType = UnitType.GRAM;

        }
        else if (selectedUnit.equals("kg")) {

            unitType = UnitType.KILOGRAM;

        }
        else if (selectedUnit.equals("ml")) {

            unitType = UnitType.MILLILITER;

        }
        else if (selectedUnit.equals("l")) {
            unitType = UnitType.LITER;

        }
        else if (selectedUnit.equals("pound")) {

            unitType = UnitType.POUND;

        }
        else if (selectedUnit.equals("piece")) {
            unitType = UnitType.PIECE;

        }
        else if (selectedUnit.equals("tsp")) {

            unitType = UnitType.TEASPOON;

        }
        else if (selectedUnit.equals("tbsp")) {

            unitType = UnitType.TABLESPOON;

        }
        else if (selectedUnit.equals("oz")) {
            unitType = UnitType.OUNCE;

        }
        else if (selectedUnit.equals("fluid oz")) {

            unitType = UnitType.FLUID_OUNCE;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // this method is used to find a pantry ingredient by id
    private PantryIngredient findPantryIngredientById(int pantryIngredientId) {

        // for every pantry ingredient in the arraylist
        for (PantryIngredient pantryIngredient : pantryIngredients) {

            if (pantryIngredient.getPantryIngredientId() == pantryIngredientId) {

                return pantryIngredient;

            }
        }

        return null;
    }

    // this method sets the existing spinner value
    private void setExistingUnitInSpinner(
            UnitType existingUnit,
            ArrayAdapter<CharSequence> unitAdapter
    ) {

        String existingUnitString = "";

        if (existingUnit == UnitType.GRAM){

            existingUnitString = "g";

        }
        else if(existingUnit == UnitType.KILOGRAM){

            existingUnitString = "kg";

        }
        else if(existingUnit == UnitType.MILLILITER) {

            existingUnitString = "ml";

        }
        else if(existingUnit == UnitType.LITER) {

            existingUnitString = "l";
        }
        else if(existingUnit == UnitType.POUND) {

            existingUnitString = "pound";

        }
        else if (existingUnit == UnitType.PIECE){

            existingUnitString = "piece";

        }
        else if(existingUnit == UnitType.TEASPOON) {

            existingUnitString = "tsp";

        }
        else if(existingUnit == UnitType.TABLESPOON) {
            existingUnitString = "tbsp";

        }
        else if(existingUnit == UnitType.OUNCE){

            existingUnitString = "oz";

        }
        else if (existingUnit == UnitType.FLUID_OUNCE){

            existingUnitString = "fluid oz";
        }

        // finds the position of the existing unit in the Spinner.
        int spinnerPosition = unitAdapter.getPosition(existingUnitString);

        // its 0 coz the spinner pos index starts there
        if (spinnerPosition >= 0) {

            unitSpinner.setSelection(spinnerPosition);

        }
    }

    // this is the main method here to edit an ingredient
    public void editIngredient(){
        // getting the input field value
        EditText editIngredientQtyInput = findViewById(R.id.edit_ingredient_qty);

        // the error message - will be reassigned based on the error occurred
        editIngredientErrorMsg = findViewById(R.id.edit_ingredient_error_msg);

        // the ingr qty
        String ingredientQty =
                editIngredientQtyInput.getText()
                        .toString()
                        .trim();
        // this will validate the qty given
        if (!validateEditIngredientForm(ingredientQty)){
            return;
        }

        double newIngredientQty = Double.parseDouble(ingredientQty);

        // now we update the existing pantry ingr object with a new qty and unit
        pantryIngredient.setPantryIngredientQty(newIngredientQty);

        pantryIngredient.setPantryIngredientUnit(unitType);

        // this sends the updated object to the service that
        // sends it to the repo, performing the update action
        pantryIngredientService.updatePantryIngredient(pantryIngredient);

        // lastly, we have the user return back to the pantry
        goToMainActivity();
    }

    // validates the qty given in form
    public boolean validateEditIngredientForm(String ingredientQty){
        boolean isValid = true;

        // returns false if qty is blank or null
        if (InputValidator.isNullOrBlank(ingredientQty)){
            editIngredientErrorMsg.setText(R.string.empty_qty_error_msg);
            editIngredientErrorMsg.setVisibility(View.VISIBLE);
            isValid = false;
            return isValid;
        }

        // false if qty is not a positive decimal
        if (!InputValidator.isPositiveDecimal(ingredientQty)){
            editIngredientErrorMsg.setText(R.string.invalid_qty_error_msg);
            editIngredientErrorMsg.setVisibility(View.VISIBLE);
            isValid = false;
            return isValid;
        }

        return isValid; // returns true if it passes each check

    }

    // returns the user back to the main activity (pantry screen)
    private void goToMainActivity(){

        // this creates the intent to navigate from the Edit Ingredient screen
        // back to the MainActivity (my pantry screen)
        Intent intent = new Intent(
                EditIngredientActivity.this,
                MainActivity.class
        );

        // opens the MainActivity
        startActivity(intent);

        // while this closes the current EditIngredientActivity to ensure it's not
        // left in the Activity back stack.
        finish();
    }
}