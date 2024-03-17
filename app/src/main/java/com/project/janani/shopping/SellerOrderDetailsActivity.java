package com.project.janani.shopping;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SellerOrderDetailsActivity extends AppCompatActivity {

    private TextView sellerOrderAddressTv;
    private ImageView sellerHistoryProductImg;
    private TextView sellerOrderHistoryProductName;
    private TextView sellerOrderHistoryProductPrice;
    private TextView sellerOrderHistoryProductQuantity;
    private TextView sellerOrderHistoryProductSubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_details);
        initView();





    }

    private void initView() {
        sellerOrderAddressTv = findViewById(R.id.seller_order_address_tv);
        sellerHistoryProductImg = findViewById(R.id.seller_history_product_img);
        sellerOrderHistoryProductName = findViewById(R.id.seller_order_history_product_name);
        sellerOrderHistoryProductPrice = findViewById(R.id.seller_order_history_product_price);
        sellerOrderHistoryProductQuantity = findViewById(R.id.seller_order_history_product_quantity);
        sellerOrderHistoryProductSubTotal = findViewById(R.id.seller_order_history_product_sub_total);
    }
}