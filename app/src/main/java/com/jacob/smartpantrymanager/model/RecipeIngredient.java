package com.jacob.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.annotation.NonNull;

@Entity(
        tableName = "recipe_ingredients",
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = { @Index("recipeId") }
)
public class RecipeIngredient {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int recipeId;

    @NonNull
    public String ingredientName;

    public double quantity;

    @NonNull
    public String unit;

    public RecipeIngredient(int recipeId, @NonNull String ingredientName, double quantity, @NonNull String unit) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
    }
}
