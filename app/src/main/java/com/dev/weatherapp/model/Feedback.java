package com.dev.weatherapp.model;

public class Feedback {
    int id, user_id;
    String rating, comment, username;

    public Feedback() {
    }

    public Feedback(int user_id, String rating, String comment, String username) {
        this.user_id = user_id;
        this.rating = rating;
        this.comment = comment;
        this.username = username;
    }

    public Feedback(int id, int user_id, String rating, String comment, String username) {
        this.id = id;
        this.user_id = user_id;
        this.rating = rating;
        this.comment = comment;
        this.username = username;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
