package com.project.janani.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SllerAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SllerAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvCompanyName;
    private TextView tvPhoneNumber;
    private TextView tvEmailId;
    private TextView tvCompanyAddress;
    private TextView tvLicenseNumber;
    private TextView tvViewLicense;
    private TextView tvUserKit;
    private TextView btSellerEditAccountButton;

    public SllerAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SllerAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SllerAccountFragment newInstance(String param1, String param2) {
        SllerAccountFragment fragment = new SllerAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_sller_account, container, false);
        initView(view);
        SharedPreferences loginSellerSharedPreferences = getActivity().getSharedPreferences("loginSellerShared", getActivity().MODE_PRIVATE);
        String sellerId = loginSellerSharedPreferences.getString("sellerId", "default");

        Toast.makeText(getContext(), sellerId, Toast.LENGTH_SHORT).show();
        //32

        viewSellerAccount(sellerId);
        btSellerEditAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditSellerAccount(sellerId);
            }
        });

        return view;
    }

    private void goToEditSellerAccount(String sellerId) {
        Intent intent = new Intent(getActivity(), SellerEditAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sellerId", sellerId);
        getActivity().startActivity(intent);
    }

    private void viewSellerAccount(String sellerId) {

        APIInterface apiViewSellerId = APIClient.getClient().create(APIInterface.class);
        apiViewSellerId.viewSellerAccountApiCall(sellerId).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        tvCompanyName.setText(root.userDetails.get(0).name);
                        tvPhoneNumber.setText(root.userDetails.get(0).phone);
                        tvCompanyAddress.setText(root.userDetails.get(0).address);
                        tvEmailId.setText(root.userDetails.get(0).email);
                        tvLicenseNumber.setText(root.userDetails.get(0).license_num);
                        if ((root.userDetails.get(0).user_kit).equals("true")) {
                            tvUserKit.setText("UserKit feature is available");
                        } else if ((root.userDetails.get(0).user_kit).equals("false")) {
                            tvUserKit.setText("UserKit feature is not available");
                        }

                    } else {
                        Toast.makeText(getActivity(), "Not Successful!", Toast.LENGTH_SHORT).show();
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
        tvCompanyName = view.findViewById(R.id.tv_company_name);
        tvPhoneNumber = view.findViewById(R.id.tv_phone_number);
        tvEmailId = view.findViewById(R.id.tv_email_id);
        tvCompanyAddress = view.findViewById(R.id.tv_company_address);
        tvLicenseNumber = view.findViewById(R.id.tv_license_number);
        tvViewLicense = view.findViewById(R.id.tv_view_license);
        tvUserKit = view.findViewById(R.id.tv_user_kit);
        btSellerEditAccountButton = view.findViewById(R.id.bt_seller_edit_account_button);
    }
}