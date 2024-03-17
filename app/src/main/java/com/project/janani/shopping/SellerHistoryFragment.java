package com.project.janani.shopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.janani.shopping.adapters.SellerOrderHistoryAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SellerHistoryFragment extends Fragment {


    private RecyclerView sellerOrderHistoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_history, container, false);
        initView(view);
        sellerOrderHistoryRecyclerView.setNestedScrollingEnabled(false);
        SharedPreferences loginSellerSharedPreferences = getActivity().getSharedPreferences("loginSellerShared", getActivity().MODE_PRIVATE);
        String sellerId = loginSellerSharedPreferences.getString("sellerId", "default");
        sellerHistoryApiCall(sellerId);
        return view;
    }

    private void initView(View view) {
        sellerOrderHistoryRecyclerView = view.findViewById(R.id.seller_order_history_recycler_view);
    }

    private void sellerHistoryApiCall(String sellerId) {

        APIInterface sellerHistoryApi = APIClient.getClient().create(APIInterface.class);
        sellerHistoryApi.viewOrderHistorySellerApiCall(sellerId).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        sellerOrderHistoryRecyclerView.setLayoutManager(linearLayoutManager);
                        SellerOrderHistoryAdapter sellerOrderHistoryAdapter = new SellerOrderHistoryAdapter(getActivity(), root);
                        sellerOrderHistoryRecyclerView.setAdapter(sellerOrderHistoryAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No Order History", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}