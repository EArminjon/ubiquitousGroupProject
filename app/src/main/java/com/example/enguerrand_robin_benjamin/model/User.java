package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

public class User extends Object {
    public String name;
    public String password;
    public boolean admin;

    public User() {} //keep for firebase

    public User(String name, String password, boolean admin) {
        this.name = name;
        this.password = password;
        this.admin = admin;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: " + name + "\n" +
                "password: " + password + "\n" +
                "admin: " + admin + "\n";
    }
}
