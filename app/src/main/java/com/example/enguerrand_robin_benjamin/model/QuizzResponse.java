package com.example.enguerrand_robin_benjamin.model;

public class QuizzResponse {
    public String name;
    public boolean correct;

    public QuizzResponse() {
    }

    QuizzResponse(String name, boolean correct) {
        this.name = name;
        this.correct = correct;
    }
}
