package com.project.janani.shopping;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProductDetailsActivity extends AppCompatActivity {
    public static String product_id;
    private ImageView ivProductImage;
    private TextView tvProductName;
    private TextView tvProductSellingPrice;
    private TextView tvDescriptionBody;
    private TextView btUpdatePriceButton;
    private TextView btDeleteProductButton;

    public SellerProductDetailsActivity() {
    }

    public SellerProductDetailsActivity(String product_id) {
        this.product_id = product_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_details);
        initView();

        displayProductDetails(product_id);


        btDeleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface apiDeleteProductApi = APIClient.getClient().create(APIInterface.class);
                apiDeleteProductApi.deleteProductApi(product_id).enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        Root root = response.body();
                        if (response.isSuccessful()) {
                            Toast.makeText(SellerProductDetailsActivity.this, root.message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SellerProductDetailsActivity.this, SellerHomeActivity.class));
                        } else {
                            Toast.makeText(SellerProductDetailsActivity.this, root.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        Toast.makeText(SellerProductDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btUpdatePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SellerProductDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_selling_price_dialog_box);

                final EditText new_price = dialog.findViewById(R.id.et_selling_price);
                Button updatePriceButton = dialog.findViewById(R.id.bt_update_price_button);

                updatePriceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (new_price.getText().toString().isEmpty()) {
                            Toast.makeText(SellerProductDetailsActivity.this, "Enter a value", Toast.LENGTH_SHORT).show();

                        } else {
                            updatePrice(product_id, new_price.getText().toString());
                            dialog.dismiss();
                            startActivity(new Intent(SellerProductDetailsActivity.this, SellerHomeActivity.class));
                            finishAffinity();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void updatePrice(String product_id, String new_price) {

        APIInterface apiUpdatePrice = APIClient.getClient().create(APIInterface.class);
        apiUpdatePrice.updatePriceApiCall(product_id, new_price).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(SellerProductDetailsActivity.this, root.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SellerProductDetailsActivity.this, SellerHomeActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(SellerProductDetailsActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(SellerProductDetailsActivity.this, "Server Error!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(String product_id) {

        APIInterface apiProductDetails = APIClient.getClient().create(APIInterface.class);
        apiProductDetails.viewProductDetailsApiCall(product_id).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Glide.with(getApplicationContext()).load(root.product_details.get(0).image1).into(ivProductImage);
                        tvProductName.setText(root.product_details.get(0).name);
                        tvDescriptionBody.setText(root.product_details.get(0).description);
                        tvProductSellingPrice.setText(root.product_details.get(0).selling_price);

                    } else {
                        Toast.makeText(SellerProductDetailsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(SellerProductDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        ivProductImage = findViewById(R.id.iv_product_image);
        tvProductName = findViewById(R.id.tv_product_name);
        tvProductSellingPrice = findViewById(R.id.tv_product_selling_price);
        tvDescriptionBody = findViewById(R.id.tv_description_body);
        btUpdatePriceButton = findViewById(R.id.bt_update_price_button);
        btDeleteProductButton = findViewById(R.id.bt_delete_product_button);
    }
}