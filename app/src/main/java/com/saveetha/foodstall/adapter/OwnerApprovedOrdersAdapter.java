package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.OwnerOrder;
import java.util.List;

public class OwnerApprovedOrdersAdapter extends RecyclerView.Adapter<OwnerApprovedOrdersAdapter.OrderViewHolder> {
    private final List<OwnerOrder> ordersList;

    public OwnerApprovedOrdersAdapter(List<OwnerOrder> ordersList) {
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_owner_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OwnerOrder order = ordersList.get(position);
        holder.orderId.setText(order.orderId);
        holder.stallName.setText(order.stallName);
        holder.userId.setText(order.userId);
        holder.itemsCount.setText(order.itemsCount);
        holder.totalAmount.setText(order.totalAmount);
        holder.pickupTime.setText(order.pickupTime);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, stallName, userId, itemsCount, totalAmount, pickupTime;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderIdTextView);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            itemsCount = itemView.findViewById(R.id.itemsCountTextView);
            totalAmount = itemView.findViewById(R.id.totalAmountTextView);
            pickupTime = itemView.findViewById(R.id.pickupTimeTextView);
        }
    }
}