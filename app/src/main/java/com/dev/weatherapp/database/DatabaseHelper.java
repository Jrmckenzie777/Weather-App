package com.dev.weatherapp.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dev.weatherapp.model.Feedback;
import com.dev.weatherapp.model.HistoryModel;
import com.dev.weatherapp.model.Users;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weather_app";
    private static final String TABLE_USERS = "users_table";
    private static final String KEY_ID_USER = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String TABLE_WEATHER = "weather";
    private static final String KEY_ID_WEATHER = "weather_id";
    private static final String KEY_CITY_NAME = "name";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_WIND = "wind";
    private static final String KEY_CLOUDS = "clouds";
    private static final String KEY_DATE_TIME = "dateTime";
    private static final String TABLE_FEEDBACK = "feedback_table";
    private static final String KEY_ID_FEEDBACK = "feedback_id";
    private static final String KEY_RATING = "rating";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_USERNAME = "user_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //UsersTable
        db.execSQL(" CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID_USER + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_WEATHER + " (" +
                KEY_ID_WEATHER + " INTEGER PRIMARY KEY, " +
                KEY_ID_USER + " TEXT NOT NULL, " +
                KEY_CITY_NAME + " TEXT NOT NULL, " +
                KEY_TEMPERATURE + " TEXT NOT NULL, " +
                KEY_HUMIDITY + " TEXT NOT NULL, " +
                KEY_WIND + " TEXT NOT NULL, " +
                KEY_CLOUDS + " TEXT NOT NULL, " +
                KEY_DATE_TIME + " TEXT NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_FEEDBACK + " (" +
                KEY_ID_FEEDBACK + " INTEGER PRIMARY KEY, " +
                KEY_ID_USER + " TEXT NOT NULL, " +
                KEY_RATING + " TEXT NOT NULL, " +
                KEY_COMMENT + " TEXT NOT NULL, " +
                KEY_USERNAME + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);

        onCreate(db);
    }

    public void register(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.getUserName());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_PASSWORD, users.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setUserName(cursor.getString(1));
                users.setEmail(cursor.getString(2));
                users.setPassword(cursor.getString(3));

                usersList.add(users);
            } while (cursor.moveToNext());
        }

        return usersList;
    }

    public void insertData(HistoryModel historyModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER, historyModel.getUser_id());
        values.put(KEY_CITY_NAME, historyModel.getCityName());
        values.put(KEY_TEMPERATURE, historyModel.getTemperature());
        values.put(KEY_HUMIDITY, historyModel.getHumidity());
        values.put(KEY_WIND, historyModel.getWind());
        values.put(KEY_CLOUDS, historyModel.getClouds());
        values.put(KEY_DATE_TIME, historyModel.getDateTime());

        db.insert(TABLE_WEATHER, null, values);
        db.close();
    }

    public List<HistoryModel> getAllData(int userId) {
        List<HistoryModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER+ " WHERE " + KEY_ID_USER + " = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HistoryModel model = new HistoryModel();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setUser_id(Integer.parseInt(cursor.getString(1)));
                model.setCityName(cursor.getString(2));
                model.setTemperature(cursor.getString(3));
                model.setHumidity(cursor.getString(4));
                model.setWind(cursor.getString(5));
                model.setClouds(cursor.getString(6));
                model.setDateTime(cursor.getString(7));

                list.add(model);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public void deleteWeather(HistoryModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER, KEY_ID_WEATHER + " = ?",
                new String[]{String.valueOf(model.getId())});
        db.close();
    }

    public void insertFeedback(Feedback feedback) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER, feedback.getUser_id());
        values.put(KEY_RATING, feedback.getRating());
        values.put(KEY_COMMENT, feedback.getComment());
        values.put(KEY_USERNAME, feedback.getUsername());
        db.insert(TABLE_FEEDBACK, null, values);
        db.close();
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Feedback model = new Feedback();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setUser_id(Integer.parseInt(cursor.getString(1)));
                model.setRating(cursor.getString(2));
                model.setComment(cursor.getString(3));
                model.setUsername(cursor.getString(4));

                list.add(model);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public Feedback getUserFeedbackByUserId(int userId) {
        Feedback feedback = null;
        String selectQuery = "SELECT * FROM " + TABLE_FEEDBACK + " WHERE " + KEY_ID_USER + " = " + userId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            feedback = new Feedback(
                    cursor.getInt(0), // ID
                    cursor.getInt(1), // User ID
                    cursor.getString(2), // Rating
                    cursor.getString(3), // Comment
                    cursor.getString(4) // Username
            );
        }

//        if (cursor.moveToFirst()) {
//            do {
//                feedback = new Feedback();
//                feedback.setId(Integer.parseInt(cursor.getString(0)));
//                feedback.setUser_id(Integer.parseInt(cursor.getString(1)));
//                feedback.setRating(cursor.getString(2));
//                feedback.setComment(cursor.getString(3));
//                feedback.setUsername(cursor.getString(4));
//            } while (cursor.moveToNext());
//        }

        cursor.close();
        return feedback;
    }

    public void updateFeedback(Feedback updatedFeedback) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RATING, updatedFeedback.getRating());
        values.put(KEY_COMMENT, updatedFeedback.getComment());
        values.put(KEY_USERNAME, updatedFeedback.getUsername());

        db.update(TABLE_FEEDBACK, values, KEY_ID_FEEDBACK + " = ?",
                new String[]{String.valueOf(updatedFeedback.getId())});

        db.close();
    }



}