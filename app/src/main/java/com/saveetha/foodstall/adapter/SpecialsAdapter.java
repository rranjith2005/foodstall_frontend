package com.saveetha.foodstall.adapter;

import android.graphics.drawable.Drawable;
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
import com.saveetha.foodstall.model.SpecialDish;
import java.util.Locale;
import java.util.Objects;

public class SpecialsAdapter extends ListAdapter<SpecialDish, SpecialsAdapter.SpecialViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SpecialDish dish);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SpecialsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<SpecialDish> DIFF_CALLBACK = new DiffUtil.ItemCallback<SpecialDish>() {
        @Override
        public boolean areItemsTheSame(@NonNull SpecialDish oldItem, @NonNull SpecialDish newItem) {
            return Objects.equals(oldItem.getStallId(), newItem.getStallId()) &&
                    Objects.equals(oldItem.getDishName(), newItem.getDishName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SpecialDish oldItem, @NonNull SpecialDish newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public SpecialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_special_item, parent, false);
        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialViewHolder holder, int position) {
        SpecialDish currentDish = getItem(position);
        if (currentDish != null) {
            holder.bind(currentDish, listener);
        }
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImageView;
        TextView dishInitialsTextView, dishNameTextView, stallNameTextView, priceTextView;

        public SpecialViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImageView = itemView.findViewById(R.id.specialDishImage);
            dishInitialsTextView = itemView.findViewById(R.id.specialDishInitials);
            dishNameTextView = itemView.findViewById(R.id.specialDishName);
            stallNameTextView = itemView.findViewById(R.id.specialStallName);
            priceTextView = itemView.findViewById(R.id.specialDishPrice);
        }

        public void bind(final SpecialDish dish, final OnItemClickListener listener) {
            dishNameTextView.setText(dish.getDishName());
            stallNameTextView.setText(dish.getStallName());
            priceTextView.setText(String.format(Locale.getDefault(), "₹%.2f", dish.getPrice()));

            String imageUrl = dish.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // If there IS an image, hide initials and load the image with Glide
                dishInitialsTextView.setVisibility(View.GONE);
                dishImageView.setVisibility(View.VISIBLE);
                String fullUrl = ApiClient.BASE_URL + "uploads/" + imageUrl;
                Glide.with(itemView.getContext()).load(fullUrl).into(dishImageView);
            } else {
                // --- START OF UPDATED SECTION ---
                // If there is NO image, use your TextDrawableUtil to create the initials drawable
                // and set it on the main ImageView.
                dishInitialsTextView.setVisibility(View.GONE);
                dishImageView.setVisibility(View.VISIBLE);
                Drawable initialsDrawable = TextDrawableUtil.getInitialDrawable(dish.getDishName());
                dishImageView.setImageDrawable(initialsDrawable);
                // --- END OF UPDATED SECTION ---
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(dish);
                }
            });
        }
    }
}