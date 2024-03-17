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
import com.project.janani.shopping.adapters.SearchResultAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private TextView tvSearchHeading;
    private RecyclerView rvSearchResultView;
    String term;
    private ScrollView scrollView;
    private ShimmerFrameLayout shimmerLayoutSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        term = getIntent().getStringExtra("searchTerm");
        initView();

        scrollView.setVisibility(View.GONE);
        shimmerLayoutSearchResult.startShimmer();

        tvSearchHeading.setText("Your search result for  " + term.toUpperCase(Locale.ROOT));

        APIInterface searchResultApi = APIClient.getClient().create(APIInterface.class);
        searchResultApi.searchItemApiCall(term).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        rvSearchResultView.setLayoutManager(gridLayoutManager);
                        SearchResultAdapter searchResultActivity = new SearchResultAdapter(getApplicationContext(), root);
                        rvSearchResultView.setAdapter(searchResultActivity);

                        shimmerLayoutSearchResult.stopShimmer();
                        shimmerLayoutSearchResult.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(SearchResultActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvSearchHeading = findViewById(R.id.tv_search_heading);
        rvSearchResultView = findViewById(R.id.rv_search_result_view);
        scrollView = findViewById(R.id.scroll_view);
        shimmerLayoutSearchResult = findViewById(R.id.shimmer_layout_search_result);
    }
}