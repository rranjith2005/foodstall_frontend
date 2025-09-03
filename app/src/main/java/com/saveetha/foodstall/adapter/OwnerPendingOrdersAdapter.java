package com.saveetha.foodstall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.DetailedOrder;
import com.saveetha.foodstall.model.ReceiptItem;

import java.util.List;
import java.util.Locale;

public class OwnerPendingOrdersAdapter extends RecyclerView.Adapter<OwnerPendingOrdersAdapter.OrderViewHolder> {

    private final Context context;
    private List<DetailedOrder> orderList;
    private final OnOrderActionListener actionListener;
    private final double PARCEL_FEE_PER_ITEM_QUANTITY = 10.00;

    // Interface to handle button clicks in the activity
    public interface OnOrderActionListener {
        void onApproveClicked(int position);
        void onRejectClicked(int position);
    }

    public OwnerPendingOrdersAdapter(Context context, List<DetailedOrder> orderList, OnOrderActionListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.actionListener = listener;
    }

    // NEW METHOD TO FIX THE ERROR
    /**
     * Safely gets the order item at a specific position.
     * @param position The position of the item in the list.
     * @return The DetailedOrder object.
     */
    public DetailedOrder getItem(int position) {
        return orderList.get(position);
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_owner_pending_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        DetailedOrder order = orderList.get(position);

        // Set header details
        holder.orderIdTextView.setText(order.getOrderId());
        holder.studentIdTextView.setText(order.getStudentId());
        holder.orderTimeTextView.setText(order.getOrderTime());

        // Clear previous items and calculate totals
        holder.itemsContainer.removeAllViews();
        double subtotal = 0;
        double totalParcelFee = 0;

        // Dynamically add each item to the card
        LayoutInflater inflater = LayoutInflater.from(context);
        for (ReceiptItem item : order.getItems()) {
            double itemLineTotal = item.getPrice() * item.getQuantity();
            subtotal += itemLineTotal;

            if (item.isParcel()) {
                totalParcelFee += item.getQuantity() * PARCEL_FEE_PER_ITEM_QUANTITY;
            }

            // Inflate and add the item row view
            View itemRow = inflater.inflate(R.layout.item_receipt_row, holder.itemsContainer, false);
            TextView itemNameTextView = itemRow.findViewById(R.id.itemNameTextView);
            TextView itemPriceTextView = itemRow.findViewById(R.id.itemPriceTextView);
            itemNameTextView.setText(String.format(Locale.getDefault(), "%s x %d", item.getName(), item.getQuantity()));
            itemPriceTextView.setText(String.format(Locale.getDefault(), "₹%.2f", itemLineTotal));
            holder.itemsContainer.addView(itemRow);
        }

        // Set calculated price details
        holder.subtotalTextView.setText(String.format(Locale.getDefault(), "₹%.2f", subtotal));
        double grandTotal = subtotal + totalParcelFee;
        holder.grandTotalTextView.setText(String.format(Locale.getDefault(), "₹%.2f", grandTotal));

        // Handle visibility of parcel fee section
        if (totalParcelFee > 0) {
            holder.parcelFeeLayout.setVisibility(View.VISIBLE);
            holder.parcelFeeTextView.setText(String.format(Locale.getDefault(), "₹%.2f", totalParcelFee));
        } else {
            holder.parcelFeeLayout.setVisibility(View.GONE);
        }

        // Handle visibility of pre-parcel end time
        if ("Pre-parcel".equalsIgnoreCase(order.getParcelType())) {
            holder.parcelEndTimeTextView.setVisibility(View.VISIBLE);
            holder.parcelEndTimeTextView.setText(String.format("Pickup Before: %s", order.getParcelEndTime()));
        } else {
            holder.parcelEndTimeTextView.setVisibility(View.GONE);
        }

        // Set button click listeners
        holder.approveButton.setOnClickListener(v -> actionListener.onApproveClicked(holder.getAdapterPosition()));
        holder.rejectButton.setOnClickListener(v -> actionListener.onRejectClicked(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Method for search/filter functionality
    public void filterList(List<DetailedOrder> filteredList) {
        orderList = filteredList;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, studentIdTextView, orderTimeTextView;
        LinearLayout itemsContainer;
        TextView subtotalTextView, parcelFeeTextView, grandTotalTextView, parcelEndTimeTextView;
        RelativeLayout parcelFeeLayout;
        Button approveButton, rejectButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            studentIdTextView = itemView.findViewById(R.id.studentIdTextView);
            orderTimeTextView = itemView.findViewById(R.id.orderTimeTextView);
            itemsContainer = itemView.findViewById(R.id.itemsContainer);
            subtotalTextView = itemView.findViewById(R.id.subtotalTextView);
            parcelFeeTextView = itemView.findViewById(R.id.parcelFeeTextView);
            grandTotalTextView = itemView.findViewById(R.id.grandTotalTextView);
            parcelEndTimeTextView = itemView.findViewById(R.id.parcelEndTimeTextView);
            parcelFeeLayout = itemView.findViewById(R.id.parcelFeeLayout);
            approveButton = itemView.findViewById(R.id.approveButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }
    }
}