package com.dev.weatherapp.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dev.weatherapp.model.HistoryModel;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weather_app";
    private static final String TABLE_WEATHER = "weather";
    private static final String KEY_ID = "id";
    private static final String KEY_CITY_NAME = "name";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_WIND = "wind";
    private static final String KEY_CLOUDS = "clouds";
    private static final String KEY_DATE_TIME = "dateTime";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + TABLE_WEATHER + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_CITY_NAME + " TEXT NOT NULL, " +
                KEY_TEMPERATURE + " TEXT NOT NULL, " +
                KEY_HUMIDITY + " TEXT NOT NULL, " +
                KEY_WIND + " TEXT NOT NULL, " +
                KEY_CLOUDS + " TEXT NOT NULL, " +
                KEY_DATE_TIME + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);

        onCreate(db);
    }

    public void insertData(HistoryModel historyModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CITY_NAME, historyModel.getCityName());
        values.put(KEY_TEMPERATURE, historyModel.getTemperature());
        values.put(KEY_HUMIDITY, historyModel.getHumidity());
        values.put(KEY_WIND, historyModel.getWind());
        values.put(KEY_CLOUDS, historyModel.getClouds());
        values.put(KEY_DATE_TIME, historyModel.getDateTime());

        db.insert(TABLE_WEATHER, null, values);
        db.close();
    }

    public List<HistoryModel> getAllData() {
        List<HistoryModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HistoryModel model = new HistoryModel();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setCityName(cursor.getString(1));
                model.setTemperature(cursor.getString(2));
                model.setHumidity(cursor.getString(3));
                model.setWind(cursor.getString(4));
                model.setClouds(cursor.getString(5));
                model.setDateTime(cursor.getString(6));

                list.add(model);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public void deleteWeather(HistoryModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER, KEY_ID + " = ?",
                new String[]{String.valueOf(model.getId())});
        db.close();
    }

}