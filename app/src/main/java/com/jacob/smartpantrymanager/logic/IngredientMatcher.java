package com.jacob.smartpantrymanager.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jacob.smartpantrymanager.model.PantryItem;
import com.jacob.smartpantrymanager.model.RecipeIngredient;

// Checks whether the ingredients needed for a recipe are available in the pantry.
public class IngredientMatcher {

    // Stores weight units and their value in grams.
    // For example, 1 kg = 1000 g.
    private static final Map<String, Double> WEIGHT_TO_GRAMS = new HashMap<>();

    // Stores volume units and their value in millilitres.
    // For example, 1 litre = 1000 ml.
    private static final Map<String, Double> VOLUME_TO_ML = new HashMap<>();

    // Add the units that the app knows how to convert.
    static {
        WEIGHT_TO_GRAMS.put("g", 1.0);
        WEIGHT_TO_GRAMS.put("kg", 1000.0);

        VOLUME_TO_ML.put("ml", 1.0);
        VOLUME_TO_ML.put("l", 1000.0);
    }

    /**
     * Changes an ingredient name into a standard format.
     * This makes names like " Eggs " and "eggs" easier to compare.
     */
    public static String normalizeName(String rawName) {

        // Remove extra spaces and change the name to lowercase.
        String name = rawName.trim().toLowerCase();

        // Remove "es" from the end of some plural words.
        // Example: "tomatoes" becomes "tomato".
        if (name.endsWith("es") && name.length() > 3) {
            name = name.substring(0, name.length() - 2);

            // Otherwise, remove "s" from the end of a word.
            // Example: "eggs" becomes "egg".
        } else if (name.endsWith("s") && name.length() > 2) {
            name = name.substring(0, name.length() - 1);
        }

        // Return the cleaned ingredient name.
        return name;
    }

    /**
     * Creates a quick lookup list of the user's pantry items.
     * Each item is stored using its cleaned ingredient name.
     */
    public static Map<String, PantryItem> buildPantryLookup(
            List<PantryItem> pantryItems) {

        // Create a map to store pantry items by ingredient name.
        Map<String, PantryItem> lookup = new HashMap<>();

        // Go through every item in the pantry.
        for (PantryItem item : pantryItems) {

            // Use the cleaned ingredient name as the map key.
            lookup.put(normalizeName(item.name), item);
        }

        // Return the completed pantry lookup.
        return lookup;
    }

    /**
     * Checks if one recipe ingredient is available in the pantry.
     */
    public static boolean isIngredientSatisfied(
            RecipeIngredient required,
            Map<String, PantryItem> pantryLookup) {

        // Clean the recipe ingredient name so it can be compared.
        String key = normalizeName(required.ingredientName);

        // Look for the ingredient in the pantry.
        PantryItem pantryMatch = pantryLookup.get(key);

        // If the ingredient is not in the pantry, it is not satisfied.
        if (pantryMatch == null) {
            return false;
        }

        // Check if there is enough of the ingredient.
        return hasEnoughQuantity(pantryMatch, required);
    }

    // Checks whether the pantry has enough of the required ingredient.
    private static boolean hasEnoughQuantity(
            PantryItem have,
            RecipeIngredient need) {

        // Get the pantry item's unit and make it lowercase.
        String haveUnit = have.unit.trim().toLowerCase();

        // Get the recipe ingredient's unit and make it lowercase.
        String needUnit = need.unit.trim().toLowerCase();

        // If both units are exactly the same, compare the quantities directly.
        if (haveUnit.equals(needUnit)) {
            return have.quantity >= need.quantity;
        }

        // Try to convert both units to grams.
        Double haveInGrams = WEIGHT_TO_GRAMS.get(haveUnit);
        Double needInGrams = WEIGHT_TO_GRAMS.get(needUnit);

        // If both are weight units, compare them using grams.
        if (haveInGrams != null && needInGrams != null) {
            return (have.quantity * haveInGrams)
                    >= (need.quantity * needInGrams);
        }

        // Try to convert both units to millilitres.
        Double haveInMl = VOLUME_TO_ML.get(haveUnit);
        Double needInMl = VOLUME_TO_ML.get(needUnit);

        // If both are volume units, compare them using millilitres.
        if (haveInMl != null && needInMl != null) {
            return (have.quantity * haveInMl)
                    >= (need.quantity * needInMl);
        }

        // If the units cannot be converted to each other,
        // the app only checks whether the ingredient exists.
        // Example: "unit" and "clove" cannot be directly compared.
        return true;
    }

    /**
     * Checks whether the pantry has every ingredient needed for a recipe.
     */
    public static boolean recipeIsFullyMatched(
            List<RecipeIngredient> required,
            Map<String, PantryItem> pantryLookup) {

        // Check each ingredient required by the recipe.
        for (RecipeIngredient ingredient : required) {

            // If even one ingredient is missing or not enough,
            // the recipe is not fully matched.
            if (!isIngredientSatisfied(ingredient, pantryLookup)) {
                return false;
            }
        }

        // Every required ingredient is available.
        return true;
    }

    /**
     * Counts how many ingredients are missing from a recipe.
     */
    public static int countMissingIngredients(
            List<RecipeIngredient> required,
            Map<String, PantryItem> pantryLookup) {

        // Start with zero missing ingredients.
        int missing = 0;

        // Check every ingredient required by the recipe.
        for (RecipeIngredient ingredient : required) {

            // If the ingredient is not available, increase the count.
            if (!isIngredientSatisfied(ingredient, pantryLookup)) {
                missing++;
            }
        }

        // Return the total number of missing ingredients.
        return missing;
    }
}