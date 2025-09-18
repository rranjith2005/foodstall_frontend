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
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactions;
    private final Context context;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    // Add a method to update the list with new data from the server
    public void updateList(List<Transaction> newTransactions) {
        this.transactions.clear();
        this.transactions.addAll(newTransactions);
        notifyDataSetChanged();
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
        holder.title.setText(transaction.title);
        holder.timestamp.setText(transaction.timestamp);

        // Determine if it's a credit or debit transaction
        if (transaction.title.toLowerCase().contains("added")) {
            holder.amount.setText(String.format(Locale.getDefault(), "+ ₹%.2f", transaction.amount));
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.icon.setImageResource(R.drawable.ic_add_money);
        } else { // It's a purchase
            holder.amount.setText(String.format(Locale.getDefault(), "- ₹%.2f", transaction.amount));
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.icon.setImageResource(R.drawable.ic_new_stall); // A generic icon for purchases
        }

        holder.status.setText(transaction.description);
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