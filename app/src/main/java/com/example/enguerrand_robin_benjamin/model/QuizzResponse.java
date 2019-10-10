package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

public class QuizzResponse {
    public String name;
    public boolean correct;
    public boolean choosed = false;

    public QuizzResponse() {
    }

    QuizzResponse(String name, boolean correct) {
        this.name = name;
        this.correct = correct;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: " + name + "\n" +
                "correct: " + correct + "\n" +
                "choosed: " + choosed + "\n";
    }
}
