package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.Order;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private final List<Order> orders;

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.stallName.setText(order.stallName);
        holder.orderId.setText(order.orderId);
        holder.orderTime.setText(order.orderTime);
        holder.orderedItems.setText(order.orderedItems);
        holder.totalAmount.setText(order.totalAmount);

        // Handle order status styling
        holder.orderStatus.setText(order.orderStatus);
        int backgroundResId;
        switch (order.orderStatus) {
            case "Delivered":
                backgroundResId = R.drawable.status_delivered_background;
                break;
            case "Ready":
                backgroundResId = R.drawable.status_ready_background;
                break;
            case "Cancelled":
                backgroundResId = R.drawable.status_cancelled_background;
                break;
            default:
                backgroundResId = R.drawable.status_pending_background;
                break;
        }
        holder.orderStatus.setBackgroundResource(backgroundResId);

        // Handle pickup time visibility
        if (order.pickupTime != null && !order.pickupTime.isEmpty()) {
            holder.pickupTime.setText(order.pickupTime);
            holder.pickupTime.setVisibility(View.VISIBLE);
        } else {
            holder.pickupTime.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView stallName, orderId, orderTime, orderedItems, orderStatus, totalAmount, pickupTime;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            stallName = itemView.findViewById(R.id.stallNameTextView);
            orderId = itemView.findViewById(R.id.orderIdTextView);
            orderTime = itemView.findViewById(R.id.orderTimeTextView);
            orderedItems = itemView.findViewById(R.id.orderedItemsTextView);
            orderStatus = itemView.findViewById(R.id.orderStatusTextView);
            totalAmount = itemView.findViewById(R.id.totalAmountTextView);
            pickupTime = itemView.findViewById(R.id.pickupTimeTextView);
        }
    }
}