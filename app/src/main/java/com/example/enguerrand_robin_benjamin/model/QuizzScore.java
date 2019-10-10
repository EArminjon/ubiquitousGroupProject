package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

public class QuizzScore {
    public String name;
    public int score;
    public int numberOfQuestion;

    public QuizzScore() {
    } //keep for firebase

    public QuizzScore(String quizzId, int score, int numberOfQuestion) {
        this.name = quizzId;
        this.score = score;
        this.numberOfQuestion = numberOfQuestion;
    }

    @NonNull
    public String toString() {
        return "name: " + name + "\n" +
                "score: " + score + "\n" +
                "numberOfQuestion: " + numberOfQuestion + "\n";
    }
}
