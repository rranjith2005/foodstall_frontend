package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.OwnerOrderPending;
import java.util.List;

public class OwnerPendingOrdersAdapter extends RecyclerView.Adapter<OwnerPendingOrdersAdapter.OrderViewHolder> {
    private final List<OwnerOrderPending> ordersList;

    public OwnerPendingOrdersAdapter(List<OwnerOrderPending> ordersList) {
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_owner_pending_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OwnerOrderPending order = ordersList.get(position);
        holder.orderId.setText(order.orderId);
        holder.stallName.setText(order.stallName);
        holder.stallLocation.setText(order.stallLocation);
        holder.orderedItems.setText(order.orderedItems);
        holder.totalAmount.setText(order.totalAmount);
        holder.orderTimeAgo.setText(order.timeAgo);
        holder.orderedAtTime.setText("Ordered at " + order.orderedAtTime);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, stallName, stallLocation, orderedItems, totalAmount, orderTimeAgo, orderedAtTime;
        Button rejectButton, approveButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderIdTextView);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            stallLocation = itemView.findViewById(R.id.stallLocationTextView);
            orderedItems = itemView.findViewById(R.id.orderedItemsTextView);
            totalAmount = itemView.findViewById(R.id.totalAmountTextView);
            orderTimeAgo = itemView.findViewById(R.id.orderTimeAgoTextView);
            orderedAtTime = itemView.findViewById(R.id.orderedAtTimeTextView);
            rejectButton = itemView.findViewById(R.id.rejectButton);
            approveButton = itemView.findViewById(R.id.approveButton);
        }
    }
}
