package com.project.janani.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEditAccountActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPhoneNumber;
    private EditText etUserAge;
    private EditText etEmailId;
    private EditText etPassword;
    private TextView btSaveChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_account);
        initView();
        String userId = getIntent().getStringExtra("userId");
        btSaveChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserDetails(userId);
            }
        });


    }

    private void editUserDetails(String userId) {
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etUserName.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNumber.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmailId.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());
        RequestBody age = RequestBody.create(MediaType.parse("text/plain"), etUserAge.getText().toString());


        APIInterface apiUserEditDetails = APIClient.getClient().create(APIInterface.class);
        apiUserEditDetails.CALL_API_User_Edit_Account(name, userId, age, email, phone, password).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(UserEditAccountActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserEditAccountActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(UserEditAccountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        etUserName = findViewById(R.id.et_user_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etUserAge = findViewById(R.id.et_user_age);
        etEmailId = findViewById(R.id.et_email_id);
        etPassword = findViewById(R.id.et_password);
        btSaveChangeButton = findViewById(R.id.bt_save_change_button);
    }
}