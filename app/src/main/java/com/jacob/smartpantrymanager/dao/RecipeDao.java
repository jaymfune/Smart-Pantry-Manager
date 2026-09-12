package com.jacob.smartpantrymanager.dao;

// Room annotations used to define database operations.
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.jacob.smartpantrymanager.model.Recipe;
import com.jacob.smartpantrymanager.model.RecipeIngredient;

// This interface contains all database operations for recipes.
@Dao
public interface RecipeDao {

    // Adds a new recipe to the database.
    @Insert
    long insertRecipe(Recipe recipe);

    // Adds an ingredient to a recipe.
    @Insert
    void insertIngredient(RecipeIngredient ingredient);

    // Gets all recipes and sorts them alphabetically by name.
    @Query("SELECT * FROM recipes ORDER BY name ASC")
    List<Recipe> getAllRecipes();

    // Gets one recipe using its ID.
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);

    // Gets all ingredients that belong to a specific recipe.
    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);

    // Counts how many recipes are currently in the database.
    @Query("SELECT COUNT(*) FROM recipes")
    int getRecipeCount();
}