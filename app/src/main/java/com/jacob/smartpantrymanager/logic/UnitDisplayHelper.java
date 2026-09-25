package com.jacob.smartpantrymanager.logic;

import android.content.Context;

import com.jacob.smartpantrymanager.SettingsActivity;

public class UnitDisplayHelper {

    /**
     * Formats a quantity+unit for display, converting to the user's preferred
     * unit system if a conversion is known. This is DISPLAY ONLY - the underlying
     * stored quantity/unit never changes, and matching logic always compares
     * the original stored values, not the converted display values.
     */
    public static String formatForDisplay(Context context, double quantity, String unit) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(
                SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String system = prefs.getString(SettingsActivity.KEY_UNIT_SYSTEM, "metric");

        String u = unit.trim().toLowerCase();

        if (system.equals("imperial")) {
            if (u.equals("g")) {
                return round(quantity / 28.35) + " oz";
            }
            if (u.equals("kg")) {
                return round(quantity * 2.205) + " lb";
            }
            if (u.equals("ml")) {
                return round(quantity / 236.6) + " cup";
            }
            if (u.equals("l")) {
                return round(quantity * 4.227) + " cup";
            }
        } else { // metric
            if (u.equals("oz")) {
                return round(quantity * 28.35) + " g";
            }
            if (u.equals("lb")) {
                return round(quantity / 2.205) + " kg";
            }
            if (u.equals("cup")) {
                return round(quantity * 236.6) + " ml";
            }
        }

        // No known conversion for this unit (e.g. "unit", "clove", "slice", "pinch") - show as-is
        return round(quantity) + " " + unit;
    }

    private static String round(double value) {
        return String.valueOf(Math.round(value * 10.0) / 10.0);
    }
}