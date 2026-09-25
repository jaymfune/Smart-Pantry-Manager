package com.jacob.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.jacob.smartpantrymanager.R;
import com.jacob.smartpantrymanager.model.PantryItem;

// Adapter used to display pantry items in the RecyclerView.
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    // Interface used to tell the Activity when an item is clicked or deleted.
    public interface OnItemClickListener {

        // Called when a pantry item is clicked.
        void onItemClick(PantryItem item);

        // Called when the delete button is clicked.
        void onDeleteClick(PantryItem item);
    }

    // List of pantry items that will be displayed.
    private List<PantryItem> items;

    // Used to send click events back to the Activity.
    private final OnItemClickListener listener;

    // Creates the adapter with the pantry items and click listener.
    public PantryAdapter(List<PantryItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    // Updates the list of pantry items.
    public void setItems(List<PantryItem> newItems) {
        this.items = newItems;

        // Tell the RecyclerView to refresh and show the new list.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Load the layout used for one pantry item.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        // Create and return a ViewHolder for the item.
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        // Get the pantry item at the current position in the list.
        PantryItem item = items.get(position);

        // Display the ingredient name.
        holder.tvName.setText(item.name);

        // Create text showing the quantity and unit.
        // Example: "2 kg"
        holder.tvQuantity.setText(
                com.jacob.smartpantrymanager.logic.UnitDisplayHelper.formatForDisplay(
                        holder.itemView.getContext(), item.quantity, item.unit));

        // Tell the listener when the pantry item is clicked.
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));

        // Tell the listener when the delete button is clicked.
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(item));
    }

    @Override
    public int getItemCount() {

        // Return the number of pantry items in the list.
        return items.size();
    }

    // Holds the views for one pantry item.
    static class PantryViewHolder extends RecyclerView.ViewHolder {

        // TextViews used to show the ingredient name and quantity.
        TextView tvName, tvQuantity;

        // Button used to delete the pantry item.
        ImageButton btnDelete;

        PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Connect the Java variables to the views in item_pantry.xml.
            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvQuantity = itemView.findViewById(R.id.tvIngredientQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}