package com.jacob.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Activity that lets the user change the app's settings.
public class SettingsActivity extends AppCompatActivity {

    // Name used to store the app's settings.
    public static final String PREFS_NAME = "smart_pantry_prefs";

    // Key used to save whether expiry alerts are enabled.
    public static final String KEY_EXPIRY_ALERTS = "expiry_alerts_enabled";

    // Key used to save the selected unit system.
    // The value can be "metric" or "imperial".
    public static final String KEY_UNIT_SYSTEM = "unit_system";

    // Used to read and save the user's settings.
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the settings screen layout.
        setContentView(R.layout.activity_settings);

        // Set the title shown at the top of the screen.
        setTitle("Settings");

        // Get the root view of the Activity.
        View rootView = findViewById(android.R.id.content);
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            bottomNav.setPadding(
                    bottomNav.getPaddingLeft(), bottomNav.getPaddingTop(),
                    bottomNav.getPaddingRight(), systemBars.bottom);
            return insets;
        });

        // Open the SharedPreferences file where the app's
        // settings are stored.
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Connect the Java variables to the settings controls
        // in the layout.
        Switch switchExpiry = findViewById(R.id.switchExpiryAlerts);
        RadioGroup radioUnitSystem = findViewById(R.id.radioUnitSystem);

        // Load the saved expiry alert setting.
        // If no value has been saved yet, use false.
        switchExpiry.setChecked(
                prefs.getBoolean(KEY_EXPIRY_ALERTS, false)
        );

        // Load the saved unit system.
        // If no value has been saved yet, use metric.
        String savedUnitSystem = prefs.getString(
                KEY_UNIT_SYSTEM,
                "metric"
        );

        // Select the correct radio button based on
        // the saved unit system.
        radioUnitSystem.check(
                savedUnitSystem.equals("imperial")
                        ? R.id.radioImperial
                        : R.id.radioMetric
        );

        // Save the expiry alert setting whenever
        // the user turns the switch on or off.
        switchExpiry.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        prefs.edit()
                                .putBoolean(KEY_EXPIRY_ALERTS, isChecked)
                                .apply()
        );

        // Save the selected unit system whenever
        // the user chooses a different option.
        radioUnitSystem.setOnCheckedChangeListener(
                (group, checkedId) -> {

                    // Decide which unit system was selected.
                    String value = (checkedId == R.id.radioImperial)
                            ? "imperial"
                            : "metric";

                    // Save the selected unit system.
                    prefs.edit()
                            .putString(KEY_UNIT_SYSTEM, value)
                            .apply();
                }
        );

        // Set up the bottom navigation and mark
        // Settings as the current screen.
        NavigationHelper.setup(this, findViewById(R.id.bottomNavigation), R.id.nav_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationHelper.highlightTab(findViewById(R.id.bottomNavigation), R.id.nav_settings);
    }
}