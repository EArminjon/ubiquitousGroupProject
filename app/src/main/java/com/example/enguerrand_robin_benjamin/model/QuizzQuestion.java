package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class QuizzQuestion {
    public String question;
    public List<String> responses = new ArrayList<>();

    public QuizzQuestion() {
    } //keep for firebase

    public QuizzQuestion(String question, List<String> responses) {
        this.question = question;
        this.responses = responses;
    }

    public void addResponses(String response) {
        responses.add(response);
    }

    @NonNull
    public String toString() {
        return "question: " + question + "\n" +
                "responses length: " + responses.size() + "\n" +
                "responses: " + responses.toString() + "\n";
    }
}
