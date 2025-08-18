package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.RevenueDetail;
import java.util.List;

public class RevenueDetailAdapter extends RecyclerView.Adapter<RevenueDetailAdapter.RevenueViewHolder> {
    private final List<RevenueDetail> revenueDetailsList;

    public RevenueDetailAdapter(List<RevenueDetail> revenueDetailsList) {
        this.revenueDetailsList = revenueDetailsList;
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_revenue_detail_item, parent, false);
        return new RevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        RevenueDetail detail = revenueDetailsList.get(position);
        holder.date.setText(detail.date);
        holder.orders.setText(detail.orders);
        holder.revenue.setText(detail.revenue);
    }

    @Override
    public int getItemCount() {
        return revenueDetailsList.size();
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder {
        public TextView date, orders, revenue;

        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
            orders = itemView.findViewById(R.id.ordersTextView);
            revenue = itemView.findViewById(R.id.revenueTextView);
        }
    }
}