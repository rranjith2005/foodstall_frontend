package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

        holder.itemNameQuantity.setText(item.name + " ×" + item.quantity);
        holder.itemPrice.setText("₹" + item.price);
        holder.preParcelSwitch.setChecked(item.isPreParcel);

        // Show/hide the time picker based on whether the item is pre-parceled
        if (item.isPreParcel) {
            holder.timePickerLayout.setVisibility(View.VISIBLE);
        } else {
            holder.timePickerLayout.setVisibility(View.GONE);
        }

        // Handle the switch state change
        holder.preParcelSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.timePickerLayout.setVisibility(View.VISIBLE);
                // You would typically open a time picker dialog here
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
        TextView itemNameQuantity, itemPrice;
        Switch preParcelSwitch;
        LinearLayout timePickerLayout;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameQuantity = itemView.findViewById(R.id.item_name_quantity);
            itemPrice = itemView.findViewById(R.id.item_price);
            preParcelSwitch = itemView.findViewById(R.id.pre_parcel_switch);
            timePickerLayout = itemView.findViewById(R.id.timePickerLayout);
        }
    }
}