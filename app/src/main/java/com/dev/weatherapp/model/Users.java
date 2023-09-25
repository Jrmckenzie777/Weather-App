package com.dev.weatherapp.model;

public class Users {
    int id;
    String userName, email, password;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Users(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Users() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
