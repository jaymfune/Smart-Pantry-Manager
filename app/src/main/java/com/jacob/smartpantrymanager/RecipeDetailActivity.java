package com.jacob.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import com.jacob.smartpantrymanager.model.Recipe;
import com.jacob.smartpantrymanager.model.RecipeIngredient;

// Activity that shows the details of a selected recipe.
public class RecipeDetailActivity extends AppCompatActivity {

    // Key used to receive the recipe ID from another Activity.
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    // TextViews used to display the recipe information.
    private TextView tvName, tvIngredients, tvSteps;

    // Reference to the Room database.
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the recipe detail layout.
        setContentView(R.layout.activity_recipe_detail);

        // Set the title shown at the top of the screen.
        setTitle("Recipe Detail");

        // Get the root view of the Activity.
        View rootView = findViewById(android.R.id.content);

        // Add padding so the screen does not overlap
        // with the status bar or navigation bar.
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {

            // Get the size of the system bar areas.
            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
            );

            // Add padding around the screen using the system bar sizes.
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        // Get the shared database instance.
        db = AppDatabase.getInstance(this);

        // Connect the Java variables to the TextViews in the layout.
        tvName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredientsList);
        tvSteps = findViewById(R.id.tvSteps);

        // Get the recipe ID that was sent from the previous Activity.
        // -1 means that no valid recipe ID was provided.
        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);

        // Only load the recipe if a valid ID was received.
        if (recipeId != -1) {
            loadRecipe(recipeId);
        }
    }

    // Loads the recipe and its ingredients from the database.
    private void loadRecipe(int recipeId) {

        // Run the database work in the background
        // so the main UI does not freeze.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // Find the recipe using its ID.
            Recipe recipe = db.recipeDao().getRecipeById(recipeId);

            // Get all ingredients belonging to this recipe.
            List<RecipeIngredient> ingredients =
                    db.recipeDao().getIngredientsForRecipe(recipeId);

            // StringBuilder is used to build one text string
            // containing all the ingredients.
            StringBuilder sb = new StringBuilder();

            // Go through each ingredient in the recipe.
            for (RecipeIngredient ing : ingredients) {
                String formatted = com.jacob.smartpantrymanager.logic.UnitDisplayHelper.formatForDisplay(
                        RecipeDetailActivity.this, ing.quantity, ing.unit);
                sb.append("• ").append(formatted).append(" ").append(ing.ingredientName).append("\n");
            }

            // Switch back to the main UI thread before
            // changing anything displayed on the screen.
            runOnUiThread(() -> {

                // Make sure the recipe was found before
                // trying to display its information.
                if (recipe != null) {

                    // Display the recipe name.
                    tvName.setText(recipe.name);

                    // Display the cooking steps.
                    tvSteps.setText(recipe.steps);
                }

                // Display the list of ingredients.
                // trim() removes extra spaces or the final newline.
                tvIngredients.setText(sb.toString().trim());
            });
        });
    }
}