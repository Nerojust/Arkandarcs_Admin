package com.nerojust.arkandarcsadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.transactions.TransactionResponse;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.views.transactions.TransactionDetailsActivity;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private TransactionResponse transactionResponse;
    private Context context;

    public TransactionAdapter(TransactionResponse transactionResponse, Context context) {
        this.transactionResponse = transactionResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.transaction_items, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        //set animation for recycler view
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        String txnId = transactionResponse.getResults().get(position).getTransactionRefId();
        String orderId = transactionResponse.getResults().get(position).getOrderId();
        String amount = transactionResponse.getResults().get(position).getAmount();
        String createdAt = transactionResponse.getResults().get(position).getCreatedAt();
        String quantity = transactionResponse.getResults().get(position).getQuantity();
        String date = AppUtils.formateDate(createdAt);

        holder.transactionIdTextview.setText(txnId);
        holder.orderIdTextview.setText(orderId);
        holder.amountTextview.setText(amount);
        holder.quantityTextview.setText(quantity);
        holder.createdAtTextview.setText(date);

        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, TransactionDetailsActivity.class);
            intent.putExtra("transactionId", transactionResponse.getResults().get(position).getId());
            intent.putExtra("orderId", orderId);
            intent.putExtra("amount", amount);
            intent.putExtra("quantity", quantity);
            intent.putExtra("date", date);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return transactionResponse.getRecordCount();
    }


    class TransactionViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout container;

        private TextView transactionIdTextview, orderIdTextview, amountTextview, createdAtTextview, quantityTextview;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layoutContainer);
            transactionIdTextview = itemView.findViewById(R.id.transactionId);
            orderIdTextview = itemView.findViewById(R.id.orderId);
            amountTextview = itemView.findViewById(R.id.orderAmount);
            createdAtTextview = itemView.findViewById(R.id.transactionDate);
            quantityTextview = itemView.findViewById(R.id.quantity);
        }
    }
}
