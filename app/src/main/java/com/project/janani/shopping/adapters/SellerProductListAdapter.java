package com.project.janani.shopping.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.janani.shopping.R;
import com.project.janani.shopping.SellerProductDetailsActivity;
import com.project.janani.shopping.model.Root;

public class SellerProductListAdapter extends RecyclerView.Adapter<SellerProductListAdapter.MyViewHolder> {

    Root root;
    Context context;


    public SellerProductListAdapter(Root root, Context context) {
        this.root = root;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_display_custom_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvProductTitle.setText(root.product_details.get(position).name);
        Glide.with(context).load(root.product_details.get(position).image1).into(holder.ivProductImage);
        holder.tvProductPrice.setText(root.product_details.get(position).selling_price);
        holder.ratingBar.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SellerProductDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                SellerProductDetailsActivity sellerProductDetailsActivity = new SellerProductDetailsActivity(root.product_details.get(position).product_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return root.product_details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView sellerProductImage;
        private TextView sellerProductName;
        private TextView sellerProductPrice;

        private RelativeLayout rlHolderView, rlMainRelativeView;
        private ImageView ivProductImage;
        private TextView tvProductTitle;
        private TextView tvProductPrice;
        private RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            rlHolderView = itemView.findViewById(R.id.rl_holder_view);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            tvProductTitle = itemView.findViewById(R.id.tv_product_title);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            rlMainRelativeView = itemView.findViewById(R.id.rl_main_relative_view);
        }
    }
}
