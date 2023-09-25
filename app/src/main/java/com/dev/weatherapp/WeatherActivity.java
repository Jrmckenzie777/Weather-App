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

import com.dev.weatherapp.database.DatabaseHelper;
import com.dev.weatherapp.model.HelperClass;
import com.dev.weatherapp.model.HistoryModel;
import com.dev.weatherapp.network.APIInterface;
import com.dev.weatherapp.network.ApiClient;
import com.dev.weatherapp.network.weather.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherActivity extends AppCompatActivity {
    String cityName;
    ImageView ivBack, ivShare;
    TextView tvLabel, tvTemp, tvHumidity, tvWind, tvClouds, tvDateTime;
    ProgressDialog progressDialog;
    APIInterface apiInterface;
    LinearLayout llMain;
    HistoryModel historyModel;
    DatabaseHelper databaseHelper;

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
        tvDateTime = findViewById(R.id.tvDateTime);
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        databaseHelper = new DatabaseHelper(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Weather App");
        progressDialog.setMessage("Please Wait...\nFetching Data...");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().getExtras() != null){
            cityName = getIntent().getStringExtra("city");
            historyModel = (HistoryModel) getIntent().getSerializableExtra("data");
            if (historyModel != null){
                ivShare.setVisibility(View.VISIBLE);
                tvTemp.setVisibility(View.VISIBLE);
                tvLabel.setVisibility(View.VISIBLE);
                llMain.setVisibility(View.VISIBLE);
                tvDateTime.setVisibility(View.VISIBLE);
                tvLabel.setText(historyModel.getCityName()+" Weather");
                tvTemp.setText(historyModel.getTemperature());
                tvHumidity.setText(historyModel.getHumidity());
                tvWind.setText(historyModel.getWind());
                tvClouds.setText(historyModel.getClouds());
                tvDateTime.setText("Date & Time: "+historyModel.getDateTime());
            }else{
                progressDialog.show();
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
                            tvDateTime.setVisibility(View.VISIBLE);
                            double fahrenheitTemperature = response.body().getMain().getTemp();
                            double celsiusTemperature = convertFahrenheitToCelsius(fahrenheitTemperature);
                            String strTemp = Double.toString(celsiusTemperature);
                            String desiredTemp = strTemp.substring(0, 2);
                            tvTemp.setText(desiredTemp + "°C");
                            tvHumidity.setText(response.body().getMain().getHumidity().toString() + "%");
                            tvWind.setText(response.body().getWind().getSpeed().toString() + " mph");
                            tvClouds.setText(response.body().getClouds().getAll().toString() + "%");
                            tvDateTime.setText("Date & Time: "+getCurrentDateTime());
                            HistoryModel model = new HistoryModel(HelperClass.users.getId(), cityName, tvTemp.getText().toString(),
                                    tvHumidity.getText().toString(), tvWind.getText().toString(),
                                    tvClouds.getText().toString(), getCurrentDateTime());
                            databaseHelper.insertData(model);
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
            }

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

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss aa", Locale.getDefault());
        return sdf.format(new Date());
    }

}