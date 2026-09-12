// DAO means Data Access Object. It is used to work with the database.
package com.jacob.smartpantrymanager.dao;

// Room imports for creating a DAO and database operations.
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

// This is the PantryItem class that represents an item in the pantry.
import com.jacob.smartpantrymanager.model.PantryItem;

// @Dao tells Room that this interface contains database operations.
@Dao
public interface PantryDao {

    // Adds a new pantry item to the database.
    // Returns the ID that was created for the new item.
    @Insert
    long insert(PantryItem item);

    // Updates an existing pantry item in the database.
    @Update
    void update(PantryItem item);

    // Removes a pantry item from the database.
    @Delete
    void delete(PantryItem item);

    // Gets all pantry items from the database.
    // The items are sorted alphabetically by their name.
    @Query("SELECT * FROM pantry_items ORDER BY name ASC")
    List<PantryItem> getAll();

    // Gets one pantry item using its ID.
    @Query("SELECT * FROM pantry_items WHERE id = :id")
    PantryItem getById(int id);
}