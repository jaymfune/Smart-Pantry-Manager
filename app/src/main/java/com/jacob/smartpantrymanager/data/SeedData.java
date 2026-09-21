package com.jacob.smartpantrymanager.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jacob.smartpantrymanager.data.RecipeSeed.IngredientSeed;

// Provides the starting recipe data for the app.
public class SeedData {

    // Creates and returns a list of sample recipes.
    public static List<RecipeSeed> getRecipes() {

        // Create an empty list to store all the recipes.
        List<RecipeSeed> recipes = new ArrayList<>();

        // Add the Tomato Pasta recipe.
        recipes.add(new RecipeSeed(
                "Tomato Pasta",

                // Cooking steps for the recipe.
                "1. Boil pasta until al dente.\n2. Heat oil, sauté garlic.\n3. Add chopped tomatoes, simmer 10 min.\n4. Season with salt and toss with pasta.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("pasta", 200, "g"),
                        new IngredientSeed("tomato", 3, "unit"),
                        new IngredientSeed("garlic", 2, "clove"),
                        new IngredientSeed("olive oil", 2, "tbsp"),
                        new IngredientSeed("salt", 1, "tsp")
                )
        ));

        // Add the Scrambled Eggs on Toast recipe.
        recipes.add(new RecipeSeed(
                "Scrambled Eggs on Toast",

                // Cooking steps for the recipe.
                "1. Whisk eggs with a splash of milk.\n2. Melt butter in a pan, scramble eggs on low heat.\n3. Toast bread.\n4. Serve eggs on toast with salt and pepper.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("egg", 3, "unit"),
                        new IngredientSeed("milk", 2, "tbsp"),
                        new IngredientSeed("butter", 1, "tbsp"),
                        new IngredientSeed("bread", 2, "slice"),
                        new IngredientSeed("salt", 1, "pinch")
                )
        ));

        // Add the Vegetable Fried Rice recipe.
        recipes.add(new RecipeSeed(
                "Vegetable Fried Rice",

                // Cooking steps for the recipe.
                "1. Heat oil in a wok.\n2. Add chopped onion and carrot, stir-fry 3 min.\n3. Add cooked rice and soy sauce, stir-fry 5 min.\n4. Push aside, scramble egg in, mix through.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("rice", 300, "g"),
                        new IngredientSeed("onion", 1, "unit"),
                        new IngredientSeed("carrot", 1, "unit"),
                        new IngredientSeed("egg", 1, "unit"),
                        new IngredientSeed("soy sauce", 2, "tbsp"),
                        new IngredientSeed("vegetable oil", 1, "tbsp")
                )
        ));

        // Add the Grilled Cheese Sandwich recipe.
        recipes.add(new RecipeSeed(
                "Grilled Cheese Sandwich",

                // Cooking steps for the recipe.
                "1. Butter one side of each bread slice.\n2. Place cheese between unbuttered sides.\n3. Grill in pan until golden on both sides.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("bread", 2, "slice"),
                        new IngredientSeed("cheese", 2, "slice"),
                        new IngredientSeed("butter", 1, "tbsp")
                )
        ));

        // Add the Banana Pancakes recipe.
        recipes.add(new RecipeSeed(
                "Banana Pancakes",

                // Cooking steps for the recipe.
                "1. Mash banana, whisk with egg and milk.\n2. Stir in flour until just combined.\n3. Cook spoonfuls on a greased pan until bubbles form, flip.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("banana", 1, "unit"),
                        new IngredientSeed("egg", 1, "unit"),
                        new IngredientSeed("milk", 100, "ml"),
                        new IngredientSeed("flour", 150, "g")
                )
        ));

        // Add the Chicken Stir Fry recipe.
        recipes.add(new RecipeSeed(
                "Chicken Stir Fry",

                // Cooking steps for the recipe.
                "1. Slice chicken and stir-fry until cooked.\n2. Add chopped pepper and onion, cook 4 min.\n3. Add soy sauce, toss and serve.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("chicken breast", 300, "g"),
                        new IngredientSeed("bell pepper", 1, "unit"),
                        new IngredientSeed("onion", 1, "unit"),
                        new IngredientSeed("soy sauce", 2, "tbsp"),
                        new IngredientSeed("vegetable oil", 1, "tbsp")
                )
        ));

        // Add the Simple Tomato Soup recipe.
        recipes.add(new RecipeSeed(
                "Simple Tomato Soup",

                // Cooking steps for the recipe.
                "1. Sauté onion and garlic in oil.\n2. Add chopped tomatoes and stock, simmer 15 min.\n3. Blend until smooth, season with salt.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("tomato", 5, "unit"),
                        new IngredientSeed("onion", 1, "unit"),
                        new IngredientSeed("garlic", 1, "clove"),
                        new IngredientSeed("vegetable stock", 500, "ml"),
                        new IngredientSeed("salt", 1, "tsp")
                )
        ));

        // Add the Peanut Butter Banana Toast recipe.
        recipes.add(new RecipeSeed(
                "Peanut Butter Banana Toast",

                // Cooking steps for the recipe.
                "1. Toast bread.\n2. Spread peanut butter on top.\n3. Slice banana over the toast.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("bread", 2, "slice"),
                        new IngredientSeed("peanut butter", 2, "tbsp"),
                        new IngredientSeed("banana", 1, "unit")
                )
        ));

        // Add the Omelette with Cheese recipe.
        recipes.add(new RecipeSeed(
                "Omelette with Cheese",

                // Cooking steps for the recipe.
                "1. Whisk eggs with salt.\n2. Pour into a hot buttered pan.\n3. Sprinkle cheese on top, fold when set.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("egg", 3, "unit"),
                        new IngredientSeed("cheese", 1, "slice"),
                        new IngredientSeed("butter", 1, "tbsp"),
                        new IngredientSeed("salt", 1, "pinch")
                )
        ));

        // Add the Garlic Butter Rice recipe.
        recipes.add(new RecipeSeed(
                "Garlic Butter Rice",

                // Cooking steps for the recipe.
                "1. Melt butter in a pan, sauté garlic until fragrant.\n2. Add cooked rice, stir through, season with salt.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("rice", 250, "g"),
                        new IngredientSeed("garlic", 2, "clove"),
                        new IngredientSeed("butter", 2, "tbsp"),
                        new IngredientSeed("salt", 1, "tsp")
                )
        ));

        // Add the Carrot and Potato Mash recipe.
        recipes.add(new RecipeSeed(
                "Carrot and Potato Mash",

                // Cooking steps for the recipe.
                "1. Boil chopped potato and carrot until soft.\n2. Drain, mash with butter and milk.\n3. Season with salt.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("potato", 3, "unit"),
                        new IngredientSeed("carrot", 2, "unit"),
                        new IngredientSeed("butter", 1, "tbsp"),
                        new IngredientSeed("milk", 50, "ml"),
                        new IngredientSeed("salt", 1, "tsp")
                )
        ));

        // Add the Cheese Quesadilla recipe.
        recipes.add(new RecipeSeed(
                "Cheese Quesadilla",

                // Cooking steps for the recipe.
                "1. Place cheese on one half of a tortilla, fold over.\n2. Cook in a dry pan until golden and cheese melts, flip once.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("tortilla", 1, "unit"),
                        new IngredientSeed("cheese", 2, "slice")
                )
        ));

        // Add the Onion and Egg Fried Rice recipe.
        recipes.add(new RecipeSeed(
                "Onion and Egg Fried Rice",

                // Cooking steps for the recipe.
                "1. Sauté chopped onion in oil until soft.\n2. Push aside, scramble in egg.\n3. Add rice and soy sauce, stir-fry until combined.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("rice", 300, "g"),
                        new IngredientSeed("onion", 1, "unit"),
                        new IngredientSeed("egg", 2, "unit"),
                        new IngredientSeed("soy sauce", 1, "tbsp"),
                        new IngredientSeed("vegetable oil", 1, "tbsp")
                )
        ));

        // Add the Milk and Banana Smoothie recipe.
        recipes.add(new RecipeSeed(
                "Milk and Banana Smoothie",

                // Cooking steps for the recipe.
                "1. Add banana and milk to a blender.\n2. Blend until smooth. Serve chilled.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("banana", 2, "unit"),
                        new IngredientSeed("milk", 250, "ml")
                )
        ));

        // Add the Chicken and Rice Bowl recipe.
        recipes.add(new RecipeSeed(
                "Chicken and Rice Bowl",

                // Cooking steps for the recipe.
                "1. Cook chicken breast, slice thin.\n2. Serve over cooked rice.\n3. Drizzle with soy sauce.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("chicken breast", 250, "g"),
                        new IngredientSeed("rice", 250, "g"),
                        new IngredientSeed("soy sauce", 1, "tbsp")
                )
        ));

        // Add the Buttered Potato Bake recipe.
        recipes.add(new RecipeSeed(
                "Buttered Potato Bake",

                // Cooking steps for the recipe.
                "1. Slice potatoes thin.\n2. Layer in a dish with butter, salt, and a splash of milk.\n3. Bake at 180°C for 40 min until soft.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("potato", 4, "unit"),
                        new IngredientSeed("butter", 2, "tbsp"),
                        new IngredientSeed("milk", 100, "ml"),
                        new IngredientSeed("salt", 1, "tsp")
                )
        ));

        // Add the Pepper and Onion Omelette recipe.
        recipes.add(new RecipeSeed(
                "Pepper and Onion Omelette",

                // Cooking steps for the recipe.
                "1. Sauté chopped pepper and onion until soft.\n2. Pour whisked eggs over, cook until set.\n3. Season with salt.",

                // Ingredients needed for the recipe.
                Arrays.asList(
                        new IngredientSeed("egg", 3, "unit"),
                        new IngredientSeed("bell pepper", 1, "unit"),
                        new IngredientSeed("onion", 1, "unit"),
                        new IngredientSeed("salt", 1, "pinch")
                )
        ));

        // Return the complete list of sample recipes.
        return recipes;
    }
}