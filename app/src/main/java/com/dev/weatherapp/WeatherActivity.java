package com.dev.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.weatherapp.network.APIInterface;
import com.dev.weatherapp.network.ApiClient;
import com.dev.weatherapp.network.weather.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherActivity extends AppCompatActivity {
    String cityName;
    ImageView ivBack, ivShare;
    TextView tvLabel, tvTemp, tvHumidity, tvWind, tvClouds;
    ProgressDialog progressDialog;
    APIInterface apiInterface;
    LinearLayout llMain;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        tvLabel = findViewById(R.id.tvLabel);
        tvTemp = findViewById(R.id.tvTemp);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWind = findViewById(R.id.tvWind);
        tvClouds = findViewById(R.id.tvClouds);
        llMain = findViewById(R.id.llMain);
        ivBack = findViewById(R.id.ivBack);
        ivShare = findViewById(R.id.ivShare);
        apiInterface = ApiClient.getClient().create(APIInterface.class);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Weather App");
        progressDialog.setMessage("Please Wait...\nFetching Data...");
        progressDialog.show();

        if (getIntent().getExtras() != null){
            cityName = getIntent().getStringExtra("data");
            tvLabel.setText(cityName+" Weather");

            Call<Response> weather = apiInterface.getWeather(String.valueOf(cityName));
            weather.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        //Received Weather updating views
                        ivShare.setVisibility(View.VISIBLE);
                        tvTemp.setVisibility(View.VISIBLE);
                        tvLabel.setVisibility(View.VISIBLE);
                        llMain.setVisibility(View.VISIBLE);
                        double fahrenheitTemperature = response.body().getMain().getTemp();
                        double celsiusTemperature = convertFahrenheitToCelsius(fahrenheitTemperature);
                        String strTemp = Double.toString(celsiusTemperature);
                        String desiredTemp = strTemp.substring(0, 2);
                        tvTemp.setText(desiredTemp + "°C");
                        tvHumidity.setText(response.body().getMain().getHumidity().toString() + "%");
                        tvWind.setText(response.body().getWind().getSpeed().toString() + " mph");
                        tvClouds.setText(response.body().getClouds().getAll().toString() + "%");

                    } else {
                        Toast.makeText(WeatherActivity.this, response.message().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(WeatherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textShare = "City: "+tvLabel.getText().toString()+"\n"+
                     "Temperature: "+tvTemp.getText().toString()+"\n"+
                     "Humidity: "+tvHumidity.getText().toString()+"\n"+
                     "Wind: "+tvWind.getText().toString()+"\n"+
                     "Clouds: "+tvClouds.getText().toString();
                    shareText(textShare);
                }
            });

        }
    }


    private void shareText(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No sharing app installed.", Toast.LENGTH_SHORT).show();
        }
    }
    private double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

}