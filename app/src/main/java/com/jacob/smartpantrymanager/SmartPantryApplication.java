package com.jacob.smartpantrymanager;

import android.app.Application;
import android.util.Log;

import java.util.List;

import com.jacob.smartpantrymanager.data.RecipeSeed;
import com.jacob.smartpantrymanager.data.SeedData;
import com.jacob.smartpantrymanager.model.Recipe;
import com.jacob.smartpantrymanager.model.RecipeIngredient;

// This class runs when the app starts.
public class SmartPantryApplication extends Application {

    // Used to identify messages from this class in Logcat.
    private static final String TAG = "SmartPantryApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Run the database work in the background
        // so it does not slow down the app's main screen.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // Get the app's database.
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());

            // Check if there are already recipes in the database.
            if (db.recipeDao().getRecipeCount() == 0) {

                // No recipes were found, so add the starting recipes.
                Log.d(TAG, "No recipes found, seeding database...");

                // Get the list of recipes we want to add.
                List<RecipeSeed> seeds = SeedData.getRecipes();

                // Go through each recipe in the starting recipe list.
                for (RecipeSeed seed : seeds) {

                    // Create a Recipe object using the recipe name and steps.
                    Recipe recipe = new Recipe(seed.name, seed.steps);

                    // Save the recipe and get its database ID.
                    long recipeId = db.recipeDao().insertRecipe(recipe);

                    // Go through all the ingredients for this recipe.
                    for (RecipeSeed.IngredientSeed ingredientSeed : seed.ingredients) {

                        // Create a RecipeIngredient object.
                        RecipeIngredient ri = new RecipeIngredient(
                                // Connect the ingredient to the recipe we just created.
                                (int) recipeId,

                                // Ingredient name.
                                ingredientSeed.name,

                                // Amount of the ingredient needed.
                                ingredientSeed.quantity,

                                // Unit used for the amount.
                                ingredientSeed.unit
                        );

                        // Save the ingredient in the database.
                        db.recipeDao().insertIngredient(ri);
                    }
                }

                // Show in Logcat how many recipes were added.
                Log.d(TAG, "Seeding complete: " + seeds.size() + " recipes inserted.");

            } else {

                // Recipes already exist, so there is no need to add them again.
                Log.d(TAG, "Recipes already present, skipping seed.");
            }
        });
    }
}