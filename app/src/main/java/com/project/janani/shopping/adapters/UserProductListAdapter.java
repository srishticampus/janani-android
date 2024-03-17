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
import com.project.janani.shopping.ProductDetailsActivity;
import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class UserProductListAdapter extends RecyclerView.Adapter<UserProductListAdapter.MyViewHolder> {
    Context context;
    Root root;
    int row_index = -1;
    int[] images = {R.drawable.shopping, R.drawable.shopping, R.drawable.shopping, R.drawable.shopping};

    public UserProductListAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_display_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(root.product_details.get(position).image1).into(holder.ivProductImage);
        holder.tvProductTitle.setText(root.product_details.get(position).name);
        holder.tvProductPrice.setText(root.product_details.get(position).selling_price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("position", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ProductDetailsActivity productDetailsActivity = new ProductDetailsActivity(root.product_details.get(position).product_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return root.product_details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlHolderView, rlMainRelativeView;
        private ImageView ivProductImage;
        private TextView tvProductTitle;
        private TextView tvProductPrice;
        private RatingBar ratingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlHolderView = itemView.findViewById(R.id.rl_holder_view);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            tvProductTitle = itemView.findViewById(R.id.tv_product_title);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            rlMainRelativeView = itemView.findViewById(R.id.rl_main_relative_view);
        }

    }
}
