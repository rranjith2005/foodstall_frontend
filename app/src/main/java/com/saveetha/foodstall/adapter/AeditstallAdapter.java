package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.StallEdit;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AeditstallAdapter extends RecyclerView.Adapter<AeditstallAdapter.StallViewHolder> {
    private final List<StallEdit> stallsList;

    public AeditstallAdapter(List<StallEdit> stallsList) {
        this.stallsList = stallsList;
    }

    @NonNull
    @Override
    public StallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_edit_stall, parent, false);
        return new StallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallViewHolder holder, int position) {
        StallEdit stall = stallsList.get(position);
        holder.stallName.setText(stall.stallName);
        holder.ownerId.setText(stall.ownerId);
        holder.ownerName.setText(stall.ownerName);
    }

    @Override
    public int getItemCount() {
        return stallsList.size();
    }

    public static class StallViewHolder extends RecyclerView.ViewHolder {
        ImageView stallImage;
        TextView stallName, ownerId, ownerName;
        Button editButton;

        public StallViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImage = itemView.findViewById(R.id.stallImage);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            ownerId = itemView.findViewById(R.id.ownerIdTextView);
            ownerName = itemView.findViewById(R.id.ownerNameTextView);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}