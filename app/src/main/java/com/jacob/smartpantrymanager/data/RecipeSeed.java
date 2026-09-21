package com.jacob.smartpantrymanager.data;

import java.util.List;

// Holds the starting information for a recipe.
public class RecipeSeed {

    // The name of the recipe.
    public final String name;

    // The cooking instructions for the recipe.
    public final String steps;

    // The list of ingredients needed for the recipe.
    public final List<IngredientSeed> ingredients;

    // Creates a recipe with its name, steps, and ingredients.
    public RecipeSeed(String name, String steps, List<IngredientSeed> ingredients) {
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    // Holds the starting information for one recipe ingredient.
    public static class IngredientSeed {

        // The name of the ingredient.
        public final String name;

        // How much of the ingredient is needed.
        public final double quantity;

        // The unit used for the quantity, such as grams or cups.
        public final String unit;

        // Creates an ingredient with its name, quantity, and unit.
        public IngredientSeed(String name, double quantity, String unit) {
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
        }
    }
}