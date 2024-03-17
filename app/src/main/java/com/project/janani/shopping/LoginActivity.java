package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private EditText etPassword;
    private SwitchMaterial swAccountSwitch;
    private TextView btLoginButton;
    private TextView tvRegisterButton;
    private TextView tvSellerSwitchButton;
    private TextView tvUserSwitchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initView();

        buttonPress();


        swAccountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Log in as Seller ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Log in as user", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!swAccountSwitch.isChecked()) {
                    if (!(etPhoneNumber.getText().toString().isEmpty() && etPassword.getText().toString().isEmpty())) {
                        userLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter Login Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sellerLogin();
                }

            }
        });
    }

    private void sellerLogin() {
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNumber.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());

        APIInterface api_seller_login = APIClient.getClient().create(APIInterface.class);
        api_seller_login.CALL_APISellerLogin(phone, password).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        SharedPreferences loginSellerSharedPreferences = getSharedPreferences("loginSellerShared", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginSellerSharedPreferences.edit();
                        editor.putString("sellerId", root.userDetails.get(0).id);
                        editor.putString("phoneNumber", root.userDetails.get(0).mobile);
                        editor.putString("userName", root.userDetails.get(0).name);
                        editor.apply();

                        Intent sellerIntent = new Intent(LoginActivity.this, SellerHomeActivity.class);
                        startActivity(sellerIntent);
                        Toast.makeText(LoginActivity.this, "Logging in as seller", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userLogin() {

        String phNumber = etPhoneNumber.getText().toString();
        String pWord = etPassword.getText().toString();
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNumber.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());

        APIInterface api_user_login = APIClient.getClient().create(APIInterface.class);
        api_user_login.CALL_APIUserLogin(phone, password).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {

                        SharedPreferences loginSharedPreferences = getSharedPreferences("loginShared", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginSharedPreferences.edit();
                        editor.putString("userId", root.userDetails.get(0).id);
                        editor.putString("phoneNumber", root.userDetails.get(0).mobile);
                        editor.putString("userName", root.userDetails.get(0).name);
                        editor.apply();

                        Intent userIntent = new Intent(LoginActivity.this, UserHomeActivity.class);
                        startActivity(userIntent);
                        Toast.makeText(LoginActivity.this, "Logging in as user", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void buttonPress() {
        tvUserSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserRegistration.class));
            }
        });
        tvSellerSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SellerRegistration.class));
            }
        });
    }

    private void initView() {
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        swAccountSwitch = findViewById(R.id.sw_account_switch);
        btLoginButton = findViewById(R.id.bt_login_button);
        tvRegisterButton = findViewById(R.id.tv_register);
        tvSellerSwitchButton = findViewById(R.id.tv_seller_switch_button);
        tvUserSwitchButton = findViewById(R.id.tv_user_switch_button);
    }
}