package com.project.janani.shopping.adapters;


import static androidx.core.content.ContextCompat.getDrawable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;
import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

import org.w3c.dom.Text;

public class UserKitCategoryAdapter extends RecyclerView.Adapter<UserKitCategoryAdapter.MyViewHolder> {
    int row_index = -1;
    Context context;
    Root root;

    public UserKitCategoryAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_kit_category_custom_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {



        holder.categoryTitle.setText(root.category.get(position).item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

            }
        });
        if (row_index == position) {
            holder.categoryTitle.setTextColor(Color.parseColor("#757070"));
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#5FCCA2"));

        } else {
            holder.categoryTitle.setTextColor(Color.parseColor("#414D48"));
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public int getItemCount() {
        return root.category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle;
        RelativeLayout relativeLayout;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.tv_category_title);
            cardView = itemView.findViewById(R.id.cv_holder_card);
            relativeLayout = itemView.findViewById(R.id.rl_holder_view);
        }
    }
}
