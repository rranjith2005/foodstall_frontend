package com.saveetha.foodstall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.Notification;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<Notification> notificationsList;

    public NotificationAdapter(List<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.notificationTypeTextView.setText(notification.type);
        holder.notificationTitleTextView.setText(notification.title);
        holder.notificationDescriptionTextView.setText(notification.description);
        holder.notificationLocationTextView.setText(notification.location);
        holder.notificationTimeTextView.setText(notification.time);
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTypeTextView;
        public TextView notificationTitleTextView;
        public TextView notificationDescriptionTextView;
        public TextView notificationLocationTextView;
        public TextView notificationTimeTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTypeTextView = itemView.findViewById(R.id.notificationTypeTextView);
            notificationTitleTextView = itemView.findViewById(R.id.notificationTitleTextView);
            notificationDescriptionTextView = itemView.findViewById(R.id.notificationDescriptionTextView);
            notificationLocationTextView = itemView.findViewById(R.id.notificationLocationTextView);
            notificationTimeTextView = itemView.findViewById(R.id.notificationTimeTextView);
        }
    }
}