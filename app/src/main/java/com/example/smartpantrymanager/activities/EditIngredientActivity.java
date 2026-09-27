package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.R;

public class EditIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_ingredient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.edit_ingredient_title);

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
    }
}