package com.example.enguerrand_robin_benjamin.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class QuizzQuestion {
    public String question;
    public List<QuizzResponse> responses = new ArrayList<>();

    public QuizzQuestion() {
    } //keep for firebase

    public QuizzQuestion(String question, List<QuizzResponse> responses) {
        this.question = question;
        this.responses = responses;
    }

    public void addResponses(QuizzResponse response) {
        responses.add(response);
    }

    @NonNull
    public String toString() {
        return "question: " + question + "\n" +
                "responses length: " + responses.size() + "\n" +
                "responses: " + responses.toString() + "\n";
    }
}
