package com.jacob.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @NonNull
    public String steps; // preparation instructions, plain text

    public Recipe(@NonNull String name, @NonNull String steps) {
        this.name = name;
        this.steps = steps;
    }
}