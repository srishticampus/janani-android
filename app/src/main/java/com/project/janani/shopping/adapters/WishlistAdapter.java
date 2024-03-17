package com.project.janani.shopping.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    int[] images = {R.drawable.shopping, R.drawable.shopping, R.drawable.shopping, R.drawable.shopping,};
    Context context;
    Root root;

    public WishlistAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_items_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(root.wishlistDetails.get(position).image1).into(holder.ivProductImage);
        holder.tvProductTitle.setText(root.wishlistDetails.get(position).productname);
        holder.tvProductPrice.setText(root.wishlistDetails.get(position).mrp);
        holder.tvProductCategory.setText(root.wishlistDetails.get(position).category);

        String user_id = root.wishlistDetails.get(position).user_id;
        String product_id = root.wishlistDetails.get(position).product_id;
        holder.cvRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position >= 0) {
                    try {
                        removeFromWishList(user_id, product_id, position, root);
                        root.wishlistDetails.remove(position);
                        notifyItemRemoved(position);
                    } catch (IndexOutOfBoundsException e) {
                        Toast.makeText(context, "WishList empty", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

        });

        holder.cvAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_dialog_box);
                final EditText quantity = dialog.findViewById(R.id.et_quantity);
                Button addButton = dialog.findViewById(R.id.bt_add_button);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addToCart(user_id, product_id, quantity.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void addToCart(String user_id, String product_id, String quantity) {

        APIInterface apiAddToCart = APIClient.getClient().create(APIInterface.class);
        apiAddToCart.addToCartApiCall(product_id, user_id, quantity).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(context, root.message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromWishList(String user_id, String product_id, int position, Root
            rootWishList) {

        APIInterface apiRemoveFromWishList = APIClient.getClient().create(APIInterface.class);
        apiRemoveFromWishList.removeFromWishListApiCall(product_id, user_id).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                if (response.isSuccessful()) {
                    if (root.status) {
                        Toast.makeText(context, root.message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
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
        return root.wishlistDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductTitle, tvProductCategory, tvProductPrice, tvUserName;
        private CardView cvAddButton;
        private CardView cvRemoveButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            tvProductTitle = itemView.findViewById(R.id.tv_product_title);
            tvProductCategory = itemView.findViewById(R.id.tv_product_category);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            cvAddButton = itemView.findViewById(R.id.cv_add_button);
            cvRemoveButton = itemView.findViewById(R.id.cv_remove_button);
        }
    }
}
