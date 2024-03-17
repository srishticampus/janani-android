package com.project.janani.shopping.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.janani.shopping.ProductDetailsActivity;
import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {
    Context context;
    Root root;

    public SearchResultAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(root.product_details.get(position).image1).into(holder.ivSearchProductImage);
        holder.tvSearchProductTitle.setText(root.product_details.get(position).name);
        holder.tvSearchProductPrice.setText(root.product_details.get(position).selling_price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
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
        private ImageView ivSearchProductImage;
        private TextView tvSearchProductTitle;
        private TextView tvSearchProductPrice;
        private RatingBar searchRatingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSearchProductImage = itemView.findViewById(R.id.iv_search_product_image);
            tvSearchProductTitle = itemView.findViewById(R.id.tv_search_product_title);
            tvSearchProductPrice = itemView.findViewById(R.id.tv_search_product_price);
            searchRatingBar = itemView.findViewById(R.id.search_ratingBar);
        }
    }
}
