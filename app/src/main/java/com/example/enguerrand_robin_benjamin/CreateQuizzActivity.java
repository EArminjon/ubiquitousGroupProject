package com.example.enguerrand_robin_benjamin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;

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
        System.out.println(name);
        LinearLayout InputLinearLayout = findViewById(R.id.inputList);
        final int childCount = InputLinearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v1 = InputLinearLayout.getChildAt(i);
            String questionName = ((EditText) v1.findViewById(R.id.questionName)).getText().toString();
            System.out.println(questionName);

            LinearLayout ResponseLinearLayout = findViewById(R.id.responsesList);
            final int childCount2 = ResponseLinearLayout.getChildCount();
            for (int j = 0; j < childCount2; j++) {
                View v2 = ResponseLinearLayout.getChildAt(j);
                String responseName = ((EditText) v2.findViewById(R.id.responseName)).getText().toString();
                System.out.println(responseName);


            }

        }
    }
}
