package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.NotificationAdmin;
import java.util.List;

public class NotificationAdminAdapter extends RecyclerView.Adapter<NotificationAdminAdapter.NotificationViewHolder> {

    private final List<NotificationAdmin> notificationsList;

    public NotificationAdminAdapter(List<NotificationAdmin> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_admin, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationAdmin notification = notificationsList.get(position);
        holder.notificationTitle.setText(notification.title);
        holder.notificationMessage.setText(notification.message);
        holder.notificationTime.setText(notification.time);

        if (notification.hasNewStatus) {
            holder.statusIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.statusIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTitle, notificationMessage, notificationTime;
        public ImageView statusIndicator;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationMessage = itemView.findViewById(R.id.notificationMessage);
            notificationTime = itemView.findViewById(R.id.notificationTime);

        }
    }
}