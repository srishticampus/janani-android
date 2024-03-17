package com.project.janani.shopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class SellerOrderHistoryProductListAdapter extends RecyclerView.Adapter<SellerOrderHistoryProductListAdapter.MyViewHolder> {

    Root root;
    Context context;



    public SellerOrderHistoryProductListAdapter(Root root, Context context) {
        this.root = root;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.seller_order_details_product_recycler_view_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return root.product_details.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView sellerHistoryProductImg;
        private TextView sellerOrderHistoryProductName;
        private TextView sellerOrderHistoryProductPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }
        private void initView(View itemView) {
            sellerHistoryProductImg = itemView.findViewById(R.id.seller_history_product_img);
            sellerOrderHistoryProductName = itemView.findViewById(R.id.seller_order_history_product_name);
            sellerOrderHistoryProductPrice = itemView.findViewById(R.id.seller_order_history_product_price);
        }


    }

}
