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

public class LovedByAdapter extends RecyclerView.Adapter<LovedByAdapter.ViewHolder> {

    public interface OnAddItemClickListener {
        void onAddItemClick(MenuItem item);
    }

    private final ArrayList<MenuItem> lovedByItems;
    private final OnAddItemClickListener listener;

    public LovedByAdapter(ArrayList<MenuItem> lovedByItems, OnAddItemClickListener listener) {
        this.lovedByItems = lovedByItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_loved_by_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(lovedByItems.get(position));
    }

    @Override
    public int getItemCount() {
        return lovedByItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice;
        Button addButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.lovedItemImageView);
            itemName = itemView.findViewById(R.id.lovedItemNameTextView);
            itemPrice = itemView.findViewById(R.id.lovedItemPriceTextView);
            addButton = itemView.findViewById(R.id.lovedItemAddButton);
        }

        void bind(MenuItem item) {
            itemName.setText(item.getName());
            itemPrice.setText(String.format(Locale.getDefault(), "₹%.2f", item.getPrice()));
            itemImage.setImageResource(item.getImageResId());
            addButton.setOnClickListener(v -> listener.onAddItemClick(item));
        }
    }
}