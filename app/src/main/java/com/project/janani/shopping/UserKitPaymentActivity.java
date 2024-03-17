package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserKitPaymentActivity extends AppCompatActivity {
    String phone;
    private RadioGroup radioGroup;
    private RadioButton rbUpiSelect;
    private EditText etUpiAddress;
    private RadioButton rbCardSelect;
    private RadioButton rbCodSelect;
    private EditText etCardPayment;
    private EditText etCardName;
    private EditText etCardCvv;
    private EditText etCardExpiryDate;
    private Button btPlaceUserKitOrder;


    public static String getLatitude, getLongitude;

    public UserKitPaymentActivity() {
    }

    public UserKitPaymentActivity(String getLatitude, String getLongitude) {
        this.getLatitude = getLatitude;
        this.getLongitude = getLongitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_kit_payment);
        initView();
        etUpiAddress.setVisibility(View.GONE);
        etCardPayment.setVisibility(View.GONE);
        etCardName.setVisibility(View.GONE);
        etCardCvv.setVisibility(View.GONE);
        etCardExpiryDate.setVisibility(View.GONE);

        btPlaceUserKitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeUserKitOrder();
            }
        });

    }

    private void placeUserKitOrder() {

        APIInterface apiUserKitCheckout = APIClient.getClient().create(APIInterface.class);

        SharedPreferences sharedPreferences = getSharedPreferences("loginShared", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("userId", "default;");
        String address = sharedPreferences.getString("address", "default");
        if (!(address.isEmpty())) {
            getLongitude = getLatitude = null;
        }
        String category = sharedPreferences.getString("category", "default");
        String productID = sharedPreferences.getString("productID", "default");
        String userName = sharedPreferences.getString("userName", "default");


        if (sharedPreferences.getString("optionalPhoneNumber", "").isEmpty()) {
            phone = sharedPreferences.getString("phoneNumber", "default");
        } else {
            phone = sharedPreferences.getString("optionalPhoneNumber", null);
        }


        apiUserKitCheckout.userKitCheckOutApiCall(user_id, getLatitude, getLongitude, address, phone, category, productID).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(UserKitPaymentActivity.this, root.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserKitPaymentActivity.this, UserHomeActivity.class));
                        finishAffinity();
                    }
                } else {
                    Toast.makeText(UserKitPaymentActivity.this, "api call error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(UserKitPaymentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        radioGroup = findViewById(R.id.radioGroup);
        rbUpiSelect = findViewById(R.id.rb_upi_select);
        etUpiAddress = radioGroup.findViewById(R.id.et_upi_address);
        rbCardSelect = radioGroup.findViewById(R.id.rb_card_select);
        etCardPayment = findViewById(R.id.et_card_payment);
        rbCodSelect = findViewById(R.id.rb_cod_select);
        etCardCvv = findViewById(R.id.et_card_cvv);
        etCardName = findViewById(R.id.et_card_name);
        etCardExpiryDate = findViewById(R.id.et_card_expiry_date);
        btPlaceUserKitOrder = findViewById(R.id.bt_place_userkit_order);
    }

    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_upi_select:
                if (checked) {
                    etUpiAddress.setVisibility(View.VISIBLE);
                    etCardPayment.setVisibility(View.GONE);
                    etCardName.setVisibility(View.GONE);
                    etCardCvv.setVisibility(View.GONE);
                    etCardExpiryDate.setVisibility(View.GONE);
                    rbCardSelect.setChecked(false);
                    rbCodSelect.setChecked(false);

                }

                break;
            case R.id.rb_card_select:
                if (checked) {
                    etUpiAddress.setVisibility(View.GONE);
                    etCardPayment.setVisibility(View.VISIBLE);
                    etCardName.setVisibility(View.VISIBLE);
                    etCardCvv.setVisibility(View.VISIBLE);
                    etCardExpiryDate.setVisibility(View.VISIBLE);
                    rbUpiSelect.setChecked(false);
                    rbCardSelect.setChecked(false);
                    rbCodSelect.setChecked(false);
                }
                break;
            case R.id.rb_cod_select:
                if (checked) {
                    etUpiAddress.setVisibility(View.GONE);
                    etCardPayment.setVisibility(View.GONE);
                    etCardName.setVisibility(View.GONE);
                    etCardCvv.setVisibility(View.GONE);
                    etCardExpiryDate.setVisibility(View.GONE);
                    rbCardSelect.setChecked(false);
                    rbUpiSelect.setChecked(false);
                }
        }
    }
}
