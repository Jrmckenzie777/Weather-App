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
import android.widget.ImageView;
import android.widget.Toast;

public class CheckWeatherActivity extends AppCompatActivity {
    ImageView ivBack;
    EditText etCityName;
    AppCompatButton btnCheckWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_weather);

        ivBack = findViewById(R.id.ivBack);
        etCityName = findViewById(R.id.etCityName);
        btnCheckWeather = findViewById(R.id.btnCheckWeather);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = etCityName.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(CheckWeatherActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    if (isInternetAvailable()) {
                        etCityName.setText("");
                        Intent intent = new Intent(CheckWeatherActivity.this, WeatherActivity.class);
                        intent.putExtra("city", city);
                        startActivity(intent);
                    } else {
                        // No internet connectivity
                        Toast.makeText(CheckWeatherActivity.this, "No internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

}