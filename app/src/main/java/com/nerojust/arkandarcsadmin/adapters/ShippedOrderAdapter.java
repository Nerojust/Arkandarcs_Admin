package com.nerojust.arkandarcsadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.orders.OrdersResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShippedOrderAdapter extends RecyclerView.Adapter<ShippedOrderAdapter.OrdersViewHolder> {

    private OrdersResponse ordersResponse;
    private Context context;

    public ShippedOrderAdapter(OrdersResponse ordersResponse, Context context) {
        this.ordersResponse = ordersResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_items_shipped, parent, false);

        return new OrdersViewHolder(view);
    }

    private String formateDate(String dateString) {
        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat("MMMM dd, hh:mm a", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        //set animation for recycler view
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        String orderId = ordersResponse.getResults().get(position).getOrderId();
        String date = ordersResponse.getResults().get(position).getCreatedAt();

        String quantity = ordersResponse.getResults().get(position).getProduct().getProductQuantity();
        String amount = ordersResponse.getResults().get(position).getProduct().getProductAmount();
        String status = ordersResponse.getResults().get(position).getOrderStatus();


        holder.orderNumber.setText(orderId);
        holder.orderDate.setText(formateDate(date));
        holder.orderQuantity.setText(quantity);
        holder.orderAmount.setText(amount);
        holder.orderStatus.setText(status);
    }
        /*holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("userId", usersResponse.getResults().get(position).getId());
            intent.putExtra("userName", fullName);
            intent.putExtra("gender", gender);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("username", username);
            intent.putExtra("emailAddress", email);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });*/


    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < ordersResponse.getResults().size(); i++) {
            if (ordersResponse.getResults().get(i).getOrderStatus().equals("Shipped")) {
                count++;
            }
        }
        //   return count;
        return ordersResponse.getRecordCount();
    }


    class OrdersViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout container;

        private TextView orderNumber, orderDate, orderAmount, orderQuantity, orderStatus;

        OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layoutContainer);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
            orderAmount = itemView.findViewById(R.id.orderAmount);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
}
