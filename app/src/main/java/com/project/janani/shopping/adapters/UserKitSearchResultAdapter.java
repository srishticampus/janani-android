package com.project.janani.shopping.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.janani.shopping.ProductDetailsActivity;
import com.project.janani.shopping.R;
import com.project.janani.shopping.UserKitAddressActivity;
import com.project.janani.shopping.model.Root;

public class UserKitSearchResultAdapter extends RecyclerView.Adapter<UserKitSearchResultAdapter.MyViewHolder> {
    Context context;
    Root root;

    public UserKitSearchResultAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_kit_search_result_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(root.product_details.get(position).image1).into(holder.ivUserKitSearchProductImage);
        holder.tvUserKitSearchProductTitle.setText(root.product_details.get(position).name);
        holder.tvUserKitSearchProductPrice.setText(root.product_details.get(position).selling_price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserKitAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return root.product_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivUserKitSearchProductImage;
        private TextView tvUserKitSearchProductTitle;
        private TextView tvUserKitSearchProductPrice;
        private RatingBar UserKitsearchRatingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivUserKitSearchProductImage = itemView.findViewById(R.id.iv_userkit_search_product_image);
            tvUserKitSearchProductTitle = itemView.findViewById(R.id.tv_userkit_search_product_title);
            tvUserKitSearchProductPrice = itemView.findViewById(R.id.tv_userkit_search_product_price);
            UserKitsearchRatingBar = itemView.findViewById(R.id.userkit_search_ratingBar);
        }
    }
}
