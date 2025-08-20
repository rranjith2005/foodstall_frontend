package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.NeedsAttentionItem;
import java.util.List;

public class NeedsAttentionAdapter extends RecyclerView.Adapter<NeedsAttentionAdapter.NeedsAttentionViewHolder> {
    private final List<NeedsAttentionItem> itemsList;

    public NeedsAttentionAdapter(List<NeedsAttentionItem> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public NeedsAttentionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_needs_attention, parent, false);
        return new NeedsAttentionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NeedsAttentionViewHolder holder, int position) {
        NeedsAttentionItem item = itemsList.get(position);
        holder.dishName.setText(item.dishName);
        holder.details.setText(item.unitsSold + " units • " + item.revenue);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class NeedsAttentionViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, details;

        public NeedsAttentionViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishName);
            details = itemView.findViewById(R.id.details);
        }
    }
}