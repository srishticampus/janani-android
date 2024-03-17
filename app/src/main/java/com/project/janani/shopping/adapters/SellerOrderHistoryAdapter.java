package com.project.janani.shopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class SellerOrderHistoryAdapter extends RecyclerView.Adapter<SellerOrderHistoryAdapter.MyViewHolder> {

    Root root;
    Context context;


    public SellerOrderHistoryAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_custom_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(root.product_details.get(position).image1).into(holder.ivHistoryProductImage);
        holder.tvHistoryProductTitle.setText(root.product_details.get(position).name);
        holder.tvHistoryProductCategory.setText(root.product_details.get(position).description);
        holder.tvHistoryProductPrice.setText(root.product_details.get(position).selling_price);
        holder.tvHistoryProductQty.setText(root.product_details.get(position).qty);
        holder.tvHistoryOrderStatus.setText(root.product_details.get(position).order_status);


    }

    @Override
    public int getItemCount() {
        return root.product_details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cvHistoryProductImage;
        private ImageView ivHistoryProductImage;
        private TextView tvHistoryProductTitle;
        private TextView tvHistoryProductCategory;
        private TextView tvHistoryRsSign;
        private TextView tvHistoryProductPrice;
        private TextView tvHistoryProductQtyTitle;
        private TextView tvHistoryProductQty;
        private TextView tvHistoryOrderStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            cvHistoryProductImage = itemView.findViewById(R.id.cv_history_product_image);
            ivHistoryProductImage = itemView.findViewById(R.id.iv_history_product_image);
            tvHistoryProductTitle = itemView.findViewById(R.id.tv_history_product_title);
            tvHistoryProductCategory = itemView.findViewById(R.id.tv_history_product_category);
            tvHistoryRsSign = itemView.findViewById(R.id.tv_history_rs_sign);
            tvHistoryProductPrice = itemView.findViewById(R.id.tv_history_product_price);
            tvHistoryProductQtyTitle = itemView.findViewById(R.id.tv_history_product_qty_title);
            tvHistoryProductQty = itemView.findViewById(R.id.tv_history_product_qty);
            tvHistoryOrderStatus = itemView.findViewById(R.id.tv_history_order_status);
        }


    }

}
