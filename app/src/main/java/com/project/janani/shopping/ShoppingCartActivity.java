package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.janani.shopping.adapters.ShoppingCartAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView rvShoppingCartItems;
    private Button btPlaceOrderButton;
    private ScrollView scrollView;
    private ShimmerFrameLayout shimmerLayoutShoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initView();

        scrollView.setVisibility(View.GONE);
        shimmerLayoutShoppingCart.startShimmer();
        rvShoppingCartItems.setNestedScrollingEnabled(false);
        SharedPreferences loginSharedPreferences = getSharedPreferences("loginShared", MODE_PRIVATE);
        String userId = loginSharedPreferences.getString("userId", "default");
        String total_amount = loginSharedPreferences.getString("total_amount", "default");


        APIInterface apiViewCart = APIClient.getClient().create(APIInterface.class);
        apiViewCart.viewCartApiCall(userId).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {

                        String cart_id = root.orderDetails.get(0).cart_id;
                        SharedPreferences saveCartId = getSharedPreferences("saveCartId", MODE_PRIVATE);
                        SharedPreferences.Editor editor = saveCartId.edit();
                        editor.putString("select_cart_id", cart_id);
                        editor.apply();


                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        rvShoppingCartItems.setLayoutManager(linearLayoutManager);
                        ShoppingCartAdapter shoppingCartAdapter = new ShoppingCartAdapter(getApplicationContext(), root);
                        rvShoppingCartItems.setAdapter(shoppingCartAdapter);
                        int total_amount = 0;
                        for (int i = 0; i < root.orderDetails.size(); i++) {
                            total_amount += root.orderDetails.get(i).total_amount;
                        }
                        int value = root.orderDetails.size();
                        try {
                            btPlaceOrderButton.setText("Pay ₹ " + root.orderDetails.get(value).total_amount);
                        }catch (Exception e){

                        }

                        shimmerLayoutShoppingCart.stopShimmer();
                        shimmerLayoutShoppingCart.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

//                    }else if (root.equals(null)) {
//                        Toast.makeText(ShoppingCartActivity.this, "Shopping cart empty", Toast.LENGTH_SHORT).show();
//                        shimmerLayoutShoppingCart.stopShimmer();
//                        shimmerLayoutShoppingCart.setVisibility(View.GONE);
//                        scrollView.setVisibility(View.VISIBLE);
                    } else {

                        shimmerLayoutShoppingCart.stopShimmer();
                        shimmerLayoutShoppingCart.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        Toast.makeText(ShoppingCartActivity.this, "shopping Cart failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

                shimmerLayoutShoppingCart.stopShimmer();
                shimmerLayoutShoppingCart.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(ShoppingCartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectAddressActivity.class));
            }
        });
    }

    private void initView() {
        rvShoppingCartItems = findViewById(R.id.rv_shopping_cart_items);
        btPlaceOrderButton = findViewById(R.id.bt_place_order_button);
        scrollView = findViewById(R.id.scrollView);
        shimmerLayoutShoppingCart = findViewById(R.id.shimmer_layout_shopping_cart);
    }
}