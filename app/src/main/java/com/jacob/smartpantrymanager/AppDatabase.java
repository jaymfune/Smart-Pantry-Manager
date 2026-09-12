package com.jacob.smartpantrymanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jacob.smartpantrymanager.dao.PantryDao;
import com.jacob.smartpantrymanager.dao.RecipeDao;
import com.jacob.smartpantrymanager.model.PantryItem;
import com.jacob.smartpantrymanager.model.Recipe;
import com.jacob.smartpantrymanager.model.RecipeIngredient;

// This tells Room which classes should be stored in the database.
@Database(
        entities = { PantryItem.class, Recipe.class, RecipeIngredient.class },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    // Gives access to the database operations for pantry items.
    public abstract PantryDao pantryDao();

    // Gives access to the database operations for recipes.
    public abstract RecipeDao recipeDao();

    // Stores one shared copy of the database.
    // "volatile" makes sure all parts of the app see the latest value.
    private static volatile AppDatabase instance;

    // Gets the database instance.
    // If it does not exist yet, it creates it.
    public static AppDatabase getInstance(Context context) {

        // Check if the database has already been created.
        if (instance == null) {

            // Prevents multiple parts of the app from creating the database
            // at the same time.
            synchronized (AppDatabase.class) {

                // Check again in case another part of the app created it
                // while we were waiting.
                if (instance == null) {

                    // Create the Room database.
                    instance = Room.databaseBuilder(
                            // Use the app's context so the database does not
                            // depend on an Activity or other screen.
                            context.getApplicationContext(),

                            // Tell Room which database class to use.
                            AppDatabase.class,

                            // Name of the database file.
                            "pantry_database"
                    ).build();
                }
            }
        }

        // Return the existing database.
        return instance;
    }
}