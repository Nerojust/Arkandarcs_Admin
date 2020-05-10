package com.nerojust.arkandarcsadmin.adapters;

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
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.views.products.ProductDetailsActivity;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ProductsResponse productsResponseList;
    private Context context;

    public ProductAdapter(ProductsResponse productsResponseList, Context context) {
        this.productsResponseList = productsResponseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_items, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
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
        holder.productColor.setText(color);
        holder.productCategory.setText(category);
        holder.productAmount.setText(amount);
        holder.productDiscountedAmount.setText(discountedAmount);

        if (stock == null) {
            holder.numberInstock.setText("None in stock");
        } else {
            holder.numberInstock.setText(stock + " in stock");
        }

        if (isLive) {
            holder.isLiveTextview.setText("Live");
            holder.isLiveTextview.setTextColor(context.getResources().getColor(R.color.arkandarcs_green));
        } else {
            holder.isLiveTextview.setText("Inactive");
            holder.isLiveTextview.setTextColor(context.getResources().getColor(R.color.arkandarcs_gray_color));
        }

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
            Intent intent = new Intent(context, ProductDetailsActivity.class);
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
            productCategory = itemView.findViewById(R.id.category);
            productColor = itemView.findViewById(R.id.color);
            productAmount = itemView.findViewById(R.id.productAmount);
            productAmount.setPaintFlags((productAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));

            isLiveTextview = itemView.findViewById(R.id.isLive);
            numberInstock = itemView.findViewById(R.id.numberInStock);
            productDiscountedAmount = itemView.findViewById(R.id.productDiscountedAmount);

        }
    }
}
