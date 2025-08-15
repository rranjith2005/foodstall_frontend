package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.ReportItem;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private final List<ReportItem> reportItems;

    public ReportAdapter(List<ReportItem> reportItems) {
        this.reportItems = reportItems;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_report_item, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportItem item = reportItems.get(position);
        holder.rankTextView.setText(item.rank);
        holder.stallNameTextView.setText(item.stallName);
        holder.stallIdTextView.setText(item.stallId);
        holder.totalRevenueTextView.setText(item.totalRevenue);
        holder.bestSellingItemTextView.setText(item.bestSellingItem);
    }

    @Override
    public int getItemCount() {
        return reportItems.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView rankTextView;
        TextView stallNameTextView;
        TextView stallIdTextView;
        TextView totalRevenueTextView;
        TextView bestSellingItemTextView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            stallNameTextView = itemView.findViewById(R.id.stallNameTextView);
            stallIdTextView = itemView.findViewById(R.id.stallIdTextView);
            totalRevenueTextView = itemView.findViewById(R.id.totalRevenueTextView);
            bestSellingItemTextView = itemView.findViewById(R.id.bestSellingItemTextView);
        }
    }
}