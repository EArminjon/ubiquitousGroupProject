package com.example.enguerrand_robin_benjamin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzQuestion;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

public class EditQuizzActivity extends CreateQuizzActivity {
    Quizz quizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quizz);

        Gson gson = new Gson();
        this.quizz = gson.fromJson(getIntent().getStringExtra("quizz"), Quizz.class);

        autoFillQuizz();
    }

    void setQuizzName() {
        EditText name = findViewById(R.id.name);

        name.setText(quizz.name);
    }

    void setQuizzQuestion(LinearLayout layout, QuizzQuestion quizzQuestion) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.question, null);
        EditText questionName = child.findViewById(R.id.questionName);
        questionName.setText(quizzQuestion.question);

        LinearLayout responseLinearLayout = child.findViewById(R.id.responsesList);
        final int childCount = quizzQuestion.responses.size();
        for (int i = 0; i < childCount; i++) {
            View subChild = inflater.inflate(R.layout.response, null);
            EditText response = subChild.findViewById(R.id.responseName);
            response.setText(quizzQuestion.responses.get(i));
            responseLinearLayout.addView(subChild);
        }
        layout.addView(child);
    }

    @Override
    public void submit(View view) {
        Quizz quizz = generateQuizz();
        if (quizz == null) return;
        quizz.setDataBaseId(this.quizz.getDataBaseId());
        insertQuizz(quizz);
    }


    void autoFillQuizz() {
        setQuizzName();
        LinearLayout InputLinearLayout = findViewById(R.id.inputList);
        final int childCount = quizz.questions.size();
        for (int i = 0; i < childCount; i++) {
            setQuizzQuestion(InputLinearLayout, quizz.questions.get(i));
        }
    }

    public void deleteThisQuizz(View view) {
        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.deleteThisQuizz(this.quizz, item -> finish(), item -> {
            String msg = (String) item;
            Toasty.error(this, msg, Toast.LENGTH_SHORT, true).show();
        });
    }
}
