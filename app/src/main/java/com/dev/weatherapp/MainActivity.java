package com.dev.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.weatherapp.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {

    EditText etCityName;
    AppCompatButton btnCheckWeather, btnViewHistory, btnViewFeedback, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        etCityName = findViewById(R.id.etCityName);
        btnCheckWeather = findViewById(R.id.btnCheckWeather);
        btnViewHistory = findViewById(R.id.btnViewHistory);
        btnViewFeedback = findViewById(R.id.btnViewFeedback);
        btnLogout = findViewById(R.id.btnLogout);

        btnCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckWeatherActivity.class);
                startActivity(intent);
            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        btnViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

}