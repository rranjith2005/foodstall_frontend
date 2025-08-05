package com.saveetha.foodstall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.foodstall.R;
import com.saveetha.foodstall.models.SpecialItem;

import java.util.List;

public class SpecialsAdapter extends RecyclerView.Adapter<SpecialsAdapter.ViewHolder> {

    private Context context;
    private List<SpecialItem> items;

    public SpecialsAdapter(Context context, List<SpecialItem> items) {
        this.context = context;
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, stall, price;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.special_image);
            name = v.findViewById(R.id.special_name);
            stall = v.findViewById(R.id.special_stall);
            price = v.findViewById(R.id.special_price);
        }
    }

    @NonNull
    @Override
    public SpecialsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_special, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialItem item = items.get(position);
        holder.image.setImageResource(item.getImageResId());
        holder.name.setText(item.getName());      // ✅
        holder.stall.setText(item.getStallName()); // ✅
        holder.price.setText(item.getPrice());    // ✅

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
