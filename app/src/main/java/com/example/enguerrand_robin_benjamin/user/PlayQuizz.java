package com.example.enguerrand_robin_benjamin.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.Callback;
import com.example.enguerrand_robin_benjamin.FirebaseDatabaseHelper;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzQuestion;
import com.example.enguerrand_robin_benjamin.model.QuizzResponse;
import com.example.enguerrand_robin_benjamin.model.QuizzScore;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import java.util.Collections;

import es.dmoral.toasty.Toasty;

public class PlayQuizz extends AppCompatActivity {
    Quizz quizz;
    User user;
    int index = 0;
    ResponseListAdapter productListViewAdapter;

    void randomQuestionGenerator(Callback success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Number between 0 - 9");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_PHONE);
        KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
        input.setKeyListener(keyListener);
        InputFilter[] editFilters = input.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.LengthFilter(1); //the desired length
        input.setFilters(newFilters);


        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            int inputNumber = Integer.parseInt(input.getText().toString());
            success.run(inputNumber);
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quizz);

        Gson gson = new Gson();
        quizz = gson.fromJson(getIntent().getStringExtra("quizz"), Quizz.class);
        user = gson.fromJson(getIntent().getStringExtra("user"), User.class);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Quizz: " + quizz.name);


        randomQuestionGenerator(item -> {
            System.out.println(item);
            Collections.shuffle(quizz.questions);

            ListView listView = findViewById(R.id.choiceList);
            productListViewAdapter = new ResponseListAdapter();
            listView.setAdapter(productListViewAdapter);
            generateThisQuestion(index);
        });
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

    int computeScore() {
        int score = 0;
        for (QuizzQuestion question : quizz.questions) {
            for (QuizzResponse response : question.responses) {
                if (response.choosed && response.correct)
                    score += 1;
            }
        }
        return score;
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
                int result = computeScore();
                submit(result);
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

    void submit(int result) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Toasty.success(this, "Your score: " + result, Toast.LENGTH_SHORT, true).show();
                    user.scores.add(new QuizzScore(quizz.name, result, quizz.questions.size()));
                    FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
                    helper.updateOrCreateThisUser(this.user, item -> finish(), item -> {
                        String msg = (String) item;
                        Toasty.error(this, msg, Toast.LENGTH_SHORT, true).show();
                    });
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quizz ready to submit, process ?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }
}
