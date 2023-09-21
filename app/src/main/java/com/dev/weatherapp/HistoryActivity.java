package com.dev.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.weatherapp.adapter.HistoryAdapter;
import com.dev.weatherapp.database.DatabaseHelper;
import com.dev.weatherapp.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ImageView ivBack;
    TextView noDataFound;
    RecyclerView rvHistory;
    HistoryAdapter adapter;
    DatabaseHelper databaseHelper;
    List<HistoryModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ivBack = findViewById(R.id.ivBack);
        rvHistory = findViewById(R.id.rvHistory);
        noDataFound = findViewById(R.id.noDataFound);
        databaseHelper = new DatabaseHelper(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        list.clear();
        list.addAll(databaseHelper.getAllData());
        if (list.size()>0){
            noDataFound.setVisibility(View.GONE);
            rvHistory.setVisibility(View.VISIBLE);
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            adapter = new HistoryAdapter(list, this, databaseHelper);
            rvHistory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            noDataFound.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
        }

    }

}