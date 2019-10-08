package com.example.enguerrand_robin_benjamin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzQuestion;

import es.dmoral.toasty.Toasty;

public class CreateQuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);
    }

    /// Add Input in view
    public void addQuestion(View view) {
        LinearLayout linearLayout = findViewById(R.id.inputList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.question, null);
        linearLayout.addView(child);
    }

    /// Add Input in view
    public void addResponse(View view) {
        View parent = (View) view.getParent();
        LinearLayout linearLayout = parent.findViewById(R.id.responsesList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.response, null);
        linearLayout.addView(child);
    }

    /// Get input value
    String getQuizzName() {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();

        if (name.isEmpty()) {
            Toasty.error(this, "You need to provide the quizz name", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        return name;
    }

    /// Get input value
    String getResponseName(View v) {
        String name = ((EditText) v.findViewById(R.id.responseName)).getText().toString();
        if (name.isEmpty()) {
            Toasty.error(this, "You need to provide a response name", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        return name;
    }

    /// Get input value
    String getQuestionName(View v) {
        String questionName = ((EditText) v.findViewById(R.id.questionName)).getText().toString();

        if (questionName.isEmpty()) {
            Toasty.error(this, "You need to provide the question name", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        return questionName;
    }

    QuizzQuestion generateQuizzQuestion(View v) {
        String questionName = getQuestionName(v);

        if (questionName == null) return null;

        QuizzQuestion quizzQuestion = new QuizzQuestion();
        quizzQuestion.question = questionName;

        LinearLayout ResponseLinearLayout = findViewById(R.id.responsesList);
        final int childCount2 = ResponseLinearLayout.getChildCount();
        for (int j = 0; j < childCount2; j++) {
            View v2 = ResponseLinearLayout.getChildAt(j);
            String responseName = getResponseName(v2);
            if (responseName == null) return null;
            quizzQuestion.addResponses(responseName);
        }
        if (quizzQuestion.responses == null || quizzQuestion.responses.size() < 2) {
            Toasty.error(this, "You need to add at least two responses", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        return quizzQuestion;
    }

    Quizz generateQuizz() {
        String name = getQuizzName();
        if (name == null) return null;

        Quizz quizz = new Quizz();
        quizz.name = name;

        LinearLayout InputLinearLayout = findViewById(R.id.inputList);
        final int childCount = InputLinearLayout.getChildCount();
        if (childCount == 0) {
            Toasty.error(this, "You need to provide 20 questions", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        for (int i = 0; i < childCount; i++) {
            View v = InputLinearLayout.getChildAt(i);
            QuizzQuestion quizzQuestion = generateQuizzQuestion(v);
            if (quizzQuestion == null) return null;
            quizz.addQuestion(quizzQuestion);
        }
        return quizz;
    }

    public void submit(View view) {
        Quizz quizz = generateQuizz();
        if (quizz == null) return;
        insertQuizz(quizz);
    }

    void insertQuizz(Quizz quizz) {
        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.insertThisQuizz(quizz, item -> finish(), item -> {
            String msg = (String) item;
            Toasty.error(this, msg, Toast.LENGTH_SHORT, true).show();
        });
    }
}
