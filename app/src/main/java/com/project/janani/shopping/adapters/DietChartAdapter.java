package com.project.janani.shopping.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class DietChartAdapter extends RecyclerView.Adapter<DietChartAdapter.MyViewHolder> {
    Root root;
    Context context;
    int row_index = -1;


    public DietChartAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_diet_chart_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.tvDayDietChart.setText(root.dietDetails.get(position).day);
        holder.tvTimeDietChart.setText(root.dietDetails.get(position).time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

            }
        });
        if (row_index == position) {
            holder.rlDietChartLayout.setBackgroundResource(R.drawable.out_line_background_selected);
            holder.tvDescriptionDietChart.setVisibility(View.VISIBLE);
            holder.tvDescriptionDietChart.setText(root.dietDetails.get(position).description);
        } else {
            holder.rlDietChartLayout.setBackgroundResource(R.drawable.out_line_background);
            holder.tvDescriptionDietChart.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return root.dietDetails.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTimeDietChart;
        private TextView tvDayDietChart;
        private TextView tvDescriptionDietChart;
        private RelativeLayout rlDietChartLayout;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeDietChart = itemView.findViewById(R.id.tv_time_diet_chart);
            tvDayDietChart = itemView.findViewById(R.id.tv_day_diet_chart);
            tvDescriptionDietChart = itemView.findViewById(R.id.tv_description_diet_chart);
            rlDietChartLayout = itemView.findViewById(R.id.rl_diet_chart_layout);

        }
    }
}
