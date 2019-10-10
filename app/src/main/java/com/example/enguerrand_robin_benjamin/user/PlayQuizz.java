package com.example.enguerrand_robin_benjamin.user;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.QuizzListAdapter;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzQuestion;
import com.example.enguerrand_robin_benjamin.model.QuizzResponse;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

//class QuizzUserAnswer {
//    int questionPosition = -1, answerPosition = -1;
//    boolean correct;
//}

public class PlayQuizz extends AppCompatActivity {
    Quizz quizz;
    User user;
    int index = 0;
    ResponseListAdapter productListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quizz);

        Gson gson = new Gson();
        quizz = gson.fromJson(getIntent().getStringExtra("quizz"), Quizz.class);
        user = gson.fromJson(getIntent().getStringExtra("user"), User.class);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Quizz: " + quizz.name);


        ListView listView = findViewById(R.id.choiceList);
        productListViewAdapter = new ResponseListAdapter();
        listView.setAdapter(productListViewAdapter);
        generateThisQuestion(index);
    }

    void generateThisQuestion(int i) {
        ((TextView) findViewById(R.id.questionName)).setText(quizz.questions.get(i).question);
        productListViewAdapter.update(quizz.questions.get(i).responses);
    }

    public boolean validThisQuizzQuestion() {
        ListView responseList = findViewById(R.id.choiceList);
        final int childCount = responseList.getChildCount();
        int answerNumber = 0;

        for (int j = 0; j < childCount; j++) {
            QuizzResponse response = quizz.questions.get(index).responses.get(j);
            View v2 = responseList.getChildAt(j);
            CheckBox checkbox = v2.findViewById(R.id.answer);
            if (checkbox.isChecked()) {
                response.choosed = true;
                answerNumber += 1;
            } else {
                response.choosed = false;
            }
        }
        if (answerNumber > 1) {
            Toasty.error(this, "You can only select one response", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (answerNumber == 0) {
            Toasty.error(this, "Select a response first", Toast.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

    public void onNext(View view) {
        if (index >= quizz.questions.size()) {
            Toasty.info(this, "This is the last question", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (validThisQuizzQuestion()) {
            if (index < quizz.questions.size() - 1) {
                ++index;
                generateThisQuestion(index);
            } else {
                Toasty.info(this, "Submit your response ?", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    public void onPrevious(View view) {
        if (index <= 0) {
            Toasty.info(this, "This is the first question", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (validThisQuizzQuestion()) {
            --index;
            generateThisQuestion(index);
        }
    }
}
