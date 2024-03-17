package com.project.janani.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.janani.shopping.adapters.SellerProductListAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SellerOrderFragment extends Fragment {


    private RecyclerView recycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_order, container, false);
        initView(view);




        return view;
    }

    public void apiCall(String sellerId) {
        APIInterface api = APIClient.getClient().create(APIInterface.class);


    }

    private void initView(View view) {
        recycleView = view.findViewById(R.id.recycle_view);
    }
}