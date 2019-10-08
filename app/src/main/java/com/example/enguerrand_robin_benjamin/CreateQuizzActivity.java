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

    public void addQuestion(View view) {
        LinearLayout linearLayout = findViewById(R.id.inputList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.question, null);
        linearLayout.addView(child);
    }

    public void addResponse(View view) {
        View parent = (View) view.getParent();
        LinearLayout linearLayout = parent.findViewById(R.id.responsesList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.response, null);
        linearLayout.addView(child);
    }

    public void submit(View view) {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();

        if (name.isEmpty()) {
            Toasty.error(this, "You need to provide the quizz name", Toast.LENGTH_SHORT, true).show();
            return;
        }

        Quizz quizz = new Quizz();
        quizz.name = name;

        LinearLayout InputLinearLayout = findViewById(R.id.inputList);
        final int childCount = InputLinearLayout.getChildCount();
        System.out.println(childCount);
        if (childCount == 0) {
            Toasty.error(this, "You need to provide 20 questions", Toast.LENGTH_SHORT, true).show();
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View v1 = InputLinearLayout.getChildAt(i);
            String questionName = ((EditText) v1.findViewById(R.id.questionName)).getText().toString();

            if (questionName.isEmpty()) {
                Toasty.error(this, "You need to provide the question name", Toast.LENGTH_SHORT, true).show();
                return;
            }

            QuizzQuestion quizzQuestion = new QuizzQuestion();
            quizz.name = questionName;

            LinearLayout ResponseLinearLayout = findViewById(R.id.responsesList);
            final int childCount2 = ResponseLinearLayout.getChildCount();
            for (int j = 0; j < childCount2; j++) {
                View v2 = ResponseLinearLayout.getChildAt(j);
                String responseName = ((EditText) v2.findViewById(R.id.responseName)).getText().toString();
                System.out.println(responseName);
                if (responseName.isEmpty()) {
                    Toasty.error(this, "You need to provide a response name", Toast.LENGTH_SHORT, true).show();
                    return;
                }
            }
            if (quizzQuestion.responses == null || quizzQuestion.responses.size() < 2) {
                Toasty.error(this, "You need to add at least two responses", Toast.LENGTH_SHORT, true).show();
                return;
            }
            quizz.addQuestion(quizzQuestion);
        }
    }
}
