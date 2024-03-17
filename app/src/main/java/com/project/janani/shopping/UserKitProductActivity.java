package com.project.janani.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.janani.shopping.adapters.UserKitCategoryAdapter;
import com.project.janani.shopping.adapters.UserKitProductAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserKitProductActivity extends AppCompatActivity {

    private EditText etUserKitSearchBar;
    private RecyclerView rvUserKitCategoriesUserKit;
    private RecyclerView rvUserKitProductUserKit;
    private ImageView ivUserKitSearchButton;
    private ScrollView scrollView;
    private ShimmerFrameLayout shimmerLayoutUserKitHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_kit_product);
        initView();

        scrollView.setVisibility(View.GONE);
        shimmerLayoutUserKitHome.startShimmer();


        rvUserKitProductUserKit.setNestedScrollingEnabled(false);

//        categoryViewDisplay();
        productViewDisplay();
        ivUserKitSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProduct();
            }
        });

    }

    private void searchProduct() {
        Intent intent = new Intent(getApplicationContext(), UserKitSearchResult.class);
        if (!etUserKitSearchBar.getText().toString().isEmpty()) {
            intent.putExtra("userKitSearchTerm", etUserKitSearchBar.getText().toString());
            startActivity(intent);
        }
    }

    private void productViewDisplay() {
        APIInterface api = APIClient.getClient().create(APIInterface.class);
        api.viewProductsUserApiCall().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root root = response.body();
                    if (root.status) {

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        rvUserKitProductUserKit.setLayoutManager(gridLayoutManager);
                        UserKitProductAdapter userKitProductAdapter = new UserKitProductAdapter(getApplicationContext(), root);
                        rvUserKitProductUserKit.setAdapter(userKitProductAdapter);

                        shimmerLayoutUserKitHome.stopShimmer();
                        shimmerLayoutUserKitHome.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
//                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//                        rvProductListView.setLayoutManager(layoutManager);
//                        SellerProductListAdapter sellerProductListAdapter = new SellerProductListAdapter(root, getActivity());
//
//                        int spanCount = 2; // 3 columns
//                        int spacing = 50; // 50px
//                        boolean includeEdge = true;
//                        rvProductListView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
//                        rvProductListView.setAdapter(sellerProductListAdapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Server Failed", Toast.LENGTH_SHORT).show();
                    shimmerLayoutUserKitHome.stopShimmer();
                    shimmerLayoutUserKitHome.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                shimmerLayoutUserKitHome.stopShimmer();
                shimmerLayoutUserKitHome.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

            }
        });
    }

    private void categoryViewDisplay() {
        APIInterface apiCategoryList = APIClient.getClient().create(APIInterface.class);
        apiCategoryList.categoryListApiCall().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        rvUserKitCategoriesUserKit.setLayoutManager(linearLayoutManager);
                        UserKitCategoryAdapter userKitCategoryAdapter = new UserKitCategoryAdapter(getApplicationContext(), root);
                        rvUserKitCategoriesUserKit.setAdapter(userKitCategoryAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });

    }

    private void initView() {
        etUserKitSearchBar = findViewById(R.id.et_userkit_search_bar);
//        rvUserKitCategoriesUserKit = findViewById(R.id.rv_categories_user_kit);
        rvUserKitProductUserKit = findViewById(R.id.rv_product_user_kit);
        ivUserKitSearchButton = findViewById(R.id.iv_userkit_search_button);
        scrollView = findViewById(R.id.scrollView);
        shimmerLayoutUserKitHome = findViewById(R.id.shimmer_layout_user_kit_home);
    }
}
