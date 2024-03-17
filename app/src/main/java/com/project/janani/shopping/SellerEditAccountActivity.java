package com.project.janani.shopping;

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

public class SellerEditAccountActivity extends AppCompatActivity {

    private EditText etCompanyName;
    private EditText etPhoneNumber;
    private EditText etEmailId;
    private EditText etPassword;
    private EditText etAddress;
    private TextView btSaveButton;
    private SwitchMaterial swUserKitSwitch;
    public static boolean switchState = false;
    private EditText etLicenseNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_edit_account);
        initView();
        String sellerId = getIntent().getStringExtra("sellerId");
        swUserKitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // on below line we are checking
                // if switch is checked or not.
                if (isChecked) {
                    // on below line we are setting text
                    // if switch is checked.
                    switchState = true;
                    Toast.makeText(getApplicationContext(), String.valueOf(switchState), Toast.LENGTH_SHORT).show();

                } else {
                    // on below line we are setting text
                    // if switch is unchecked.
                    switchState = false;
                    Toast.makeText(getApplicationContext(), String.valueOf(switchState), Toast.LENGTH_SHORT).show();

                }
            }
        });
        btSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSellerDetails(sellerId);
            }

        });



    }

    private void editSellerDetails(String sellerId) {

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etCompanyName.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNumber.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmailId.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());
        RequestBody userKit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(switchState));
        RequestBody license_number = RequestBody.create(MediaType.parse("text/plain"), etLicenseNumber.getText().toString());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), etAddress.getText().toString());


        APIInterface apiSellerEditAccount = APIClient.getClient().create(APIInterface.class);
        apiSellerEditAccount.CALL_API_Seller_Edit_Account(sellerId, name, address, phone, email, userKit, password, license_number).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(SellerEditAccountActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SellerEditAccountActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(SellerEditAccountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        etCompanyName = findViewById(R.id.et_company_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etEmailId = findViewById(R.id.et_email_id);
        etPassword = findViewById(R.id.et_password);
        etAddress = findViewById(R.id.et_address);
        btSaveButton = findViewById(R.id.bt_save_button);
        swUserKitSwitch = findViewById(R.id.sw_user_kit_switch);
        etLicenseNumber = findViewById(R.id.et_license_number);
    }
}