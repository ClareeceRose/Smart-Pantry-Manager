package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings_screen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // gets the title and sets it to "Settings" when using this screen
        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.settings);

        // creating a spinner for measurement types
        Spinner measurementsSpinner = (Spinner) findViewById(R.id.measurement_spinner);
        // this creates an ArrayAdapter using the defined string array
        ArrayAdapter<CharSequence> measurementsAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.measurement_options,
                android.R.layout.simple_spinner_item
        );
        // states which layout should be used when the options are listed
        measurementsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // this will apply the adapter to the spinner
        measurementsSpinner.setAdapter(measurementsAdapter);
        // listens for selected items using this activity
        measurementsSpinner.setOnItemSelectedListener(this);

        // user can move to the recipes screen by tapping the tab
        LinearLayout recipesTab = (LinearLayout) findViewById(R.id.recipe_tab);
        recipesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });

        // user can move to the pantry screen my tapping the tab
        LinearLayout pantryTab = (LinearLayout) findViewById(R.id.pantry_tab);
        pantryTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // when an item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        // this is for retrieving the selected item ans storing it
        String selectedMeasurement = parent.getItemAtPosition(pos).toString();

        // for our enums
        MeasurementSystemType measurementSystemType;

        // this is what actually sets the measurement system used
        if (selectedMeasurement.equals("Metric")){
            measurementSystemType = MeasurementSystemType.METRIC;
        }
        else {
            measurementSystemType = MeasurementSystemType.IMPERIAL;
        }
    }

    // and when an item is not selected
    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}