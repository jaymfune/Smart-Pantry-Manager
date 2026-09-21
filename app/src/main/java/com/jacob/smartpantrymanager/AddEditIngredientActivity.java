package com.jacob.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.jacob.smartpantrymanager.model.PantryItem;

// Screen used to add a new pantry item or edit an existing one.
public class AddEditIngredientActivity extends AppCompatActivity {

    // Text fields used to enter the ingredient information.
    private TextInputEditText etName, etQuantity, etUnit;

    // Gives access to the app's database.
    private AppDatabase db;

    // Stores the ID of the item being edited.
    // -1 means we are adding a new item.
    private int editingItemId = -1;

    // Stores the pantry item that is currently being edited.
    private PantryItem existingItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout for the Add/Edit screen.
        setContentView(R.layout.activity_add_edit_ingredient);

        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the database instance.
        db = AppDatabase.getInstance(this);

        // Connect the text fields and button to the layout.
        etName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        Button btnSave = findViewById(R.id.btnSave);

        // Check if an item ID was sent from MainActivity.
        // If no ID was sent, use -1.
        editingItemId = getIntent().getIntExtra(
                MainActivity.EXTRA_PANTRY_ITEM_ID,
                -1
        );

        // If the ID is not -1, an existing item is being edited.
        if (editingItemId != -1) {

            // Change the screen title to show that we are editing.
            setTitle("Edit Ingredient");

            // Load the existing item from the database.
            loadExistingItem();

        } else {

            // Otherwise, this screen is being used to add a new item.
            setTitle("Add Ingredient");
        }

        // Save the item when the Save button is clicked.
        btnSave.setOnClickListener(v -> saveItem());
    }

    // Loads the existing pantry item from the database.
    private void loadExistingItem() {

        // Run the database work in the background.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // Find the item using its ID.
            existingItem = db.pantryDao().getById(editingItemId);

            // Update the screen after the database work is finished.
            runOnUiThread(() -> {

                // Make sure the item was found.
                if (existingItem != null) {

                    // Put the existing name into the name field.
                    etName.setText(existingItem.name);

                    // Put the existing quantity into the quantity field.
                    etQuantity.setText(String.valueOf(existingItem.quantity));

                    // Put the existing unit into the unit field.
                    etUnit.setText(existingItem.unit);
                }
            });
        });
    }

    // Checks the entered information and saves the pantry item.
    private void saveItem() {

        // Get the ingredient name from the text field.
        String name = etName.getText() != null
                ? etName.getText().toString().trim()
                : "";

        // Get the quantity from the text field.
        String quantityStr = etQuantity.getText() != null
                ? etQuantity.getText().toString().trim()
                : "";

        // Get the unit from the text field.
        String unit = etUnit.getText() != null
                ? etUnit.getText().toString().trim()
                : "";

        // Check that the ingredient name was entered.
        if (name.isEmpty()) {
            etName.setError("Ingredient name is required");
            return;
        }

        // Check that the quantity was entered.
        if (quantityStr.isEmpty()) {
            etQuantity.setError("Quantity is required");
            return;
        }

        double quantity;

        try {

            // Convert the quantity from text into a number.
            quantity = Double.parseDouble(quantityStr);

            // Make sure the quantity is greater than zero.
            if (quantity <= 0) {
                etQuantity.setError("Quantity must be greater than 0");
                return;
            }

        } catch (NumberFormatException e) {

            // Show an error if the user entered something that is not a number.
            etQuantity.setError("Enter a valid number");
            return;
        }

        // Check that a unit was entered.
        if (unit.isEmpty()) {
            etUnit.setError("Unit is required");
            return;
        }

        // Save the data in the background.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // If an existing item was selected, update it.
            if (editingItemId != -1 && existingItem != null) {

                // Update the item's information with the new values.
                existingItem.name = name;
                existingItem.quantity = quantity;
                existingItem.unit = unit;

                // Save the updated item to the database.
                db.pantryDao().update(existingItem);

            } else {

                // Create a new pantry item.
                PantryItem newItem = new PantryItem(
                        name,
                        quantity,
                        unit,
                        null
                );

                // Save the new item to the database.
                db.pantryDao().insert(newItem);
            }

            // Show the success message on the main screen.
            runOnUiThread(() -> {

                // Tell the user that the item was saved.
                Toast.makeText(
                        this,
                        "Saved",
                        Toast.LENGTH_SHORT
                ).show();

                // Close this screen and return to MainActivity.
                // MainActivity will reload the pantry list in onResume().
                finish();
            });
        });
    }
}