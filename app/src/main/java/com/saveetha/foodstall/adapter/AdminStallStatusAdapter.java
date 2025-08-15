package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.StallStatus;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminStallStatusAdapter extends RecyclerView.Adapter<AdminStallStatusAdapter.StallViewHolder> {
    private final List<StallStatus> stallsList;

    public AdminStallStatusAdapter(List<StallStatus> stallsList) {
        this.stallsList = stallsList;
    }

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_admin_stall_status, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        StallStatus stall = stallsList.get(position);
        holder.stallImage.setImageResource(stall.stallImageResId);
        holder.stallName.setText(stall.stallName);
        holder.ownerName.setText(stall.ownerName);
        holder.status.setText(stall.status);

        // Logic to show/hide the reason TextView based on status
        if ("Rejected".equals(stall.status)) {
            holder.reason.setText(stall.reason);
            holder.reason.setVisibility(View.VISIBLE);
        } else {
            holder.reason.setVisibility(View.GONE);
        }

        // Logic to show/hide the View button based on status
        if ("Pending".equals(stall.status)) {
            holder.viewButton.setVisibility(View.VISIBLE);
        } else {
            holder.viewButton.setVisibility(View.GONE);
        }

        // Dynamic status background and text color
        switch (stall.status) {
            case "Approved":
                holder.status.setBackgroundResource(R.drawable.status_approved_background);
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                break;
            case "Pending":
                holder.status.setBackgroundResource(R.drawable.status_pending_background);
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
        CircleImageView stallImage;
        TextView stallName, ownerName, status, reason;
        Button viewButton;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImage = itemView.findViewById(R.id.stallImage);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            ownerName = itemView.findViewById(R.id.ownerNameTextView);
            status = itemView.findViewById(R.id.statusTextView);
            reason = itemView.findViewById(R.id.reasonTextView);
            viewButton = itemView.findViewById(R.id.viewButton);
        }
    }
}
