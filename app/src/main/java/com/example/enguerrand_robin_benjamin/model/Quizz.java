package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Quizz {
    public String name;
    public List<QuizzQuestion> questions = new ArrayList<>();

    public Quizz() {
    } //keep for firebase

    public Quizz(String name, List<QuizzQuestion> questions) {
        this.name = name;
        this.questions = questions;
    }

    public void addQuestion(QuizzQuestion question) {
        questions.add(question);
    }

    @NonNull
    public String toString() {
        return "name: " + name + "\n" +
                "quizz length: " + questions.size() + "\n" +
                "quizz: " + questions.toString() + "\n";
    }
}
