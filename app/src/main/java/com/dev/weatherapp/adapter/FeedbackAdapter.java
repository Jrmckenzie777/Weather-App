package com.dev.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.weatherapp.R;
import com.dev.weatherapp.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.VH>{
    List<Feedback> list;
    Context context;

    public FeedbackAdapter(List<Feedback> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews, parent, false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Feedback model=list.get(position);
        holder.tvRating.setText("("+model.getRating()+")");
        holder.tvUserName.setText(model.getUsername());
        holder.tvComment.setText(model.getComment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        AppCompatTextView tvRating, tvComment,tvUserName;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvUserName = itemView.findViewById(R.id.tvUserName);
        }
    }
}
