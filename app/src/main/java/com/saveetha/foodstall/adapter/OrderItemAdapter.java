package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.OrderItem;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    private final List<OrderItem> orderItems;

    public OrderItemAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_confirm_order_item, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);

        holder.itemName.setText(item.name);
        holder.quantity.setText("Quantity: " + item.quantity);
        holder.price.setText("₹" + item.price);
        holder.preParcelSwitch.setChecked(item.isPreParcel);

        // Corrected line to use the new preParcelTime field
        if (item.isPreParcel) {
            holder.timePickerLayout.setVisibility(View.VISIBLE);
            holder.timeTextView.setText(item.preParcelTime);
        } else {
            holder.timePickerLayout.setVisibility(View.GONE);
        }

        holder.preParcelSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.timePickerLayout.setVisibility(View.VISIBLE);
                holder.timeTextView.setText(item.preParcelTime);
            } else {
                holder.timePickerLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, quantity, price, timeTextView;
        Switch preParcelSwitch;
        LinearLayout timePickerLayout;
        ImageView deleteButton;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameTextView);
            quantity = itemView.findViewById(R.id.quantityTextView);
            price = itemView.findViewById(R.id.priceTextView);
            timeTextView = itemView.findViewById(R.id.timePickerTextView);
            preParcelSwitch = itemView.findViewById(R.id.pre_parcel_switch);
            timePickerLayout = itemView.findViewById(R.id.timePickerLayout);
            deleteButton = itemView.findViewById(R.id.deleteItemButton);
        }
    }
}