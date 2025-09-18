package com.saveetha.foodstall.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.TextDrawableUtil;
import com.saveetha.foodstall.model.Stall;

import java.util.Locale;

public class StallsAdapter extends ListAdapter<Stall, StallsAdapter.StallViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Stall stall);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public StallsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Stall> DIFF_CALLBACK = new DiffUtil.ItemCallback<Stall>() {
        @Override
        public boolean areItemsTheSame(@NonNull Stall oldItem, @NonNull Stall newItem) {
            return oldItem.getStallId().equals(newItem.getStallId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Stall oldItem, @NonNull Stall newItem) {
            return oldItem.getStallName().equals(newItem.getStallName()) &&
                    oldItem.getRating() == newItem.getRating() &&
                    oldItem.isOpen() == newItem.isOpen();
        }
    };

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food_stall, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    class StallViewHolder extends RecyclerView.ViewHolder {
        TextView initials, name, rating, status, timings;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            initials = itemView.findViewById(R.id.stallInitials);
            name = itemView.findViewById(R.id.stallName);
            rating = itemView.findViewById(R.id.stallRating);
            status = itemView.findViewById(R.id.openStatus);
            timings = itemView.findViewById(R.id.stallTimings);
        }

        public void bind(final Stall stall, final OnItemClickListener listener) {
            name.setText(stall.getStallName());
            rating.setText(String.format(Locale.US, "%.1f", stall.getRating()));
            initials.setText(TextDrawableUtil.getInitials(stall.getStallName()));
            GradientDrawable bg = (GradientDrawable) initials.getBackground().mutate();
            bg.setColor(TextDrawableUtil.getColor(stall.getStallName()));

            if (stall.isOpen()) {
                status.setText("Open Now");
                status.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.green));
            } else {
                status.setText("Closed");
                status.setTextColor(Color.RED);
            }

            String opening = stall.getOpeningHours();
            String closing = stall.getClosingHours();
            if (opening != null && !opening.isEmpty() && closing != null && !closing.isEmpty()) {
                timings.setText(String.format("%s - %s", opening, closing));
                timings.setVisibility(View.VISIBLE);
            } else {
                timings.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(stall);
            });
        }
    }
}