package com.saveetha.foodstall.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.saveetha.foodstall.ApiClient;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.TextDrawableUtil;
import com.saveetha.foodstall.model.PopularDish;
import java.util.Locale;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class PopularDishesAdapter extends ListAdapter<PopularDish, PopularDishesAdapter.PopularViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PopularDish dish);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PopularDishesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<PopularDish> DIFF_CALLBACK = new DiffUtil.ItemCallback<PopularDish>() {
        @Override
        public boolean areItemsTheSame(@NonNull PopularDish oldItem, @NonNull PopularDish newItem) {
            return Objects.equals(oldItem.getStallId(), newItem.getStallId()) &&
                    Objects.equals(oldItem.getDishName(), newItem.getDishName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PopularDish oldItem, @NonNull PopularDish newItem) {
            // Assumes PopularDish has a proper .equals() method
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_dish, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    class PopularViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, stallName, price, initials;
        CircleImageView dishImage;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.popularDishName);
            stallName = itemView.findViewById(R.id.popularStallName);
            price = itemView.findViewById(R.id.popularDishPrice);
            initials = itemView.findViewById(R.id.popularDishInitials);
            dishImage = itemView.findViewById(R.id.popularDishImage);
        }

        public void bind(final PopularDish dish, final OnItemClickListener listener) {
            dishName.setText(dish.getDishName());
            stallName.setText(dish.getStallName());
            price.setText(String.format(Locale.getDefault(), "₹%.2f", dish.getPrice()));

            String imageUrl = dish.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                initials.setVisibility(View.GONE);
                dishImage.setVisibility(View.VISIBLE);
                String fullUrl = ApiClient.BASE_URL + "uploads/" + imageUrl;
                Glide.with(itemView.getContext()).load(fullUrl).into(dishImage);
            } else {
                // --- START OF UPDATED SECTION ---
                dishImage.setVisibility(View.GONE);
                initials.setVisibility(View.VISIBLE);
                initials.setText(TextDrawableUtil.getInitials(dish.getDishName()));

                // Get the circular background and set its color dynamically
                GradientDrawable background = (GradientDrawable) initials.getBackground().mutate();
                background.setColor(TextDrawableUtil.getColor(dish.getDishName()));
                // --- END OF UPDATED SECTION ---
            }

            itemView.setOnClickListener(v -> {
                if(listener != null) listener.onItemClick(dish);
            });
        }
    }
}