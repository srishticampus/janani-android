package com.project.janani.shopping;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.janani.shopping.adapters.CategoriesAdapter;
import com.project.janani.shopping.adapters.UserProductListAdapter;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ScrollView scrollView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private EditText etSearchBar;
    private RecyclerView rvCategoriesView;
    private RecyclerView rvProductListView;
    private FloatingActionButton btUserKitButton;
    public static String data;
    private ImageView ivSearchButton;
    private TextView tvCategorySanitaryPad;
    private TextView tvCategoryPeriodUnderwear;
    private TextView tvCategoryTampons;
    private TextView tvCategoryMenstrualCups;
    private TextView tvErrorMsg;
    private TextView tvAllCategories;
    private FrameLayout frameLayout;

    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        initView(view);

        shimmerFrameLayout.startShimmer();

        rvProductListView.setNestedScrollingEnabled(false);
        productViewDisplay();

        SharedPreferences categorySharedPreference = getActivity().getSharedPreferences("category select", Context.MODE_PRIVATE);
        data = categorySharedPreference.getString("category", "default");

        tvAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvErrorMsg.setVisibility(View.GONE);
                rvProductListView.setVisibility(VISIBLE);
                productViewDisplay();
                tvAllCategories.setTextColor(Color.parseColor("#757070"));
                tvAllCategories.setBackgroundColor(Color.parseColor("#5FCCA2"));
                tvCategorySanitaryPad.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryMenstrualCups.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryTampons.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryPeriodUnderwear.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });

        tvCategorySanitaryPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCategorySanitaryPad.setTextColor(Color.parseColor("#757070"));
                tvCategorySanitaryPad.setBackgroundColor(Color.parseColor("#5FCCA2"));
                tvAllCategories.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryMenstrualCups.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryTampons.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryPeriodUnderwear.setBackgroundColor(Color.parseColor("#ffffff"));
                String category = tvCategorySanitaryPad.getText().toString();

                displayProduct(category);
//                tvCategorySanitaryPad.setTextColor(Color.parseColor("#757070"));
//                tvCategorySanitaryPad.setBackgroundColor(Color.parseColor("#5FCCA2"));
//                rvProductListView.setVisibility(View.GONE);
//                frameLayout.setVisibility(View.VISIBLE);
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.frame_layout, padsFragment);
//                transaction.addToBackStack("");
//                transaction.commit();
            }
        });

        tvCategoryMenstrualCups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCategoryMenstrualCups.setTextColor(Color.parseColor("#757070"));
                tvCategoryMenstrualCups.setBackgroundColor(Color.parseColor("#5FCCA2"));
                tvAllCategories.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategorySanitaryPad.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryPeriodUnderwear.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryTampons.setBackgroundColor(Color.parseColor("#ffffff"));
                String category = tvCategoryMenstrualCups.getText().toString();
                displayProduct("cups");
            }
        });

        tvCategoryPeriodUnderwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCategoryPeriodUnderwear.setTextColor(Color.parseColor("#757070"));
                tvCategoryPeriodUnderwear.setBackgroundColor(Color.parseColor("#5FCCA2"));
                tvCategorySanitaryPad.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAllCategories.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryMenstrualCups.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryTampons.setBackgroundColor(Color.parseColor("#ffffff"));
                String category = tvCategoryPeriodUnderwear.getText().toString();
                displayProduct(category);
            }
        });

        tvCategoryTampons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCategoryTampons.setTextColor(Color.parseColor("#757070"));
                tvCategoryTampons.setBackgroundColor(Color.parseColor("#5FCCA2"));
                tvAllCategories.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategorySanitaryPad.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryMenstrualCups.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCategoryPeriodUnderwear.setBackgroundColor(Color.parseColor("#ffffff"));
                String category = tvCategoryTampons.getText().toString();
                displayProduct(category);
            }
        });

//        categoryViewDisplay();


        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProduct();
            }
        });
        btUserKitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserKitProductActivity.class));
            }
        });


        return view;
    }

    private void searchProduct() {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!etSearchBar.getText().toString().isEmpty()) {
            intent.putExtra("searchTerm", etSearchBar.getText().toString());
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
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        rvProductListView.setLayoutManager(gridLayoutManager);
                        UserProductListAdapter userProductListAdapter = new UserProductListAdapter(getActivity(), root);
                        rvProductListView.setAdapter(userProductListAdapter);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        scrollView.setVisibility(VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Server Failed", Toast.LENGTH_SHORT).show();
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    scrollView.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(VISIBLE);

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
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        rvCategoriesView.setLayoutManager(linearLayoutManager);
                        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(), root);
                        rvCategoriesView.setAdapter(categoriesAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProduct(String category) {

        APIInterface apiCategoryFilterCAll = APIClient.getClient().create(APIInterface.class);
        apiCategoryFilterCAll.productCategoryFilterApiCall(category).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        tvErrorMsg.setVisibility(View.GONE);
                        rvProductListView.setVisibility(View.VISIBLE);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        rvProductListView.setLayoutManager(gridLayoutManager);
                        UserProductListAdapter userProductListAdapter = new UserProductListAdapter(getActivity(), root);
                        rvProductListView.setAdapter(userProductListAdapter);
                    } else {
                        tvErrorMsg.setVisibility(View.VISIBLE);
                        rvProductListView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Products Unavailable!", Toast.LENGTH_SHORT).show();

                    }
                }
            }


            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {

        etSearchBar = view.findViewById(R.id.et_search_bar);
//        rvCategoriesView = view.findViewById(R.id.rv_categories_view);
        rvProductListView = view.findViewById(R.id.rv_product_list_view);
        btUserKitButton = view.findViewById(R.id.bt_user_kit_button);
        ivSearchButton = view.findViewById(R.id.iv_search_button);
        scrollView = view.findViewById(R.id.scroll_view);
        shimmerFrameLayout = view.findViewById(R.id.sl_user_home_shimmer_layout);
        tvCategorySanitaryPad = view.findViewById(R.id.tv_category_sanitary_pad);
        tvCategoryPeriodUnderwear = view.findViewById(R.id.tv_category_period_underwear);
        tvCategoryTampons = view.findViewById(R.id.tv_category_tampons);
        tvCategoryMenstrualCups = view.findViewById(R.id.tv_category_menstrual_cups);
        frameLayout = view.findViewById(R.id.frame_layout);
        tvErrorMsg = view.findViewById(R.id.tv_error_message);
        tvAllCategories = view.findViewById(R.id.tv_all_category);
    }
}