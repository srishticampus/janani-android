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
import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    Context context;
    Root root;


    public OrderHistoryAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(root.orderDetails.get(position).photo).into(holder.ivHistoryProductImage);
        holder.tvHistoryProductTitle.setText(root.orderDetails.get(position).name);
        holder.tvHistoryProductCategory.setText(root.orderDetails.get(position).description);
        holder.tvHistoryProductPrice.setText(root.orderDetails.get(position).selling_price);
        holder.tvHistoryProductQty.setText(root.orderDetails.get(position).quantity_ordered);
        holder.tvHistoryOrderStatus.setText(root.orderDetails.get(position).order_status);

    }

    @Override
    public int getItemCount() {
        return root.orderDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cvHistoryProductImage;
        private ImageView ivHistoryProductImage;
        private TextView tvHistoryProductTitle;
        private TextView tvHistoryProductCategory;
        private TextView tvHistoryProductPrice;
        private TextView tvHistoryOrderStatus;
        private TextView tvHistoryProductQty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvHistoryProductImage = itemView.findViewById(R.id.cv_history_product_image);
            ivHistoryProductImage = itemView.findViewById(R.id.iv_history_product_image);
            tvHistoryProductQty = itemView.findViewById(R.id.tv_history_product_qty);
            tvHistoryProductTitle = itemView.findViewById(R.id.tv_history_product_title);
            tvHistoryProductCategory = itemView.findViewById(R.id.tv_history_product_category);
            tvHistoryProductPrice = itemView.findViewById(R.id.tv_history_product_price);
            tvHistoryOrderStatus = itemView.findViewById(R.id.tv_history_order_status);
        }
    }
}
