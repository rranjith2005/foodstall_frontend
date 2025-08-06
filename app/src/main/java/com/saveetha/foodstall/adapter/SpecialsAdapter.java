package com.saveetha.foodstall.adapter; // Make sure this matches your package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.SpecialDish;

import java.util.List;

public class SpecialsAdapter extends RecyclerView.Adapter<SpecialsAdapter.SpecialViewHolder> {

    private final List<SpecialDish> specialDishes;

    public SpecialsAdapter(List<SpecialDish> specialDishes) {
        this.specialDishes = specialDishes;
    }

    @NonNull
    @Override
    public SpecialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_special_item, parent, false);
        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialViewHolder holder, int position) {
        SpecialDish dish = specialDishes.get(position);
        holder.dishName.setText(dish.name);
        holder.stallName.setText(dish.stallName);
        holder.dishPrice.setText(dish.price);
        holder.dishImage.setImageResource(dish.imageResId);
    }

    @Override
    public int getItemCount() {
        return specialDishes.size();
    }

    public static class SpecialViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImage;
        TextView dishName, stallName, dishPrice;

        public SpecialViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.dish_image);
            dishName = itemView.findViewById(R.id.dish_name);
            stallName = itemView.findViewById(R.id.stall_name);
            dishPrice = itemView.findViewById(R.id.dish_price);
        }
    }
}