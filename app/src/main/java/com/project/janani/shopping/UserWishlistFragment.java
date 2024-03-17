package com.project.janani.shopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.janani.shopping.adapters.WishlistAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserWishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserWishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvUserName;
    private RecyclerView rvWishlistItems;
    private ScrollView scrollView;
    private ShimmerFrameLayout shimmerLayoutWishList;

    public UserWishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserWishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserWishlistFragment newInstance(String param1, String param2) {
        UserWishlistFragment fragment = new UserWishlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_wishlist, container, false);
        initView(view);


        rvWishlistItems.setNestedScrollingEnabled(false);

        SharedPreferences loginSharedPreference = getActivity().getSharedPreferences("loginShared", getActivity().MODE_PRIVATE);
        String userName = loginSharedPreference.getString("userName", "default");
        tvUserName.setText(userName);

        APIInterface apiViewWishList = APIClient.getClient().create(APIInterface.class);
        apiViewWishList.viewWishListApiCall().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    buildRecyclerView(root);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public void buildRecyclerView(Root root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvWishlistItems.setLayoutManager(linearLayoutManager);
        WishlistAdapter wishlistAdapter = new WishlistAdapter(getActivity(), root);
        rvWishlistItems.setAdapter(wishlistAdapter);
    }

    private void initView(View view) {
        tvUserName = view.findViewById(R.id.tv_user_name);
        rvWishlistItems = view.findViewById(R.id.rv_wishlist_items);
        scrollView = view.findViewById(R.id.scrollView);
        shimmerLayoutWishList = view.findViewById(R.id.shimmer_layout_wish_list);
    }
}