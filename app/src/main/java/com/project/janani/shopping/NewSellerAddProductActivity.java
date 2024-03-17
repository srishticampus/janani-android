package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSellerAddProductActivity extends AppCompatActivity {
    public static Uri selectedProductImageOne;
    private EditText productNameEdittext;
    private EditText productCategoryEdittext;
    private EditText productDescriptionEdittext;
    private EditText productMrpEdittext;
    private EditText productSellingPriceEdittext;
    private ImageView productImageOneImageView;
    private ImageView productImageTwoImageView;
    private ImageView productVideoView;
    private Button btAddProductButton;
    private int selectPicture = 200;
    int i = 0;
    public File proImageFileProductImageOne;
    public File proImageFileProductImageTwo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_seller_add_product);
        initView();

        btAddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCall();
            }
        });


        productImageOneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                imageChooser(i);
            }
        });

        productImageTwoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 0;
                imageChooser(i);
            }
        });
    }

    private void initView() {
        productNameEdittext = findViewById(R.id.product_name_edittext);
        productCategoryEdittext = findViewById(R.id.product_category_edittext);
        productDescriptionEdittext = findViewById(R.id.product_description_edittext);
        productMrpEdittext = findViewById(R.id.product_mrp_edittext);
        productSellingPriceEdittext = findViewById(R.id.product_selling_price_edittext);
        productImageOneImageView = findViewById(R.id.product_image_one_image_view);
        productImageTwoImageView = findViewById(R.id.product_image_two_image_view);
        btAddProductButton = findViewById(R.id.bt_add_product_button);
    }

    void imageChooser(int i) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);


        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"), selectPicture);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == selectPicture) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    if (i == 1) {
                        productImageOneImageView.setImageURI(selectedImageUri);
                    } else {
                        productImageTwoImageView.setImageURI(selectedImageUri);
                    }

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == selectPicture && resultCode == RESULT_OK && null != data) {
            try {
                if (i == 1) {


                    selectedProductImageOne = data.getData();
                    final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(selectedProductImageOne);
                    proImageFileProductImageOne = new File(getCacheDir(),"file_name_one");
                    OutputStream outputStream = new FileOutputStream(proImageFileProductImageOne);

                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while (((byteRead = imageStream.read(buffer)) != -1)) {
                        outputStream.write(buffer, 0, byteRead);
                    }
                    outputStream.close();
                    imageStream.close();
                } else {
                    Uri selectedProductImageTwo = data.getData();
                    final InputStream imageStream1 = getApplicationContext().getContentResolver().openInputStream(selectedProductImageTwo);
                    proImageFileProductImageTwo = new File(getCacheDir(), "file_name_two");
                    OutputStream outputStream1 = new FileOutputStream(proImageFileProductImageTwo);


                    byte[] bufferTwo = new byte[1024];
                    int byteReadTwo;

                    while (((byteReadTwo = imageStream1.read(bufferTwo)) != -1)) {
                        outputStream1.write(bufferTwo, 0, byteReadTwo);
                    }
                    outputStream1.close();
                    imageStream1.close();
                   // Toast.makeText(this, "reached4", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
               // Toast.makeText(this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void apiCall() {

        SharedPreferences loginSellerSharedPreferences = getSharedPreferences("loginSellerShared", MODE_PRIVATE);
        String sellerId = loginSellerSharedPreferences.getString("sellerId", "default");

        RequestBody sellerIdRB = RequestBody.create(MediaType.parse("text/plain"), sellerId);
        RequestBody productNameRb = RequestBody.create(MediaType.parse("text/plain"), productNameEdittext.getText().toString());
        RequestBody categoryRb = RequestBody.create(MediaType.parse("text/plain"), productCategoryEdittext.getText().toString());
        RequestBody descriptionRb = RequestBody.create(MediaType.parse("text/plain"), productDescriptionEdittext.getText().toString());
        RequestBody mrpRb = RequestBody.create(MediaType.parse("text/plain"), productMrpEdittext.getText().toString());
        RequestBody sellingPriceRb = RequestBody.create(MediaType.parse("text/plain"), productSellingPriceEdittext.getText().toString());
        RequestBody image = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(selectedProductImageOne));

        MultipartBody.Part imageFileOneMultiPart = null;
        MultipartBody.Part imageFileTwoMultiPart = null;

        try {

            imageFileOneMultiPart = MultipartBody.Part.createFormData("avatar", proImageFileProductImageOne.getName(),
                    RequestBody.create(MediaType.parse("image/*"), proImageFileProductImageOne));
            imageFileTwoMultiPart = MultipartBody.Part.createFormData("icon", proImageFileProductImageTwo.getName(),
                    RequestBody.create(MediaType.parse("image/*"), proImageFileProductImageTwo));
        } catch (NullPointerException e) {
           // Toast.makeText(this, "Error!  " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        APIInterface apiNewAddProduct = APIClient.getClient().create(APIInterface.class);
        apiNewAddProduct.newAddProductSellerApiCall(sellerIdRB, productNameRb, categoryRb, descriptionRb, mrpRb, sellingPriceRb, "1", imageFileOneMultiPart, imageFileTwoMultiPart).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(NewSellerAddProductActivity.this, "Success  " + root.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewSellerAddProductActivity.this, SellerHomeActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(NewSellerAddProductActivity.this, "Product Addition Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(NewSellerAddProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("er_msg",t.getMessage());
            }
        });
    }
}