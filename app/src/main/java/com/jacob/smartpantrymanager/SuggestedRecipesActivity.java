package com.jacob.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jacob.smartpantrymanager.adapter.RecipeAdapter;
import com.jacob.smartpantrymanager.logic.IngredientMatcher;
import com.jacob.smartpantrymanager.model.PantryItem;
import com.jacob.smartpantrymanager.model.Recipe;
import com.jacob.smartpantrymanager.model.RecipeIngredient;

// Activity that shows recipes the user can currently make
// using the ingredients available in their pantry.
public class SuggestedRecipesActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeClickListener {

    // Key used to pass a recipe ID between Activities.
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    // RecyclerView used to display the suggested recipes.
    private RecyclerView recyclerView;

    // View shown when there are no recipes that match the pantry.
    private View emptyState;

    // Adapter responsible for displaying the recipes.
    private RecipeAdapter adapter;

    // Reference to the Room database.
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout for the suggested recipes screen.
        setContentView(R.layout.activity_suggested_recipes);

        // Set the title shown at the top of the screen.
        setTitle("Suggested Recipes");

        // Get the root view of the Activity.
        View rootView = findViewById(android.R.id.content);
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            bottomNav.setPadding(
                    bottomNav.getPaddingLeft(), bottomNav.getPaddingTop(),
                    bottomNav.getPaddingRight(), systemBars.bottom);
            return insets;
        });

        // Get the shared database instance.
        db = AppDatabase.getInstance(this);

        // Connect the RecyclerView to the layout.
        recyclerView = findViewById(R.id.rvSuggestedRecipes);

        // Connect the empty state view.
        emptyState = findViewById(R.id.tvNoMatches);

        // Use a vertical list layout for the recipes.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create the recipe adapter with an empty list.
        // The Activity itself will handle recipe clicks.
        adapter = new RecipeAdapter(new ArrayList<>(), this);

        // Connect the adapter to the RecyclerView.
        recyclerView.setAdapter(adapter);

        // Bottom navigation
        NavigationHelper.setup(this, findViewById(R.id.bottomNavigation), R.id.nav_pantry);NavigationHelper.setup(this, findViewById(R.id.bottomNavigation), R.id.nav_suggested);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationHelper.highlightTab(findViewById(R.id.bottomNavigation), R.id.nav_suggested);
        loadSuggestedRecipes();
    }

    // Finds all recipes that can be completely made
    // using the ingredients currently in the pantry.
    private void loadSuggestedRecipes() {

        // Run the database work in the background so the UI
        // does not freeze while the database is being checked.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // Get all ingredients currently stored in the pantry.
            List<PantryItem> pantryItems = db.pantryDao().getAll();

            // Create a quick lookup of pantry ingredients by name.
            Map<String, PantryItem> pantryLookup =
                    IngredientMatcher.buildPantryLookup(pantryItems);

            // Get all recipes stored in the database.
            List<Recipe> allRecipes = db.recipeDao().getAllRecipes();

            // This list will contain only recipes that can be made.
            List<Recipe> matchedRecipes = new ArrayList<>();

            // Check every recipe in the database.
            for (Recipe recipe : allRecipes) {

                // Get the ingredients required for this recipe.
                List<RecipeIngredient> required =
                        db.recipeDao().getIngredientsForRecipe(recipe.id);

                // Check whether the pantry contains enough
                // ingredients to make the complete recipe.
                if (IngredientMatcher.recipeIsFullyMatched(
                        required,
                        pantryLookup)) {

                    // If all ingredients are available,
                    // add the recipe to the suggested list.
                    matchedRecipes.add(recipe);
                }
            }

            // Switch back to the main UI thread before
            // changing anything on the screen.
            runOnUiThread(() -> {

                // Give the matching recipes to the adapter.
                adapter.setRecipes(matchedRecipes);

                // Show the empty message if there are no matches.
                // Otherwise, hide it.
                emptyState.setVisibility(
                        matchedRecipes.isEmpty()
                                ? View.VISIBLE
                                : View.GONE
                );
            });
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipe.id);
        startActivity(intent);
    }
}