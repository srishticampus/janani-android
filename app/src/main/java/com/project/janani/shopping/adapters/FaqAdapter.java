package com.project.janani.shopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.janani.shopping.R;
import com.project.janani.shopping.model.Root;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {
    Context context;
    Root root;

    public FaqAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_custom_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvFaqQuestion.setText(root.faq.get(position).question);
        holder.tvFaqAnswers.setText(root.faq.get(position).answer);
    }

    @Override
    public int getItemCount() {
        return root.faq.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFaqQuestion;
        private TextView tvFaqAnswers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFaqQuestion = itemView.findViewById(R.id.tv_faq_question);
            tvFaqAnswers = itemView.findViewById(R.id.tv_faq_answers);
        }
    }
}
