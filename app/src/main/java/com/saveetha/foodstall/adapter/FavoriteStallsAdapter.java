package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.FavoriteStall;

import java.util.List;

public class FavoriteStallsAdapter extends RecyclerView.Adapter<FavoriteStallsAdapter.ViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    private List<FavoriteStall> favoriteStalls;
    private OnDeleteClickListener onDeleteClickListener;

    public FavoriteStallsAdapter(List<FavoriteStall> favoriteStalls, OnDeleteClickListener listener) {
        this.favoriteStalls = favoriteStalls;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_stall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(favoriteStalls.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteStalls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView stallImageView, deleteButton;
        TextView stallNameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            stallImageView = itemView.findViewById(R.id.stallImageView);
            stallNameTextView = itemView.findViewById(R.id.stallNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(v -> {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });
        }

        void bind(FavoriteStall stall) {
            stallImageView.setImageResource(stall.getImageResId());
            stallNameTextView.setText(stall.getName());
        }
    }
}