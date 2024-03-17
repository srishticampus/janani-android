package com.project.janani.shopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistration extends AppCompatActivity implements Validator.ValidationListener, View.OnClickListener {


    int selectPicture = 200;
    private ImageView ivDisplayImage;
    private CardView cvDpEditButton;
    @NotEmpty(message = "enter the user name")
    private EditText etUserName;
    @NotEmpty(message = "Enter your Phone number")
    @Pattern(regex = "[789][0-9]{9}", message = "Please enter valid Phone number")

    private EditText etPhoneNumber;
    @NotEmpty
    private EditText etUserAge;
    @NotEmpty
    @Email

    private EditText etEmailId;
    @Password(min = 8, message = "Password must have minimum 8 characters")

    private EditText etPassword;
    @ConfirmPassword(message = "Passwords does not match!")
    private EditText etConfirmPassword;
    private TextView btRegisterButton;

    private File proImageFile;
    private Validator validator;
    protected boolean validated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        initView();


        validator = new Validator(this);
        validator.setValidationListener(this);

//        cvDpEditButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageChooser();
//            }
//        });


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
                    ivDisplayImage.setImageURI(selectedImageUri);
                }
            }
        }

//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == selectPicture && resultCode == RESULT_OK && null != data) {
//            try {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//                proImageFile = new File(picturePath);
//
//                final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(selectedImage);
//                final Bitmap selectedImageBit = BitmapFactory.decodeStream(imageStream);
//                Bitmap photo = (Bitmap) data.getExtras().get(String.valueOf(selectedImageBit));
//                Toast.makeText(this, "no image found", Toast.LENGTH_SHORT).show();
//                ivDisplayImage.setImageBitmap(photo);
//
//            } catch (Exception e) {
//                Toast.makeText(this, "Unable", Toast.LENGTH_SHORT).show();
//
//            }
//        }
    }

    private void initView() {
//        ivDisplayImage = findViewById(R.id.iv_display_image);
//        cvDpEditButton = findViewById(R.id.cv_dp_edit_button);
        etUserName = findViewById(R.id.et_user_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etUserAge = findViewById(R.id.et_user_age);
        etEmailId = findViewById(R.id.et_email_id);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btRegisterButton = findViewById(R.id.bt_register_button);

        btRegisterButton.setOnClickListener(this);
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
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etUserName.getText().toString());
            RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), etPhoneNumber.getText().toString());
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmailId.getText().toString());
            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());
            RequestBody age = RequestBody.create(MediaType.parse("text/plain"), etUserAge.getText().toString());
            RequestBody device_token = RequestBody.create(MediaType.parse("text/plain"), "qwertyuiop");


            MultipartBody.Part proImageFilePart = null;

            try {
                proImageFilePart = MultipartBody.Part.createFormData("image", proImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), proImageFile));
            } catch (NullPointerException e) {

            }

            APIInterface api_user_registration = APIClient.getClient().create(APIInterface.class);
            api_user_registration.CALL_API_User_Registration(name, phone, age, email, password, device_token).enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    if (response.isSuccessful()) {
                        Root root = response.body();
                        if (root.status) {
                            Toast.makeText(UserRegistration.this, root.message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(UserRegistration.this, root.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    Toast.makeText(UserRegistration.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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