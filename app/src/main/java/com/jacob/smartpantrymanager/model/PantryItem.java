package com.jacob.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "pantry_items")
public class PantryItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    public double quantity;

    @NonNull
    public String unit;

    // Nullable: stored as epoch millis, or null if no expiry set
    public Long expiryDate;

    public PantryItem(@NonNull String name, double quantity, @NonNull String unit, Long expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }
}