package com.dev.weatherapp.network;

import com.dev.weatherapp.network.weather.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    //Get data of Weather by city
    @Headers({
            "X-RapidAPI-Key: 07b04c436bmsha5c93ff09be9de1p1a0905jsn2166944e4c29",
            "X-RapidAPI-Host: open-weather13.p.rapidapi.com"
    })
    @GET("city/{name}")
    Call<Response> getWeather(@Path("name") String cityName);

}
