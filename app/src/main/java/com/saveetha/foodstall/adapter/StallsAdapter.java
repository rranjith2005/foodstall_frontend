package com.saveetha.foodstall.adapter;

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

    private List<Stall> stalls;

    public StallsAdapter(List<Stall> stalls) {
        this.stalls = stalls;
    }

    // This is the new method you need to add to resolve the error
    public List<Stall> getStalls() {
        return stalls;
    }

    public void updateList(List<Stall> newStalls) {
        this.stalls = newStalls;
        notifyDataSetChanged();
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
        holder.stallName.setText(stall.stallName);
        holder.stallRating.setText("★ " + stall.rating);
        holder.stallTimings.setText(stall.timings);
        holder.stallImage.setImageResource(stall.imageResId);
        if (stall.isOpen) {
            holder.stallStatus.setText("● Open");
            holder.stallStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.stallStatus.setText("● Closed");
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
