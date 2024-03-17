package com.project.janani.shopping.retrofit;

import com.project.janani.shopping.model.Root;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @Multipart
    @POST("user_login.php")
    Call<Root> CALL_APIUserLogin(@Part("mobile") RequestBody phone_number,
                                 @Part("password") RequestBody password);

    @Multipart
    @POST("seller_login.php")
    Call<Root> CALL_APISellerLogin(@Part("phone") RequestBody phone_number,
                                   @Part("password") RequestBody password);


    @Multipart
    @POST("user_reg.php")
    Call<Root> CALL_API_User_Registration(
            @Part("name") RequestBody name,
            @Part("mobile") RequestBody mobile,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("device_token") RequestBody deviceToken
    );

    @Multipart
    @POST("edit_user.php")
    Call<Root> CALL_API_User_Edit_Account(
            @Part("name") RequestBody name,
            @Part("id") String id,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("password") RequestBody password
    );


    @Multipart
    @POST("seller_reg.php")
    Call<Root> CALL_API_Seller_Registration(@Part("name") RequestBody name,
                                            @Part("address") RequestBody company_address,
                                            @Part("phone") RequestBody mobile,
                                            @Part("email") RequestBody email,
                                            @Part("password") RequestBody password,
                                            @Part MultipartBody.Part avatar,
                                            @Part MultipartBody.Part icon,
                                            @Part("license_num") RequestBody license_number,
                                            @Part("user_kit") RequestBody user_kit
    );

    @Multipart
    @POST("edit_seller.php")
    Call<Root> CALL_API_Seller_Edit_Account(@Part("id") String id,
                                            @Part("name") RequestBody name,
                                            @Part("address") RequestBody address,
                                            @Part("phone") RequestBody phone,
                                            @Part("email") RequestBody email,
                                            @Part("user_kit") RequestBody user_kit,
                                            @Part("password") RequestBody password,
                                            @Part("license_num") RequestBody license_number);

    @FormUrlEncoded
    @POST("view_product_seller_id.php")
    Call<Root> viewProductsSellerApiCall(@Field("seller_id") String sellerId);


    @POST("view_products.php")
    Call<Root> viewProductsUserApiCall();

    @FormUrlEncoded
    @POST("product_filter.php")
    Call<Root> productCategoryFilterApiCall(@Field("category") String category);

    @FormUrlEncoded
    @POST("view_product_byProduct_id.php")
    Call<Root> viewProductDetailsApiCall(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("add_cart.php")
    Call<Root> addToCartApiCall(@Field("product_id") String product_id, @Field("user_id") String user_id, @Field("qty") String quantity);


    @FormUrlEncoded
    @POST("view_cart.php")
    Call<Root> viewCartApiCall(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("delete_product.php")
    Call<Root> deleteProductApi(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("view_user.php")
    Call<Root> viewUserAccountApiCall(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("view_seller.php")
    Call<Root> viewSellerAccountApiCall(@Field("sellerid") String sellerid);

    @POST("view_category.php")
    Call<Root> categoryListApiCall();

    @POST("view_faq.php")
    Call<Root> faqApiCall();

    @POST("dietchart.php")
    Call<Root> dietChartCall();

    @FormUrlEncoded
    @POST("order_history.php")
    Call<Root> orderHistoryApiCall(@Field("user_id") String user_id);

    @Multipart
    @POST("add_product.php")
    Call<Root> addProductSellerApiCall(@Part("seller_id") RequestBody sellerId,
                                       @Part("name") RequestBody name,
                                       @Part("description") RequestBody description,
                                       @Part("mrp") RequestBody mrp,
                                       @Part("selling_price") RequestBody sellingPrice,
                                       @Part("qty") String quantity,
                                       @Part MultipartBody.Part image1,
                                       @Part MultipartBody.Part image2,
                                       @Part MultipartBody.Part image3,
                                       @Part MultipartBody.Part video1);

    @Multipart
    @POST("add_product.php")
    Call<Root> newAddProductSellerApiCall(@Part("seller_id") RequestBody sellerId,
                                          @Part("name") RequestBody name,
                                          @Part("category") RequestBody category,
                                          @Part("description") RequestBody description,
                                          @Part("mrp") RequestBody mrp,
                                          @Part("selling_price") RequestBody sellingPrice,
                                          @Part("qty") String quantity,
                                          @Part MultipartBody.Part image1,
                                          @Part MultipartBody.Part image2);

    @FormUrlEncoded
    @POST("seller_order_history.php")
    Call<Root> viewOrderHistorySellerApiCall(@Field("seller_id") String seller_id);


    @FormUrlEncoded
    @POST("product_filter.php")
    Call<Root> categoryFilterApiCall(@Field("category") String category);

    @FormUrlEncoded
    @POST("remove_item_from_cart.php")
    Call<Root> removeItemApiCall(@Field("product_id") String product_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("loyalty.php")
    Call<Root> updatePriceApiCall(@Field("product_id") String product_id, @Field("selling_price") String selling_price);

    @FormUrlEncoded
    @POST("search_product.php")
    Call<Root> searchItemApiCall(@Field("term") String term);

    @FormUrlEncoded
    @POST("add_wishlist.php")
    Call<Root> addToWishListApiCall(@Field("product_id") String product_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("kit_button.php")
    Call<Root> userKitCheckOutApiCall(@Field("user_id") String user_id, @Field("lat") String latitude, @Field("log") String longitude, @Field("address") String address, @Field("phone") String phone, @Field("category") String category, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("place_order.php")
    Call<Root> placeOrderAPiCall(@Field("user_id") String user_id,
                                 @Field("customer_name") String customer_name,
                                 @Field("address") String address,
                                 @Field("pin_code") String pin_code,
                                 @Field("phone") String phone,
                                 @Field("city") String city,
                                 @Field("state") String state,
                                 @Field("cart_id") String cart_id);

    @POST("view_wishlist.php")
    Call<Root> viewWishListApiCall();

//    @FormUrlEncoded
//    @POST("add_cart.php")
//    Call<Root> placeOrderAPiCall(@Field("product_id") String product_id,
//                                 @Field("user_id") String user_id,
//                                 @Field("qty") String quantity);

//    @FormUrlEncoded
//    @POST("seller_login.php")
//    Call<Root>sellerLoginApiCall(@Field("phone")String phone)

    @FormUrlEncoded
    @POST("remove_from_wishlist.php")
    Call<Root> removeFromWishListApiCall(@Field("product_id") String product_id, @Field("user_id") String user_id);

}
