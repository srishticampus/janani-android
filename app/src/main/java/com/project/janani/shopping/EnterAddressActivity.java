package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.project.janani.shopping.adapters.SavedAddressAdapter;

import java.util.ArrayList;

public class EnterAddressActivity extends AppCompatActivity {
    public static ArrayList<SavedAddressClass> mExampleList;
    private EditText etUserName;
    private EditText etUserAddress;
    private EditText etPhoneNumber;
    private Button btAddLocationButton;
    private EditText etUserPincode;
    private EditText etUserCity;
    private EditText etUserState;

    public EnterAddressActivity() {
    }

    public EnterAddressActivity(ArrayList<SavedAddressClass> mExampleList) {
        this.mExampleList = mExampleList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);
        initView();
        setInsertButton();


    }

    private void setInsertButton() {
        btAddLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertItem(etUserName.getText().toString(), etUserAddress.getText().toString(), etUserPincode.getText().toString(), etUserCity.getText().toString(), etUserState.getText().toString(), etPhoneNumber.getText().toString());
                saveData();
                startActivity(new Intent(getApplicationContext(), SelectAddressActivity.class));
            }


        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mExampleList);
        editor.putString("task list", json);
        editor.apply();
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
    }

    private void insertItem(String userName, String userAddress, String userPinCode, String userCity, String userState, String userPhoneNumber) {
        mExampleList.add(new SavedAddressClass(userName, userAddress, userPinCode, userCity, userState, userPhoneNumber));
        SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter();
        savedAddressAdapter.notifyItemInserted(mExampleList.size());
    }

    private void initView() {

        etUserName = findViewById(R.id.et_user_name);
        etUserAddress = findViewById(R.id.et_user_address);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btAddLocationButton = findViewById(R.id.bt_add_location_button);
        etUserPincode = findViewById(R.id.et_user_pincode);
        etUserCity = findViewById(R.id.et_user_city);
        etUserState = findViewById(R.id.et_user_state);
    }
}