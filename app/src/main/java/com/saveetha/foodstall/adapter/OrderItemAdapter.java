package com.saveetha.foodstall.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.OrderItem;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private final List<OrderItem> items;
    private final Context context;
    private final OnDataChangedListener listener;

    public OrderItemAdapter(List<OrderItem> items, Context context, OnDataChangedListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_confirm_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemPrice, timePickerText;
        // ADD a variable for the new Switch
        Switch preParcelSwitch, parcelSwitch;
        ImageView deleteButton;
        LinearLayout timePickerLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemQuantity = itemView.findViewById(R.id.quantityTextView);
            itemPrice = itemView.findViewById(R.id.priceTextView);
            timePickerLayout = itemView.findViewById(R.id.timePickerLayout);
            timePickerText = itemView.findViewById(R.id.timePickerTextView);
            preParcelSwitch = itemView.findViewById(R.id.pre_parcel_switch);
            // FIND the new Switch by its ID from the XML
            parcelSwitch = itemView.findViewById(R.id.parcelSwitch);
            deleteButton = itemView.findViewById(R.id.deleteItemButton);
        }

        void bind(OrderItem item) {
            itemName.setText(item.getName());
            itemQuantity.setText(String.format(Locale.getDefault(), "Quantity: %d", item.getQuantity()));
            itemPrice.setText(String.format(Locale.getDefault(), "₹%.2f", item.getPrice() * item.getQuantity()));

            // --- Pre-parcel Switch Logic (Updated) ---
            preParcelSwitch.setOnCheckedChangeListener(null);
            preParcelSwitch.setChecked(item.isPreParcel());
            if (item.isPreParcel() && item.getPreParcelTime() != null) {
                timePickerLayout.setVisibility(View.VISIBLE);
                timePickerText.setText(item.getPreParcelTime());
            } else {
                timePickerLayout.setVisibility(View.GONE);
            }
            preParcelSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setPreParcel(isChecked);
                if (isChecked) {
                    showTimePickerDialog(item);
                } else {
                    item.setPreParcelTime(null);
                    notifyItemChanged(getAdapterPosition());
                    listener.onDataChanged(); // Notify activity of change
                }
            });

            // --- NEW: Parcel Switch Logic ---
            parcelSwitch.setOnCheckedChangeListener(null);
            parcelSwitch.setChecked(item.isParcel());
            parcelSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setParcel(isChecked);
                listener.onDataChanged(); // Notify activity to recalculate the bill
            });

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    items.remove(position);
                    notifyItemRemoved(position);
                    listener.onDataChanged();
                }
            });
        }

        private void showTimePickerDialog(OrderItem item) {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                String amPm = hourOfDay >= 12 ? "PM" : "AM";
                int hourIn12 = hourOfDay % 12 == 0 ? 12 : hourOfDay % 12;
                String time = String.format(Locale.getDefault(), "%02d:%02d %s", hourIn12, minute, amPm);
                item.setPreParcelTime(time);
                notifyItemChanged(getAdapterPosition());
                listener.onDataChanged(); // Notify activity of change
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

            dialog.setOnCancelListener(dialogInterface -> {
                item.setPreParcel(false);
                item.setPreParcelTime(null);
                notifyItemChanged(getAdapterPosition());
                listener.onDataChanged(); // Notify activity of change
            });
            dialog.show();
        }
    }
}