package com.project.janani.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.janani.shopping.adapters.UserKitSearchResultAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserKitSearchResult extends AppCompatActivity {
    private TextView tvUserKitSearchHeading;
    private RecyclerView rvUserKitSearchResultView;
    String searchTerm;
    private ScrollView scrollView;
    private ShimmerFrameLayout shimmerLayoutUserKitSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_kit_search_result);
        searchTerm = getIntent().getStringExtra("userKitSearchTerm");
        initView();


        scrollView.setVisibility(View.GONE);
        shimmerLayoutUserKitSearchResult.startShimmer();

        tvUserKitSearchHeading.setText("Your search result for : " + searchTerm.toUpperCase(Locale.ROOT));

        APIInterface searchUserKitResultApi = APIClient.getClient().create(APIInterface.class);
        searchUserKitResultApi.searchItemApiCall(searchTerm).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(UserKitSearchResult.this, "success", Toast.LENGTH_SHORT).show();
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        rvUserKitSearchResultView.setLayoutManager(gridLayoutManager);
                        UserKitSearchResultAdapter userKitSearchResultAdapter = new UserKitSearchResultAdapter(getApplicationContext(), root);
                        rvUserKitSearchResultView.setAdapter(userKitSearchResultAdapter);

                        shimmerLayoutUserKitSearchResult.stopShimmer();
                        shimmerLayoutUserKitSearchResult.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(UserKitSearchResult.this, "false", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserKitSearchResult.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(UserKitSearchResult.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvUserKitSearchHeading = findViewById(R.id.tv_userkit_search_heading);
        rvUserKitSearchResultView = findViewById(R.id.rv_userkit_search_result_view);
        scrollView = findViewById(R.id.scrollView);
        shimmerLayoutUserKitSearchResult = findViewById(R.id.shimmer_layout_user_kit_search_result);
    }
}
