package com.jacob.smartpantrymanager;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHelper {

    // Call once in onCreate() - attaches the click listener only.
    public static void setup(AppCompatActivity activity, BottomNavigationView bottomNav, int selectedItemId) {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_add) {
                activity.startActivity(new Intent(activity, AddEditIngredientActivity.class));
                bottomNav.post(() -> bottomNav.setSelectedItemId(selectedItemId));
                return false;
            }

            if (id == selectedItemId) {
                return true;
            }

            Class<?> target = null;
            if (id == R.id.nav_pantry) {
                target = MainActivity.class;
            } else if (id == R.id.nav_suggested) {
                target = SuggestedRecipesActivity.class;
            } else if (id == R.id.nav_settings) {
                target = SettingsActivity.class;
            }

            if (target != null) {
                Intent intent = new Intent(activity, target);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
            return true;
        });
    }

    // Call in onResume() of every nav-bar screen - guarantees the correct
    // tab is highlighted every time the screen becomes visible, whether it
    // was freshly created or reused from the back stack.
    public static void highlightTab(BottomNavigationView bottomNav, int selectedItemId) {
        bottomNav.setSelectedItemId(selectedItemId);
    }
}