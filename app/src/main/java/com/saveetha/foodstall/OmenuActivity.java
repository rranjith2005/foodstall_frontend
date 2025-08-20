package com.saveetha.foodstall;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.view.View;

public class OmenuActivity extends AppCompatActivity {

    private EditText shopIdEditText, specialDishName, specialDishPrice, specialDishQuantity, openingTimeEditText, closingTimeEditText;
    private Switch workingTodaySwitch;
    private Button addMenuItemButton, addComboButton, updateMenuButton;
    private LinearLayout regularItemsContainer, comboDealsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.omenu);

        // Find views by their IDs
        shopIdEditText = findViewById(R.id.shopIdEditText);
        specialDishName = findViewById(R.id.specialDishName);
        specialDishPrice = findViewById(R.id.specialDishPrice);
        specialDishQuantity = findViewById(R.id.specialDishQuantity);
        openingTimeEditText = findViewById(R.id.openingTimeEditText);
        closingTimeEditText = findViewById(R.id.closingTimeEditText);
        workingTodaySwitch = findViewById(R.id.workingTodaySwitch);
        addMenuItemButton = findViewById(R.id.addMenuItemButton);
        addComboButton = findViewById(R.id.addComboButton);
        updateMenuButton = findViewById(R.id.updateMenuButton);
        regularItemsContainer = findViewById(R.id.regularItemsContainer);
        comboDealsContainer = findViewById(R.id.comboDealsContainer);

        // Set up the back button listener
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Set up "Add Item" button listener
        addMenuItemButton.setOnClickListener(v -> {
            Toast.makeText(this, "Adding new menu item...", Toast.LENGTH_SHORT).show();
            // You would dynamically add a new card_menu_item_editable to the regularItemsContainer here
        });

        // Set up "Add Combo" button listener
        addComboButton.setOnClickListener(v -> {
            Toast.makeText(this, "Adding new combo deal...", Toast.LENGTH_SHORT).show();
            // You would dynamically add a new card_combo_deal_editable to the comboDealsContainer here
        });

        // Set up "Update Menu" button listener
        updateMenuButton.setOnClickListener(v -> {
            // Add your logic to collect all the data from the form and submit it here
            Toast.makeText(this, "Menu updated successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}
