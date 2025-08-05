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
import com.saveetha.foodstall.models.Stall;

import java.util.List;

public class StallsAdapter extends RecyclerView.Adapter<StallsAdapter.StallViewHolder> {

    private Context context;
    private List<Stall> stallList;

    public StallsAdapter(Context context, List<Stall> stallList) {
        this.context = context;
        this.stallList = stallList;
    }

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_stall_item, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        Stall stall = stallList.get(position);
        holder.stallName.setText(stall.getName());
        holder.stallRating.setText("⭐ " + stall.getRating());
        holder.stallTimings.setText("⏰ " + stall.getTimings());
        holder.stallStatus.setText(stall.isOpen() ? "● Open" : "● Closed");
        holder.stallStatus.setTextColor(context.getResources().getColor(
                stall.isOpen() ? R.color.green : R.color.red
        ));

        // Local image
        holder.stallImage.setImageResource(stall.getImageResId());

        // If using image URL from server
        // Glide.with(context).load(stall.getImageUrl()).into(holder.stallImage);
    }

    @Override
    public int getItemCount() {
        return stallList != null ? stallList.size() : 0;
    }
    public void setStalls(List<Stall> newStalls) {
        this.stallList = newStalls;
        notifyDataSetChanged();
    }

    public static class StallViewHolder extends RecyclerView.ViewHolder {
        ImageView stallImage;
        TextView stallName, stallRating, stallCategory, stallTimings, stallStatus;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImage = itemView.findViewById(R.id.stall_image);
            stallName = itemView.findViewById(R.id.stall_name);
            stallRating = itemView.findViewById(R.id.stall_rating);
            stallCategory = itemView.findViewById(R.id.stall_category);
            stallTimings = itemView.findViewById(R.id.stall_timings);
            stallStatus = itemView.findViewById(R.id.stall_status);
        }
    }
}
