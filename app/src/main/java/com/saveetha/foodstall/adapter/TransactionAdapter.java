package com.saveetha.foodstall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.saveetha.foodstall.R;
import com.saveetha.foodstall.model.Transaction;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactions;
    private final Context context;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.icon.setImageResource(transaction.iconResId);
        holder.title.setText(transaction.title);
        holder.timestamp.setText(transaction.timestamp);
        holder.amount.setText(transaction.amount);

        if (transaction.amount.startsWith("-")) {
            holder.amount.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        } else {
            holder.amount.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        }

        if (transaction.isCompleted) {
            holder.status.setText("Completed");
            holder.status.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else {
            holder.status.setText("Pending");
            holder.status.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, timestamp, amount, status;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.transactionIcon);
            title = itemView.findViewById(R.id.transactionTitle);
            timestamp = itemView.findViewById(R.id.transactionTimestamp);
            amount = itemView.findViewById(R.id.transactionAmount);
            status = itemView.findViewById(R.id.transactionStatus);
        }
    }
}