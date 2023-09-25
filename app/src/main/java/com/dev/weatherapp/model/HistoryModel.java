package com.dev.weatherapp.model;

import java.io.Serializable;

public class HistoryModel implements Serializable {
    int id, user_id;
    String cityName, temperature, humidity, wind, clouds, dateTime;

    public HistoryModel() {
    }

    public HistoryModel(int id, int user_id, String cityName, String temperature, String humidity, String wind, String clouds, String dateTime) {
        this.id = id;
        this.user_id = user_id;
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.clouds = clouds;
        this.dateTime = dateTime;
    }

    public HistoryModel(int user_id, String cityName, String temperature, String humidity, String wind, String clouds, String dateTime) {
        this.user_id = user_id;
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.clouds = clouds;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
