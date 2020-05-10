package com.nerojust.arkandarcsadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.orders.OrdersResponse;
import com.nerojust.arkandarcsadmin.views.orders.OrderDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.nerojust.arkandarcsadmin.utils.AppUtils.decodeStringToImage;

public class ReadyToShipOrderAdapter extends RecyclerView.Adapter<ReadyToShipOrderAdapter.OrdersViewHolder> {

    private OrdersResponse ordersResponse;
    private Context context;

    public ReadyToShipOrderAdapter(OrdersResponse ordersResponse, Context context) {
        this.ordersResponse = ordersResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_items, parent, false);

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

        // if (ordersResponse.getResults().get(position).getOrderStatus().equals("Ready to ship")) {
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
        holder.orderStatus.setTextColor(context.getResources().getColor(R.color.arkandarcs_blue));

        String imageString = ordersResponse.getResults().get(position).getProduct().getProductImage();

        if (imageString != null) {
            if (imageString != null || imageString != "") {
                Bitmap bitmap = decodeStringToImage(imageString);
                if (bitmap != null) {
                    Glide.with(context)
                            .load(bitmap)
                            .placeholder(R.drawable.load)
                            .into(holder.imageView);
                }
            }
        }

        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailsActivity.class);
            intent.putExtra("itemId", ordersResponse.getResults().get(position).getId());

            intent.putExtra("quantity", quantity);
            intent.putExtra("amount", amount);

            intent.putExtra("orderNumber", orderId);
            intent.putExtra("productId", ordersResponse.getResults().get(position).getProduct().getProductId());
            intent.putExtra("productName", ordersResponse.getResults().get(position).getProduct().getProductName());
            intent.putExtra("productCategory", ordersResponse.getResults().get(position).getProduct().getProductCategory());
            intent.putExtra("productColor", ordersResponse.getResults().get(position).getProduct().getProductColor());
            intent.putExtra("productQuantity", ordersResponse.getResults().get(position).getProduct().getProductQuantity());
            intent.putExtra("imageString", ordersResponse.getResults().get(position).getProduct().getProductImage());
            intent.putExtra("status", status);
            intent.putExtra("date", formateDate(date));

            intent.putExtra("paymentMethod", ordersResponse.getResults().get(position).getPayment().getPaymentMethod());
            intent.putExtra("tax", ordersResponse.getResults().get(position).getPayment().getTaxAmount());

            intent.putExtra("name", ordersResponse.getResults().get(position).getCustomerName());
            intent.putExtra("phoneNumber", ordersResponse.getResults().get(position).getCustomerPhoneNumber());
            intent.putExtra("emailAddress", ordersResponse.getResults().get(position).getCustomerEmailAddress());
            intent.putExtra("address", ordersResponse.getResults().get(position).getCustomerAddress());
            intent.putExtra("country", ordersResponse.getResults().get(position).getCountry());
            intent.putExtra("state", ordersResponse.getResults().get(position).getState());
            intent.putExtra("lga", ordersResponse.getResults().get(position).getLga());


            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < ordersResponse.getResults().size(); i++) {
            if (ordersResponse.getResults().get(i).getOrderStatus().equals("Ready to ship")) {
                count++;
            }
        }
        //return count;
        return ordersResponse.getRecordCount();
    }


    class OrdersViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout container;

        private TextView orderNumber, orderDate, orderAmount, orderQuantity, orderStatus;
        private ImageView imageView;

        OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layoutContainer);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
            orderAmount = itemView.findViewById(R.id.orderAmount);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
