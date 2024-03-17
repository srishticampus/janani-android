package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerAdditionalDetails extends AppCompatActivity implements Validator.ValidationListener, View.OnClickListener {
    int selectPicture = 200;
    private static File proImageFileLicenseImage, proImageFileProfileImage;
    @NotEmpty(message = "Please enter company address")
    private EditText etCompanyAddress;
    @NotEmpty(message = "Please enter your license number")
    private EditText etLicenseNumber;
    private SwitchMaterial swUserKitSwitch;
    @NotEmpty(message = "Please enter your bank account number")
    private EditText etAccountNumber;
    @NotEmpty(message = "Please enter your branch address")
    private EditText etBranchAddress;
    @NotEmpty(message = "Please enter your IFSC code")
    private EditText etIFSCCode;
    private TextView btRegisterSellerButton;
    private Validator validator;
    protected boolean validated;
    private static String switchState = "false";
    private TextView tvLicenseImageAddButton;
    private ImageView ivDisplayLicenseImage;
    private TextView tvProfileImageAddButton;
    private ImageView ivDisplayProfileImage;

    public static Uri selectedLicenseImage, selectedProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_additional_details);
        initView();

        tvProfileImageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDisplayProfileImage.setVisibility(View.VISIBLE);
                imageChooser();


            }
        });

        tvLicenseImageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDisplayLicenseImage.setVisibility(View.VISIBLE);
                imageChooser();

            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);

        if (swUserKitSwitch.isChecked()) {
            // on below line we are setting text
            // if switch is checked.

        } else {
            // on below line we are setting the text
            // if switch is un checked


        }

        // on below line we are adding check change listener for our switch.
        swUserKitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // on below line we are checking
                // if switch is checked or not.
                if (isChecked) {
                    // on below line we are setting text
                    // if switch is checked.
                    switchState = "true";
                    Toast.makeText(getApplicationContext(), String.valueOf(switchState), Toast.LENGTH_SHORT).show();

                } else {
                    // on below line we are setting text
                    // if switch is unchecked.
                    switchState = "false";
                    Toast.makeText(getApplicationContext(), String.valueOf(switchState), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityIfNeeded(Intent.createChooser(i, "Select Picture"), selectPicture);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == selectPicture) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    ivDisplayLicenseImage.setImageURI(selectedImageUri);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == selectPicture && resultCode == RESULT_OK && null != data) {
            try {
                selectedLicenseImage = data.getData();
                final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(selectedLicenseImage);
                proImageFileLicenseImage = new File(getCacheDir(),"file_name");
                OutputStream outputStream = new FileOutputStream(proImageFileLicenseImage);


                SharedPreferences profileImageSave = getSharedPreferences("profileImage", MODE_PRIVATE);
                selectedProfileImage = Uri.parse(profileImageSave.getString("profile_image", "default"));
                final InputStream imageStream1 = getApplicationContext().getContentResolver().openInputStream(selectedProfileImage);
                proImageFileProfileImage = new File(getCacheDir(),"file_name_two");
                OutputStream outputStream1 = new FileOutputStream(proImageFileProfileImage);


                byte[] buffer = new byte[1024];
                int byteRead;

                byte[] bufferTwo = new byte[1024];
                int byteReadTwo;

                while (((byteRead = imageStream.read(buffer)) != -1) && (byteReadTwo = imageStream1.read(bufferTwo)) != -1) {
                    outputStream.write(buffer, 0, byteRead);
                    outputStream1.write(bufferTwo, 0, byteReadTwo);
                }
                outputStream1.close();
                outputStream.close();
                imageStream.close();
                imageStream1.close();
                Toast.makeText(this, "reached4", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Toast.makeText(this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void initView() {
        etCompanyAddress = findViewById(R.id.et_company_address);
        etLicenseNumber = findViewById(R.id.et_license_number);
        swUserKitSwitch = findViewById(R.id.sw_user_kit_switch);
        etAccountNumber = findViewById(R.id.et_account_number);
        etBranchAddress = findViewById(R.id.et_branch_address);
        etIFSCCode = findViewById(R.id.et_IFSC_code);
        btRegisterSellerButton = findViewById(R.id.bt_register_seller_button);

        btRegisterSellerButton.setOnClickListener(this);
        tvLicenseImageAddButton = findViewById(R.id.tv_license_image_add_button);
        ivDisplayLicenseImage = findViewById(R.id.iv_display_license_image);
        tvProfileImageAddButton = findViewById(R.id.tv_profile_image_add_button);
        ivDisplayProfileImage = findViewById(R.id.iv_display_profile_image);
    }

    protected boolean validate() {
        if (validator != null)
            validator.validate();
        return validated;
    }

    @Override
    public void onClick(View view) {
        validator.validate();
        if (validated) {


            SharedPreferences preferences = getSharedPreferences("myShared", MODE_PRIVATE);

            RequestBody companyName = RequestBody.create(MediaType.parse("text/plain"), preferences.getString("companyName", "default"));
            RequestBody phoneNumber = RequestBody.create(MediaType.parse("text/plain"), preferences.getString("phoneNumber", "default"));
            RequestBody emailID = RequestBody.create(MediaType.parse("text/plain"), preferences.getString("emailID", "default"));
            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), preferences.getString("password", "default"));
            RequestBody confirmPassword = RequestBody.create(MediaType.parse("text/plain"), preferences.getString("confirmPassword", "default"));


            RequestBody companyAddress = RequestBody.create(MediaType.parse("text/plain"), etCompanyAddress.getText().toString());
            RequestBody licenseNumber = RequestBody.create(MediaType.parse("text/plain"), etLicenseNumber.getText().toString());
            RequestBody accountNumber = RequestBody.create(MediaType.parse("text/plain"), etAccountNumber.getText().toString());
            RequestBody branchAddress = RequestBody.create(MediaType.parse("text/plain"), etBranchAddress.getText().toString());
            RequestBody ifscCode = RequestBody.create(MediaType.parse("text/plain"), etIFSCCode.getText().toString());
            RequestBody userKit = RequestBody.create(MediaType.parse("text/plain"), switchState);


            MultipartBody.Part proImageFilePartProfileImage = null;
            MultipartBody.Part proImageFilePartLicenseImage = null;

            try {
                proImageFilePartLicenseImage = MultipartBody.Part.createFormData("avatar", proImageFileLicenseImage.getName(), RequestBody.create(MediaType.parse("image/*"), proImageFileLicenseImage));
                proImageFilePartProfileImage = MultipartBody.Part.createFormData("icon", proImageFileProfileImage.getName(), RequestBody.create(MediaType.parse("image/*"), proImageFileProfileImage));

            } catch (NullPointerException e) {
               // Toast.makeText(this, "ERROR in catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            APIInterface api_seller_registration = APIClient.getClient().create(APIInterface.class);
            api_seller_registration.CALL_API_Seller_Registration(companyName, companyAddress, phoneNumber, emailID, password, proImageFilePartProfileImage, proImageFilePartLicenseImage, licenseNumber, userKit).enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    Root root = response.body();
                    if (response.isSuccessful()) {
                        if (root.status) {
                            Toast.makeText(SellerAdditionalDetails.this, "Seller Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(SellerAdditionalDetails.this, root.message + "  Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    Toast.makeText(SellerAdditionalDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onValidationSucceeded() {
        validated = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validated = false;

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);


            // Display error messages
            if (view instanceof Spinner) {
                Spinner sp = (Spinner) view;
                view = ((LinearLayout) sp.getSelectedView()).getChildAt(0);        // we are actually interested in the text view spinner has
            }

            if (view instanceof TextView) {
                TextView et = (TextView) view;
                et.setError(message);
            }
        }
    }
}
