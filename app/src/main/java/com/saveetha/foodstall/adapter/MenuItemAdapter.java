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
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private final List<MenuItem> menuItems;
    private OnItemAddedListener mListener;

    public interface OnItemAddedListener {
        void onItemAdded(String name, double price);
    }

    // Updated constructor to accept the listener
    public MenuItemAdapter(List<MenuItem> menuItems, OnItemAddedListener listener) {
        this.menuItems = menuItems;
        this.mListener = listener;
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
        holder.dishImage.setImageResource(item.imageResId);
        holder.dishName.setText(item.name);
        holder.dishPrice.setText(item.price);

        holder.addButton.setOnClickListener(v -> {
            // Remove the currency symbol before parsing to double
            double price = Double.parseDouble(item.price.substring(1));
            mListener.onItemAdded(item.name, price);
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImage;
        TextView dishName, dishPrice;
        Button addButton;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.dish_image);
            dishName = itemView.findViewById(R.id.dish_name);
            dishPrice = itemView.findViewById(R.id.dish_price);
            addButton = itemView.findViewById(R.id.addButton);
        }
    }
}