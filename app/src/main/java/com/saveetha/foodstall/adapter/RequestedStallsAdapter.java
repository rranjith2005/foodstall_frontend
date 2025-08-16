package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.RequestedStall;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class RequestedStallsAdapter extends RecyclerView.Adapter<RequestedStallsAdapter.StallViewHolder> {
    private final List<RequestedStall> stallsList;

    public RequestedStallsAdapter(List<RequestedStall> stallsList) {
        this.stallsList = stallsList;
    }

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_requested_stall, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        RequestedStall stall = stallsList.get(position);
        holder.stallImage.setImageResource(stall.stallImageResId);
        holder.stallName.setText(stall.stallName);
        holder.ownerName.setText(stall.ownerName);
        holder.requestedDate.setText("Requested: " + stall.requestedDate);
    }

    @Override
    public int getItemCount() {
        return stallsList.size();
    }

    public static class StallViewHolder extends RecyclerView.ViewHolder {
        CircleImageView stallImage;
        TextView stallName, ownerName, requestedDate;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImage = itemView.findViewById(R.id.stallImage);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            ownerName = itemView.findViewById(R.id.ownerNameTextView);
            requestedDate = itemView.findViewById(R.id.requestedDateTextView);
        }
    }
}