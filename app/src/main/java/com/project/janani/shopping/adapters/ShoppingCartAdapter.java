package com.project.janani.shopping.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;
import com.project.janani.shopping.retrofit.APIClient;
import com.project.janani.shopping.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    Context context;
    Root root;

    String quantityOrdered;

    public ShoppingCartAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(root.orderDetails.get(position).photo).into(holder.ivProductImage);
        holder.tvProductTitle.setText(root.orderDetails.get(position).name);
        holder.tvProductPrice.setText(root.orderDetails.get(position).selling_price);
        holder.tvQuantity.setText(root.orderDetails.get(position).quantity_ordered);


        SharedPreferences loginSharedPreferences = context.getSharedPreferences("loginShared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        editor.putString("total_amount", String.valueOf(root.orderDetails.get(position).total_amount));
        editor.putString("quantity", String.valueOf(root.orderDetails.get(position).quantity_ordered));
        editor.apply();

        holder.cvRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position >= 0) {
                    try {
                        String product_id = root.orderDetails.get(position).product_id;
                        removeItem(product_id, position, root);
                        root.orderDetails.remove(position);
                        notifyItemRemoved(position);
                    } catch (IndexOutOfBoundsException e) {
                        Toast.makeText(context, "WishList empty", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }
//                if (position >= 0) {
//                    String product_id = root.orderDetails.get(position).product_id;
//                    removeItem(product_id, position, root);
//                }


            }
        });

    }

    private void removeItem(String product_id, int position, Root rootShoppingCart) {
        APIInterface removeItemAPI = APIClient.getClient().create(APIInterface.class);
        SharedPreferences loginSharedPreferences = context.getSharedPreferences("loginShared", context.MODE_PRIVATE);
        String userId = loginSharedPreferences.getString("userId", "default");
        removeItemAPI.removeItemApiCall(product_id, userId).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {


                        Toast.makeText(context, root.message, Toast.LENGTH_SHORT).show();
                        rootShoppingCart.orderDetails.remove(position);
                        notifyItemRemoved(position);

                    } else {
                        Toast.makeText(context, root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "response not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return root.orderDetails.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cvProductImage;
        private ImageView ivProductImage;
        private TextView tvProductTitle;
        private TextView tvProductCategory;
        private TextView tvProductPrice;

        private CardView cvRemoveButton;
        private TextView tvQuantity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvProductImage = itemView.findViewById(R.id.cv_product_image);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            tvProductTitle = itemView.findViewById(R.id.tv_product_title);
            tvProductCategory = itemView.findViewById(R.id.tv_product_category);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            cvRemoveButton = itemView.findViewById(R.id.cv_remove_button);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
