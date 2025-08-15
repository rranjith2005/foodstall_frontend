package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.StallAdmin;
import java.util.List;

public class AdminStallsAdapter extends RecyclerView.Adapter<AdminStallsAdapter.StallViewHolder> {
    private final List<StallAdmin> stallsList;

    public AdminStallsAdapter(List<StallAdmin> stallsList) {
        this.stallsList = stallsList;
    }

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_admin_stall, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        StallAdmin stall = stallsList.get(position);
        holder.stallImage.setImageResource(stall.stallImageResId);
        holder.stallName.setText(stall.stallName);
        holder.ownerName.setText(stall.ownerName);
        holder.status.setText(stall.status);

        // Dynamic status background
        switch (stall.status) {
            case "Approved":
                holder.status.setBackgroundResource(R.drawable.status_approved_background);
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                break;
            case "Pending":
                holder.status.setBackgroundResource(R.drawable.status_apending_background);
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                break;
            case "Rejected":
                holder.status.setBackgroundResource(R.drawable.status_rejected_background);
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return stallsList.size();
    }

    public static class StallViewHolder extends RecyclerView.ViewHolder {
        ImageView stallImage;
        TextView stallName, ownerName, status;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImage = itemView.findViewById(R.id.stallImage);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            ownerName = itemView.findViewById(R.id.ownerNameTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }
    }
}