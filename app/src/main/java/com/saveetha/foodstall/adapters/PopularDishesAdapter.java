package com.saveetha.foodstall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.models.PopularDish;

import java.util.List;

public class PopularDishesAdapter extends RecyclerView.Adapter<PopularDishesAdapter.PopularDishViewHolder> {

    private Context context;
    private List<PopularDish> popularDishList;

    public PopularDishesAdapter(Context context, List<PopularDish> popularDishList) {
        this.context = context;
        this.popularDishList = popularDishList;
    }

    @NonNull
    @Override
    public PopularDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_dish_circle, parent, false);
        return new PopularDishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularDishViewHolder holder, int position) {
        PopularDish dish = popularDishList.get(position);
        holder.dishName.setText(dish.getName());
        holder.price.setText("₹" + dish.getPrice());
        holder.image.setImageResource(dish.getImageResId());
    }

    @Override
    public int getItemCount() {
        return popularDishList.size();
    }

    public static class PopularDishViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView dishName, price;

        public PopularDishViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circle_image);
            dishName = itemView.findViewById(R.id.circle_dish_name);
            price = itemView.findViewById(R.id.circle_dish_price);
        }
    }
}
