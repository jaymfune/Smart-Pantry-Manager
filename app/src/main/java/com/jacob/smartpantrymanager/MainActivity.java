package com.jacob.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.jacob.smartpantrymanager.adapter.PantryAdapter;
import com.jacob.smartpantrymanager.model.PantryItem;

// Main screen of the app.
// Displays all the items stored in the pantry.
public class MainActivity extends AppCompatActivity
        implements PantryAdapter.OnItemClickListener {

    // Key used to send the pantry item's ID to another screen.
    public static final String EXTRA_PANTRY_ITEM_ID = "extra_pantry_item_id";

    // RecyclerView that displays the pantry items.
    private RecyclerView recyclerView;

    // View shown when there are no pantry items.
    private View emptyState;

    // Adapter that connects pantry data to the RecyclerView.
    private PantryAdapter adapter;

    // Gives access to the app's database.
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout for the main screen.
        setContentView(R.layout.activity_main);

        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the database instance.
        db = AppDatabase.getInstance(this);

        // Connect the Java variables to the views in the layout.
        recyclerView = findViewById(R.id.rvPantryItems);
        emptyState = findViewById(R.id.tvEmptyState);

        // Set the RecyclerView to display items in a vertical list.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create the adapter with an empty list.
        // "this" allows this Activity to receive item click events.
        adapter = new PantryAdapter(new ArrayList<>(), this);

        // Connect the adapter to the RecyclerView.
        recyclerView.setAdapter(adapter);

        // When the add button is clicked, open the Add/Edit screen.
        findViewById(R.id.fabAddItem).setOnClickListener(v -> {

            // Create an Intent to open AddEditIngredientActivity.
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            // Open the Add/Edit ingredient screen.
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reload the pantry items every time we return to this screen.
        // This makes sure new, edited, or deleted items appear immediately.
        loadPantryItems();
    }

    // Loads all pantry items from the database.
    private void loadPantryItems() {

        // Run the database work in the background.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // Get all pantry items from the database.
            List<PantryItem> items = db.pantryDao().getAll();

            // Update the screen after the database work is finished.
            runOnUiThread(() -> {

                // Give the items to the adapter so they can be displayed.
                adapter.setItems(items);

                // Show the empty message if there are no items.
                // Hide it when there are items.
                emptyState.setVisibility(
                        items.isEmpty() ? View.VISIBLE : View.GONE
                );
            });
        });
    }

    @Override
    public void onItemClick(PantryItem item) {

        // Create an Intent to open the Add/Edit screen.
        Intent intent = new Intent(
                MainActivity.this,
                AddEditIngredientActivity.class
        );

        // Send the selected item's ID to the Add/Edit screen.
        // This tells that screen which item should be edited.
        intent.putExtra(EXTRA_PANTRY_ITEM_ID, item.id);

        // Open the Add/Edit screen.
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(PantryItem item) {

        // Run the delete operation in the background.
        AppExecutors.getInstance().diskIO().execute(() -> {

            // Delete the selected pantry item from the database.
            db.pantryDao().delete(item);

            // Reload the pantry list after deleting the item.
            runOnUiThread(this::loadPantryItems);
        });
    }
}