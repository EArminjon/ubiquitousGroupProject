package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

import java.util.List;

public class User {
    public String name;
    public String password;
    public boolean admin;
    public List<QuizzScore> scores;
    public String dataBaseId;

    public User() {
    } //keep for firebase

    public User(String name, String password, boolean admin) {
        this.name = name;
        this.password = password;
        this.admin = admin;
    }

    @NonNull
    public String toString() {
        return "name: " + name + "\n" +
                "password: " + password + "\n" +
                "admin: " + admin + "\n" +
                "id:" + dataBaseId;
    }
}
