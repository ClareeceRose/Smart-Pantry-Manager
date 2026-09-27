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
import com.example.smartpantrymanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

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

        // this gets the header title and changes its value to "My Pantry"
        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.my_pantry);

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
}