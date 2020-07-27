package com.nerojust.arkandarcsadmin.customer.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;
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
import com.nerojust.arkandarcsadmin.admin.views.products.AdminProductDetailsActivity;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;

public class CustomerProductsAdapter extends RecyclerView.Adapter<CustomerProductsAdapter.ProductViewHolder> {

    private ProductsResponse productsResponseList;
    private Context context;

    public CustomerProductsAdapter(ProductsResponse productsResponseList, Context context) {
        this.productsResponseList = productsResponseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.display_products, parent, false);
        return new CustomerProductsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        //set animation for recycler view
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        String name = productsResponseList.getResults().get(position).getProductName();
        String color = productsResponseList.getResults().get(position).getProductColor();
        String category = productsResponseList.getResults().get(position).getProductCategory();
        String amount = productsResponseList.getResults().get(position).getProductAmount();
        String discountedAmount = productsResponseList.getResults().get(position).getProductDiscountedAmount();
        String description = productsResponseList.getResults().get(position).getProductDescription();
        String stock = productsResponseList.getResults().get(position).getNumberInStock();
        Boolean isLive = productsResponseList.getResults().get(position).getProductActive();

        holder.productName.setText(name);
        holder.productAmount.setText(amount);


        if (productsResponseList.getResults().get(position).getProductImages().size() > 0) {
            String encodedImageString = productsResponseList.getResults().get(position).getProductImages().get(0).getImageString();
            if (encodedImageString != null || encodedImageString != "") {
                Bitmap bitmap = decodeStringToImage(encodedImageString);
                if (bitmap != null) {
                    Glide.with(context)
                            .load(bitmap)
                            .placeholder(R.drawable.load)
                            .into(holder.productImage);
                }
            }
        }
        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminProductDetailsActivity.class);
            intent.putExtra("productId", productsResponseList.getResults().get(position).getId());
            intent.putExtra("productName", name);
            intent.putExtra("productCategory", category);
            intent.putExtra("productAmount", amount);
            intent.putExtra("productDiscountedAmount", discountedAmount);
            intent.putExtra("numberInStock", stock);
            intent.putExtra("productDescription", description);
            if (color == null) {
                intent.putExtra("productColor", "None");
            } else {
                intent.putExtra("productColor", productsResponseList.getResults().get(position).getProductColor());
            }
            intent.putExtra("isLive", isLive);
            if (productsResponseList.getResults().get(position).getProductImages().size() > 0) {
                intent.putExtra("imageString", productsResponseList.getResults().get(position).getProductImages().get(0).getImageString());
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    private Bitmap decodeStringToImage(String encodedImage) {
        Bitmap bmp = null;
        if (encodedImage != null) {
            byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);// actual conversion to Base64 String Image
            bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        }
        return bmp;
    }


    @Override
    public int getItemCount() {
        return productsResponseList.getRecordCount();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        final private LinearLayout container;
        private ImageView productImage;
        private TextView isLiveTextview;
        private TextView productName, productCategory, productAmount, productDiscountedAmount, productColor, numberInstock;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layoutContainer);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productAmount = itemView.findViewById(R.id.productAmount);
            productAmount.setPaintFlags((productAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
