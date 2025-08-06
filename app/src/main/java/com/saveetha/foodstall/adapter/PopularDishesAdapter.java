package com.saveetha.foodstall.adapter; // Make sure this matches your package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.PopularDish;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView; // You need to add this dependency

public class PopularDishesAdapter extends RecyclerView.Adapter<PopularDishesAdapter.PopularViewHolder> {

    private final List<PopularDish> popularDishes;

    public PopularDishesAdapter(List<PopularDish> popularDishes) {
        this.popularDishes = popularDishes;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_dish, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        PopularDish dish = popularDishes.get(position);
        holder.dishName.setText(dish.name);
        holder.dishPrice.setText(dish.price);
        holder.dishImage.setImageResource(dish.imageResId);
    }

    @Override
    public int getItemCount() {
        return popularDishes.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder {
        CircleImageView dishImage;
        TextView dishName, dishPrice;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.popular_dish_image);
            dishName = itemView.findViewById(R.id.popular_dish_name);
            dishPrice = itemView.findViewById(R.id.popular_dish_price);
        }
    }
}