package com.saveetha.foodstall.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.saveetha.foodstall.ApiClient;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.TextDrawableUtil;
import com.saveetha.foodstall.model.OMenuItem;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class OOwnerMenuAdapter extends RecyclerView.Adapter<OOwnerMenuAdapter.MenuViewHolder> {
    private List<OMenuItem> menuItems;
    private final OnMenuItemClickListener listener;

    public interface OnMenuItemClickListener { void onEditClick(OMenuItem item); void onDeleteClick(OMenuItem item); }
    public OOwnerMenuAdapter(List<OMenuItem> menuItems, OnMenuItemClickListener listener) { this.menuItems = menuItems; this.listener = listener; }

    @NonNull @Override public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_owner_menu_item, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) { holder.bind(menuItems.get(position), listener); }
    @Override public int getItemCount() { return menuItems.size(); }
    public void updateList(List<OMenuItem> newList) { this.menuItems = newList; notifyDataSetChanged(); }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemCategory;
        ImageButton editButton, deleteButton;
        ImageView specialIcon;
        CircleImageView itemImageView;
        MaterialCardView cardView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemPrice = itemView.findViewById(R.id.itemPriceTextView);
            itemCategory = itemView.findViewById(R.id.itemCategoryTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            specialIcon = itemView.findViewById(R.id.specialIcon);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            cardView = (MaterialCardView) itemView;
        }

        public void bind(final OMenuItem item, final OnMenuItemClickListener listener) {
            itemName.setText(item.getName());
            itemPrice.setText(String.format(Locale.getDefault(),"₹%.2f",item.getPrice()));
            itemCategory.setText(item.getCategory());

            if ("Today's Special".equalsIgnoreCase(item.getCategory())) {
                specialIcon.setVisibility(View.VISIBLE);
                cardView.setStrokeWidth(4);
            } else {
                specialIcon.setVisibility(View.GONE);
                cardView.setStrokeWidth(0);
            }

            String imageUrl = item.getItemImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // If there IS an image, load it with Glide
                String fullUrl = ApiClient.BASE_URL + "uploads/" + imageUrl;
                Glide.with(itemView.getContext())
                        .load(fullUrl)
                        .placeholder(R.drawable.ic_camera_background)
                        .error(R.drawable.ic_camera_background)
                        .into(itemImageView);
            } else {
                // If there is NO image, use your TextDrawableUtil to create the initials drawable
                Drawable initialsDrawable = TextDrawableUtil.getInitialDrawable(item.getName());
                itemImageView.setImageDrawable(initialsDrawable);
            }

            editButton.setOnClickListener(v->listener.onEditClick(item));
            deleteButton.setOnClickListener(v->listener.onDeleteClick(item));
        }
    }
}