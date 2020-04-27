package com.nerojust.arkandarcsadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;

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
        holder.productName.setText(productsResponseList.getResults().get(position).getProductName());
        holder.productCategory.setText(productsResponseList.getResults().get(position).getProductCategory());
        holder.productAmount.setText(productsResponseList.getResults().get(position).getProductAmount());
        holder.productDiscountedAmount.setText(productsResponseList.getResults().get(position).getProductDiscountedAmount());
        Glide.with(context)
                .load(productsResponseList.getResults().get(position).getProductImages().getImageUrl())
                .placeholder(R.drawable.load)
                .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return productsResponseList.getRecordCount();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productCategory, productAmount, productDiscountedAmount;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productAmount = itemView.findViewById(R.id.productAmount);
            productDiscountedAmount = itemView.findViewById(R.id.productDiscountedAmount);
        }
    }
}
