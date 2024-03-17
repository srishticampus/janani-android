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
 * Use the {@link UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvUserName;
    private TextView tvPhoneNumber;
    private TextView tvUserAge;
    private TextView tvEmailId;
    private TextView btEditAccountButton;

    public UserAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountFragment newInstance(String param1, String param2) {
        UserAccountFragment fragment = new UserAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);
        initView(view);
        SharedPreferences loginSharedPreferences = getActivity().getSharedPreferences("loginShared", getActivity().MODE_PRIVATE);
        String userId = loginSharedPreferences.getString("userId", "default");
        Toast.makeText(getContext(), userId, Toast.LENGTH_SHORT).show();
        viewUserAccount(userId);
        btEditAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditDetails(userId);
            }
        });
        return view;
    }

    private void goToEditDetails(String userId) {
        Intent intent = new Intent(getActivity(), UserEditAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId", userId);
        getActivity().startActivity(intent);
    }

    private void viewUserAccount(String userId) {

        APIInterface apiUserAccountView = APIClient.getClient().create(APIInterface.class);
        apiUserAccountView.viewUserAccountApiCall(userId).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        tvUserName.setText(root.userDetails.get(0).name);
                        tvPhoneNumber.setText(root.userDetails.get(0).mobile);
                        tvEmailId.setText(root.userDetails.get(0).email);
                        tvUserAge.setText(root.userDetails.get(0).age);
                    } else {
                        Toast.makeText(getActivity(), "Account view failed", Toast.LENGTH_SHORT).show();
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
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvPhoneNumber = view.findViewById(R.id.tv_phone_number);
        tvUserAge = view.findViewById(R.id.tv_user_age);
        tvEmailId = view.findViewById(R.id.tv_email_id);
        btEditAccountButton = view.findViewById(R.id.bt_edit_account_button);
    }
}