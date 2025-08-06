package com.saveetha.foodstall.adapter; // Make sure this matches your package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.Stall;

import java.util.List;

public class StallsAdapter extends RecyclerView.Adapter<StallsAdapter.StallViewHolder> {

    private final List<Stall> stalls;

    public StallsAdapter(List<Stall> stalls) {
        this.stalls = stalls;
    }

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food_stall, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        Stall stall = stalls.get(position);
        holder.stallName.setText(stall.name);
        holder.stallRating.setText(stall.rating + " ★");
        holder.stallTimings.setText(stall.timings);
        holder.stallImage.setImageResource(stall.imageResId);
        holder.stallStatus.setText("● " + stall.status);

        // Set color based on status
        if ("Open".equals(stall.status)) {
            holder.stallStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.stallStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return stalls.size();
    }

    public static class StallViewHolder extends RecyclerView.ViewHolder {
        ImageView stallImage;
        TextView stallName, stallRating, stallTimings, stallStatus;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImage = itemView.findViewById(R.id.stall_image);
            stallName = itemView.findViewById(R.id.stall_name);
            stallRating = itemView.findViewById(R.id.stall_rating);
            stallTimings = itemView.findViewById(R.id.stall_timings);
            stallStatus = itemView.findViewById(R.id.stall_status);
        }
    }
}