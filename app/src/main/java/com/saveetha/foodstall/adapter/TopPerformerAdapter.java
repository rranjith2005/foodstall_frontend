package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.TopPerformer;
import java.util.List;
import android.graphics.Color;
import android.graphics.PorterDuff;


public class TopPerformerAdapter extends RecyclerView.Adapter<TopPerformerAdapter.TopPerformerViewHolder> {
    private final List<TopPerformer> performersList;

    public TopPerformerAdapter(List<TopPerformer> performersList) {
        this.performersList = performersList;
    }

    @NonNull
    @Override
    public TopPerformerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_top_performer, parent, false);
        return new TopPerformerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPerformerViewHolder holder, int position) {
        TopPerformer performer = performersList.get(position);

        // Set rank icon and color based on rank
        if (performer.isTopSeller) {
            holder.rankIcon.setImageResource(R.drawable.ic_trophy);
            holder.rankIcon.setColorFilter(Color.parseColor("#EAB308"));
        } else if (performer.isLowestSeller) {
            holder.rankIcon.setImageResource(R.drawable.ic_trophy);
            holder.rankIcon.setColorFilter(Color.parseColor("#4B5563"));
        } else {
            holder.rankIcon.setImageResource(R.drawable.ic_trophy);
            holder.rankIcon.setColorFilter(Color.parseColor("#F97316"));
        }

        // Set trend icon based on trending status
        holder.trendIcon.setImageResource(performer.isTrendingUp ? R.drawable.ic_increasing : R.drawable.ic_decreasing);

        holder.dishImage.setImageResource(performer.dishImageResId);
        holder.dishName.setText(performer.dishName);
        holder.details.setText(performer.unitsSold + " units • " + performer.revenue);
    }

    @Override
    public int getItemCount() {
        return performersList.size();
    }

    public static class TopPerformerViewHolder extends RecyclerView.ViewHolder {
        ImageView rankIcon, dishImage, trendIcon;
        TextView dishName, details;

        public TopPerformerViewHolder(@NonNull View itemView) {
            super(itemView);
            rankIcon = itemView.findViewById(R.id.rankIcon);
            dishImage = itemView.findViewById(R.id.dishImage);
            trendIcon = itemView.findViewById(R.id.trendIcon);
            dishName = itemView.findViewById(R.id.dishName);
            details = itemView.findViewById(R.id.details);
        }
    }
}