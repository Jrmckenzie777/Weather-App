package com.dev.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.weatherapp.HistoryActivity;
import com.dev.weatherapp.R;
import com.dev.weatherapp.WeatherActivity;
import com.dev.weatherapp.database.DatabaseHelper;
import com.dev.weatherapp.model.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<HistoryModel> list;
    Context context;
    DatabaseHelper databaseHelper;

    public HistoryAdapter(List<HistoryModel> list, Context context, DatabaseHelper databaseHelper) {
        this.list = list;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HistoryModel historyModel = list.get(position);
        holder.tvCityName.setText(historyModel.getCityName());
        holder.tvDateTime.setText(historyModel.getDateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WeatherActivity.class);
                intent.putExtra("data", historyModel);
                context.startActivity(intent);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteWeather(historyModel);
                list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCityName, tvDateTime;
        ImageView tvDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

}
