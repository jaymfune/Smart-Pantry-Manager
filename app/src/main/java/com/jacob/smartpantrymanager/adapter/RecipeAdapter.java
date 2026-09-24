package com.jacob.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.jacob.smartpantrymanager.R;
import com.jacob.smartpantrymanager.model.Recipe;

// Adapter used to display recipes in the RecyclerView.
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    // Interface used to tell the Activity when a recipe is clicked.
    public interface OnRecipeClickListener {

        // Called when the user clicks on a recipe.
        void onRecipeClick(Recipe recipe);
    }

    // List of recipes that will be displayed.
    private List<Recipe> recipes;

    // Used to send recipe click events back to the Activity.
    private final OnRecipeClickListener listener;

    // Creates the adapter with the recipe list and click listener.
    public RecipeAdapter(List<Recipe> recipes, OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    // Updates the list of recipes shown on the screen.
    public void setRecipes(List<Recipe> newRecipes) {
        this.recipes = newRecipes;

        // Tell the RecyclerView to refresh the list.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        // Load the layout used for one recipe item.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        // Create and return a ViewHolder for the recipe.
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        // Get the recipe at the current position in the list.
        Recipe recipe = recipes.get(position);

        // Display the recipe name.
        holder.tvName.setText(recipe.name);

        // Display a short message telling the user what happens when
        // they click on the recipe.
        holder.tvSubtitle.setText("Tap to view ingredients & method");

        // Tell the listener when the recipe is clicked.
        holder.itemView.setOnClickListener(
                v -> listener.onRecipeClick(recipe)
        );
    }

    @Override
    public int getItemCount() {

        // Return the number of recipes in the list.
        return recipes.size();
    }

    // Holds the views used to display one recipe.
    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        // TextViews used to show the recipe name and subtitle.
        TextView tvName, tvSubtitle;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            // Connect the Java variables to the views in item_recipe.xml.
            tvName = itemView.findViewById(R.id.tvRecipeName);
            tvSubtitle = itemView.findViewById(R.id.tvIngredientCount);
        }
    }
}