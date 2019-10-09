package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

public class User {
    public String name;
    public String password;
    public boolean admin;
    private String id;

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
                "admin: " + admin + "\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
