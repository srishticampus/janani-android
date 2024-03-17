package com.project.janani.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserOrderPaymentActivity extends AppCompatActivity {

    private Button btPlaceUserkitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_payment);
        initView();

        btPlaceUserkitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserOrderPaymentActivity.this, UserHomeActivity.class));
            }
        });

    }

    private void initView() {
        btPlaceUserkitOrder = findViewById(R.id.bt_place_userkit_order);
    }
}