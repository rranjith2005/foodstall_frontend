package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.MenuItem;
import java.util.ArrayList;
import java.util.Locale;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    // The listener interface now passes the whole MenuItem object
    public interface OnAddItemClickListener {
        void onAddItemClick(MenuItem item);
    }

    private final ArrayList<MenuItem> menuItems;
    private final OnAddItemClickListener listener;

    public MenuItemAdapter(ArrayList<MenuItem> menuItems, OnAddItemClickListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_item, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    // Renamed this inner class for clarity
    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice;
        Button addButton;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Using the correct IDs from your card_menu_item.xml
            itemImage = itemView.findViewById(R.id.menuItemImageView);
            itemName = itemView.findViewById(R.id.menuItemNameTextView);
            itemPrice = itemView.findViewById(R.id.menuItemPriceTextView);
            addButton = itemView.findViewById(R.id.menuItemAddButton);
        }

        void bind(final MenuItem item) {
            // Using getter methods to access private data
            itemName.setText(item.getName());
            itemPrice.setText(String.format(Locale.getDefault(), "₹%.2f", item.getPrice()));
            itemImage.setImageResource(item.getImageResId());

            // The listener now passes the entire item object back to the activity
            addButton.setOnClickListener(v -> listener.onAddItemClick(item));
        }
    }
}