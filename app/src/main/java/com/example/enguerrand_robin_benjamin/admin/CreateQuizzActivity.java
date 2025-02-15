package com.example.enguerrand_robin_benjamin.admin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.FirebaseDatabaseHelper;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzQuestion;
import com.example.enguerrand_robin_benjamin.model.QuizzResponse;

import es.dmoral.toasty.Toasty;

public class CreateQuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Create");
    }


    public void removeQuestion(View view) {
        View parent = (View) view.getParent();
        LinearLayout linearLayout = ((View) parent.getParent()).findViewById(R.id.inputList);
        linearLayout.removeView(parent);
    }

    public void removeResponse(View view) {
        View parent = (View) view.getParent();
        LinearLayout linearLayout = ((View) parent.getParent()).findViewById(R.id.responsesList);
        linearLayout.removeView(parent);
    }

    /// Add Input in view
    public void addQuestion(View view) {
        LinearLayout linearLayout = findViewById(R.id.inputList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.create_question, null);
        linearLayout.addView(child);
    }

    /// Add Input in view
    public void addResponse(View view) {
        View parent = (View) view.getParent();
        LinearLayout linearLayout = parent.findViewById(R.id.responsesList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.create_response, null);
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
    QuizzResponse getResponses(View v) {
        QuizzResponse response = new QuizzResponse();
        response.name = ((EditText) v.findViewById(R.id.responseName)).getText().toString();
        response.correct = ((CheckBox) v.findViewById(R.id.answer)).isChecked();
        if (response.name.isEmpty()) {
            Toasty.error(this, "You need to provide a create_response name", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        return response;
    }

    /// Get input value
    String getQuestionName(View v) {
        String questionName = ((EditText) v.findViewById(R.id.questionName)).getText().toString();

        if (questionName.isEmpty()) {
            Toasty.error(this, "You need to provide the create_question name", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        return questionName;
    }

    QuizzQuestion generateQuizzQuestion(View v) {
        String questionName = getQuestionName(v);

        if (questionName == null) return null;

        QuizzQuestion quizzQuestion = new QuizzQuestion();
        quizzQuestion.question = questionName;

        LinearLayout ResponseLinearLayout = v.findViewById(R.id.responsesList);
        final int childCount2 = ResponseLinearLayout.getChildCount();
        for (int j = 0; j < childCount2; j++) {
            View v2 = ResponseLinearLayout.getChildAt(j);
            QuizzResponse responseName = getResponses(v2);
            if (responseName == null) return null;
            quizzQuestion.addResponses(responseName);
        }
        if (quizzQuestion.responses == null || quizzQuestion.responses.size() < 2) {
            Toasty.error(this, "You need to add at least two responses", Toast.LENGTH_SHORT, true).show();
            return null;
        }
        int answer = 0;
        for (int i =  0; i < quizzQuestion.responses.size(); ++i) {
            QuizzResponse response = quizzQuestion.responses.get(i);
            if (response.correct)
                answer += 1;
        }
        if (answer != 1) {
            Toasty.error(this, "You need to check one and only one answer", Toast.LENGTH_SHORT, true).show();
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
