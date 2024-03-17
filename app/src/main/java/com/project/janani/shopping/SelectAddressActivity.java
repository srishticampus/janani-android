package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.janani.shopping.adapters.SavedAddressAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressActivity extends AppCompatActivity {
    ArrayList<SavedAddressClass> savedAddressList;
    private RecyclerView savedAddressView;
    private SavedAddressAdapter savedAddressAdapter;
    private TextView tvAddAddressButton;
    private Button btPlaceOrderButton;
    private TextView tvAllClearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initView();
        loadData();
        buildRecyclerView();


        tvAddAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EnterAddressActivity.class));
            }
        });

        btPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface apiPlaceOrder = APIClient.getClient().create(APIInterface.class);
                SharedPreferences loginSharedPreferences = getSharedPreferences("loginShared", MODE_PRIVATE);
                String user_id = loginSharedPreferences.getString("userId", "default");
                String product_id = loginSharedPreferences.getString("productID", "default");
                String quantity = loginSharedPreferences.getString("quantity", "default");

                SharedPreferences selectAddressPreference = getSharedPreferences("selectAddress", MODE_PRIVATE);
                String user_name = selectAddressPreference.getString("select_username", "default");
                String user_address = selectAddressPreference.getString("select_address", "default");
                String user_pin_code = selectAddressPreference.getString("select_pin_code", "default");
                String user_city = selectAddressPreference.getString("select_city", "default");
                String user_state = selectAddressPreference.getString("select_state", "default");
                String user_phone_number = selectAddressPreference.getString("select_phone_number", "default");

                SharedPreferences saveCartId = getSharedPreferences("saveCartId", MODE_PRIVATE);
                String cart_id = saveCartId.getString("select_cart_id", "default");


                apiPlaceOrder.placeOrderAPiCall(user_id, user_name, user_address, user_pin_code, user_phone_number, user_city, user_state, cart_id).enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        Root root = response.body();
                        if (response.isSuccessful()) {
                            if (root.status) {
                                //Toast.makeText(SelectAddressActivity.this, root.message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SelectAddressActivity.this, UserOrderPaymentActivity.class));
                                finishAffinity();
                            } else {
                                Toast.makeText(SelectAddressActivity.this, root.message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        Toast.makeText(SelectAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tvAllClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedAddressList = new ArrayList<>();
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(savedAddressList);
                editor.putString("task list", json);
                editor.apply();
                Toast.makeText(SelectAddressActivity.this, "saved", Toast.LENGTH_SHORT).show();
                buildRecyclerView();
            }
        });

    }

    public void buildRecyclerView() {

        savedAddressView = findViewById(R.id.rv_saved_address_view);
        savedAddressView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        savedAddressAdapter = new SavedAddressAdapter(getApplicationContext(), savedAddressList);
        savedAddressView.setLayoutManager(linearLayoutManager);
        savedAddressView.setAdapter(savedAddressAdapter);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<SavedAddressClass>>() {
        }.getType();


        savedAddressList = gson.fromJson(json, type);
        if (savedAddressList == null) {
            savedAddressList = new ArrayList<>();
        }
        EnterAddressActivity enterAddressActivity = new EnterAddressActivity(savedAddressList);
    }

    private void initView() {
        tvAllClearButton = findViewById(R.id.tv_all_clear_button);
        btPlaceOrderButton = findViewById(R.id.bt_place_order_button);
        tvAddAddressButton = findViewById(R.id.tv_add_address_button);
    }
}

