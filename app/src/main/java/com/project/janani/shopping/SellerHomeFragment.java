package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.janani.shopping.adapters.SellerProductListAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SellerHomeFragment extends Fragment {


    private RecyclerView sellerProductListRecyclerView;
    private FloatingActionButton sellerAddProductBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_home, container, false);
        initView(view);
        SharedPreferences loginSellerSharedPreferences = getActivity().getSharedPreferences("loginSellerShared", getActivity().MODE_PRIVATE);
        String sellerId = loginSellerSharedPreferences.getString("sellerId", "default");

        apiCall(sellerId);

        sellerAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewSellerAddProductActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void apiCall(String sellerId) {
        APIInterface api = APIClient.getClient().create(APIInterface.class);
        api.viewProductsSellerApiCall(sellerId).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root root = response.body();
                    if (root.status) {
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                        sellerProductListRecyclerView.setLayoutManager(layoutManager);
                        SellerProductListAdapter sellerProductListAdapter = new SellerProductListAdapter(root, getActivity());

                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = true;
                        sellerProductListRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        sellerProductListRecyclerView.setAdapter(sellerProductListAdapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "Server Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), "server error" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initView(View view) {
        sellerProductListRecyclerView = view.findViewById(R.id.seller_product_list_recycler_view);
        sellerAddProductBtn = view.findViewById(R.id.seller_add_product_btn);
    }
}